package kiz.learnwithvel.browser.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import kiz.learnwithvel.browser.model.Photo;
import kiz.learnwithvel.browser.persistence.FlickrDao;
import kiz.learnwithvel.browser.persistence.FlickrDatabase;
import kiz.learnwithvel.browser.request.ServiceGenerator;
import kiz.learnwithvel.browser.request.response.ApiResponse;
import kiz.learnwithvel.browser.request.response.PhotoResponse;
import kiz.learnwithvel.browser.util.AppExecutor;
import kiz.learnwithvel.browser.util.Constants;
import kiz.learnwithvel.browser.util.NetworkBoundResource;
import kiz.learnwithvel.browser.util.Resource;

public class FlickrBrowserRepo {

    private static final String TAG = "FlickrBrowserRepo";
    private static FlickrBrowserRepo instance;
    private static final String FORMAT = "json";
    private static final String CALLBACK = "1";
    private static final String TAGMODE = "any";


    private FlickrDao flickrDao;

    public static FlickrBrowserRepo getInstance(final Context context) {
        if (instance == null) {
            instance = new FlickrBrowserRepo(context);
        }
        return instance;
    }

    private FlickrBrowserRepo(final Context context) {
        this.flickrDao = FlickrDatabase.getInstance(context).getFlickrDao();
    }

    public LiveData<Resource<List<Photo>>> retrieveFlickrPhotoApi(String query) {
        return new NetworkBoundResource<List<Photo>, PhotoResponse>(AppExecutor.getInstance()) {

            @Override
            protected void saveCallResult(@NonNull PhotoResponse item) {
                if (item.getPhotoList() != null) {

                    setTimestamp(item.getPhotoList());

                    Photo[] photos = new Photo[item.getPhotoList().size()];

                    int index = 0;
                    for (long rowId : flickrDao.insertPhotos((Photo[]) item.getPhotoList().toArray(photos))) {
                        if (rowId == -1) {
                            flickrDao.updatePhoto(
                                    photos[index].getAuthor_id(),
                                    photos[index].getTitle(),
                                    photos[index].getLink(),
                                    photos[index].getPublished(),
                                    photos[index].getDescription(),
                                    photos[index].getTags(),
                                    photos[index].getMedia()
                            );
                        }
                        index++;
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Photo> data) {

                if (data != null) {
                    if (data.size() == 0) return true;
                    for (Photo photo : data) {
                        if ((photo.getTimestamp() - Constants.CURRENT_TIME_IN_SECONDS)
                                >= Constants.PHOTO_REFRESH_TIME) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<Photo>> loadFromDb() {
                return flickrDao.searchPhoto(query);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PhotoResponse>> createCall() {
                return ServiceGenerator.getFlickrBrowserApi().searchPhotos(
                        FORMAT, CALLBACK, TAGMODE, query);
            }
        }.getAsLiveData();
    }

    private void setTimestamp(List<Photo> photos) {
        for (Photo photo : photos) {
            photo.setTimestamp(Constants.CURRENT_TIME_IN_SECONDS);
        }
    }

}
