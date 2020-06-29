package kiz.learnwithvel.browser.util;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import kiz.learnwithvel.browser.request.response.ApiResponse;

public abstract class NetworkBoundResource<CacheObject, RequestObject> {

    private static final String TAG = "NetworkBoundResource";
    private final AppExecutor appExecutor;
    private MediatorLiveData<Resource<CacheObject>> result = new MediatorLiveData<>();

    public NetworkBoundResource(AppExecutor appExecutor) {
        this.appExecutor = appExecutor;
        init();
    }

    private void init() {

        result.setValue(Resource.loading(null));

        final LiveData<CacheObject> dbSource = loadFromDb();

        result.addSource(dbSource, cacheObject -> {
            result.removeSource(dbSource);

            if (shouldFetch(cacheObject)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, new Observer<CacheObject>() {
                    @Override
                    public void onChanged(CacheObject cacheObject) {
                        setValue(Resource.success(cacheObject));
                    }
                });
            }

        });

    }

    private void fetchFromNetwork(LiveData<CacheObject> dbSource) {

        result.addSource(dbSource, cacheObject ->
                setValue(Resource.loading(cacheObject)));

        final LiveData<ApiResponse<RequestObject>> apiResponse = createCall();

        result.addSource(apiResponse, requestObjectApiResponse -> {
            result.removeSource(dbSource);
            result.removeSource(apiResponse);

            if (requestObjectApiResponse instanceof ApiResponse.ApiSuccessResponse) {
                appExecutor.getDiskIO().execute(() -> {
                    saveCallResult((RequestObject)
                            processResponse((ApiResponse.ApiSuccessResponse<RequestObject>)
                                    requestObjectApiResponse));

                    appExecutor.getMainThread().execute(()
                            -> result.addSource(loadFromDb(),
                            cacheObject -> setValue(Resource.success(cacheObject))));
                });
            } else if (requestObjectApiResponse instanceof ApiResponse.ApiEmptyResponse) {
                appExecutor.getMainThread().execute(()
                        -> result.addSource(loadFromDb(), cacheObject
                        -> setValue(Resource.success(cacheObject))));
            } else if (requestObjectApiResponse instanceof ApiResponse.ApiErrorResponse) {
                result.addSource(dbSource, cacheObject
                        -> setValue(
                        Resource.error(
                                ((ApiResponse.ApiErrorResponse<RequestObject>)
                                        requestObjectApiResponse).getMessage(),
                                cacheObject
                        )
                ));
            }
        });

    }

    private CacheObject processResponse(ApiResponse.ApiSuccessResponse<RequestObject> response) {
        return (CacheObject) response.getBody();
    }

    private void setValue(Resource<CacheObject> newValue) {
        if (result.getValue() != newValue) result.setValue(newValue);
    }


    public LiveData<Resource<CacheObject>> getAsLiveData() {
        return result;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestObject item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable CacheObject data);

    @NonNull
    @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestObject>> createCall();

}
