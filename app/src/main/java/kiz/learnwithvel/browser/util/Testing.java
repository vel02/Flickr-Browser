package kiz.learnwithvel.browser.util;

import android.util.Log;

import java.util.List;

import kiz.learnwithvel.browser.adapter.PhotoRecyclerAdapter;
import kiz.learnwithvel.browser.model.Photo;
import kiz.learnwithvel.browser.request.FlickrBrowserApi;
import kiz.learnwithvel.browser.request.ServiceGenerator;
import kiz.learnwithvel.browser.request.response.PhotoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Testing {

    public static void testRequestRetrofit(String query, String tag, PhotoRecyclerAdapter adapter) {
        FlickrBrowserApi api = ServiceGenerator.getFlickrBrowserApi();
        Call<PhotoResponse> responseCall = api.searchPhotos("json", "1", "any", query);

        responseCall.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(tag, "onResponse: " + response.body());
                    if (response.body() != null) {
                        List<Photo> list = response.body().getPhotoList();
                        adapter.addList(list);
                        for (Photo photo : list) {
                            Log.d(tag, "onResponse: " + photo);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {

            }
        });
    }
}
