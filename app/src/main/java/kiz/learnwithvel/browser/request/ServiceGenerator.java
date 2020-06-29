package kiz.learnwithvel.browser.request;

import kiz.learnwithvel.browser.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static FlickrBrowserApi flickrBrowserApi = retrofit.create(FlickrBrowserApi.class);

    public static FlickrBrowserApi getFlickrBrowserApi() {
        return flickrBrowserApi;
    }
}
