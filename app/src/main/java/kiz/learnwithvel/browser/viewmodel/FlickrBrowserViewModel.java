package kiz.learnwithvel.browser.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import kiz.learnwithvel.browser.model.Photo;
import kiz.learnwithvel.browser.repository.FlickrBrowserRepo;
import kiz.learnwithvel.browser.util.Resource;

public class FlickrBrowserViewModel extends AndroidViewModel {

    private static final String TAG = "FlickrBrowserViewModel";
    public static final String QUERY_EXHAUSTED = "No more results";

    public enum ViewState {CATEGORY, PHOTO}

    private FlickrBrowserRepo flickrBrowserRepo;

    private MediatorLiveData<Resource<List<Photo>>> photos = new MediatorLiveData<>();
    private MutableLiveData<ViewState> viewState;

    private boolean isPerformingQuery;
    private boolean isQueryExhausted;
    private boolean cancelRequest;
    private String query;

    public FlickrBrowserViewModel(@NonNull Application application) {
        super(application);
        this.flickrBrowserRepo = FlickrBrowserRepo.getInstance(application);
        init();
    }

    private void init() {
        if (viewState == null) {
            viewState = new MutableLiveData<>();
            viewState.setValue(ViewState.CATEGORY);
        }
    }

    public void setViewState(ViewState viewState) {
        this.viewState.setValue(viewState);
    }

    public LiveData<ViewState> getViewState() {
        return viewState;
    }

    public LiveData<Resource<List<Photo>>> getFlickrPhotos() {
        return photos;
    }

    public boolean isQueryExhausted() {
        return isQueryExhausted;
    }

    // ============================== Repository Source ============================== //

    public void retrieveFlickrPhotos(String query) {
        if (!isPerformingQuery) {
            if (query == null) query = "";
            this.query = query;
            this.isQueryExhausted = false;
            executeRetrieval();
        }
    }

    private void executeRetrieval() {
        this.isPerformingQuery = true;
        this.cancelRequest = false;
        this.viewState.setValue(ViewState.PHOTO);
        final LiveData<Resource<List<Photo>>> repositorySource = flickrBrowserRepo.retrieveFlickrPhotoApi(query);
        photos.addSource(repositorySource, listResource -> {
            if (!cancelRequest) {
                if (listResource != null) {
                    photos.setValue(listResource);
                    if (listResource.status == Resource.Status.SUCCESS) {
                        isPerformingQuery = false;
                        checkDataExhausted(listResource);
                        photos.removeSource(repositorySource);
                    } else if (listResource.status == Resource.Status.ERROR) {
                        isPerformingQuery = false;
                        checkDataExhausted(listResource);
                        photos.removeSource(repositorySource);
                    }

                } else {
                    isPerformingQuery = false;
                    photos.removeSource(repositorySource);
                }
            } else photos.removeSource(repositorySource);
        });
    }

    public void cancelRequest() {
        if (isPerformingQuery) {
            this.isPerformingQuery = false;
            this.cancelRequest = true;
            this.query = "";
        }
    }

    private void checkDataExhausted(Resource<List<Photo>> listResource) {
        if (listResource.data != null) {
            if (listResource.data.size() == 0) {
                isQueryExhausted = true;
                photos.setValue(new Resource<>(
                        Resource.Status.ERROR,
                        listResource.data,
                        listResource.message));
            }
        }
    }

    public boolean allowBackNavigation() {
        return viewState.getValue() == ViewState.CATEGORY;
    }


}
