package kiz.learnwithvel.browser.request;

import java.util.concurrent.TimeUnit;

import kiz.learnwithvel.browser.util.Constants;
import kiz.learnwithvel.browser.util.LiveDataAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addCallAdapterFactory(new LiveDataAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static FlickrBrowserApi flickrBrowserApi = retrofit.create(FlickrBrowserApi.class);

    public static FlickrBrowserApi getFlickrBrowserApi() {
        return flickrBrowserApi;
    }
}
