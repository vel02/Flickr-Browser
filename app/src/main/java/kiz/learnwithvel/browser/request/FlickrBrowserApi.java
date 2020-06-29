package kiz.learnwithvel.browser.request;

import androidx.lifecycle.LiveData;

import kiz.learnwithvel.browser.request.response.ApiResponse;
import kiz.learnwithvel.browser.request.response.PhotoResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrBrowserApi {

    @GET("photos_public.gne")
    LiveData<ApiResponse<PhotoResponse>> searchPhotos(
            @Query("format") String format,
            @Query("nojsoncallback") String nojsoncallback,
            @Query("tagmode") String tagmode,
            @Query("tags") String query
    );

}
