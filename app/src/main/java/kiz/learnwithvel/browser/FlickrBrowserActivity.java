package kiz.learnwithvel.browser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.List;

import kiz.learnwithvel.browser.adapter.OnPhotoClickListener;
import kiz.learnwithvel.browser.adapter.PhotoRecyclerAdapter;
import kiz.learnwithvel.browser.model.Photo;
import kiz.learnwithvel.browser.util.Resource;
import kiz.learnwithvel.browser.util.Utilities;
import kiz.learnwithvel.browser.util.VerticalSpacingAdapter;
import kiz.learnwithvel.browser.viewmodel.FlickrBrowserViewModel;

public class FlickrBrowserActivity extends BaseActivity implements OnPhotoClickListener {

    private static final String TAG = "FlickrBrowserActivity";


    @Override
    public void onClickPhoto(Photo photo) {
        Log.d(TAG, "onClick() " + photo);
        Log.d(TAG, "onClickPhoto: " + photo.getMedia().getM());
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("detail", photo);
        intent.putExtra("media", photo.getMedia());
        startActivity(intent);

    }

    @Override
    public void onClickCategory(String query) {
        Log.d(TAG, "onClick() " + query);
        retrieveFlickrPhotos(query);
    }

    private FlickrBrowserViewModel mFlickrBrowserViewModel;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private PhotoRecyclerAdapter mAdapter;

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_browser);
        setSupportActionBar(findViewById(R.id.toolbar));
        mSearchView = findViewById(R.id.search_view);
        mRecyclerView = findViewById(R.id.rv_list);

        // ======= Model ======= //
        ViewModelProvider provider = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication()));
        mFlickrBrowserViewModel = provider.get(FlickrBrowserViewModel.class);

        // ======= Adapter ======= //
        initRecyclerAdapter();

        // ======= Search ======= //
        initSearch();

        // ======= Observer ======= //
        subscribeObservable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_browser, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_categories) {
            Toast.makeText(this, "categories", Toast.LENGTH_SHORT).show();
            if (mFlickrBrowserViewModel.getViewState().getValue() == FlickrBrowserViewModel.ViewState.PHOTO)
                displayCategory();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerAdapter() {
        ViewPreloadSizeProvider<String> preload = new ViewPreloadSizeProvider<>();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new VerticalSpacingAdapter(30));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PhotoRecyclerAdapter(this, setupGlide(), preload);

        RecyclerViewPreloader<String> loader = new RecyclerViewPreloader<>(
                Glide.with(this), mAdapter, preload, 30);

        mRecyclerView.addOnScrollListener(loader);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSearch() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    retrieveFlickrPhotos(query);
                    Utilities.clearSearch(mSearchView);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private RequestManager setupGlide() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);
        return Glide.with(this).setDefaultRequestOptions(options);
    }

    private void retrieveFlickrPhotos(String query) {
        mAdapter.displayOnlyLoading();
        mFlickrBrowserViewModel.retrieveFlickrPhotos(query);
    }

    private void displayCategory() {
        mLayoutManager.scrollToPositionWithOffset(0, 0);
        mAdapter.displayCategory();
    }

    // ==================================  View Model ================================== //

    private void subscribeObservable() {
        mFlickrBrowserViewModel.getFlickrPhotos().observe(this, listResource -> {
            if (listResource != null) {
                if (listResource.data != null) {
                    switch (listResource.status) {
                        case LOADING:
                            mAdapter.displayOnlyLoading();
                            break;
                        case ERROR:
                            Log.d(TAG, "onChanged: cannot refresh the cache.");
                            Log.d(TAG, "onChanged: ERROR message:  " + listResource.message);
                            Log.d(TAG, "onChanged: status ERROR, #recipes:  " + listResource.data.size());
                            mAdapter.hideLoading();
                            mAdapter.addList(listResource.data);
                            Toast.makeText(this, listResource.message, Toast.LENGTH_SHORT).show();
                            isDisplayExhausted(listResource);
                            break;
                        case SUCCESS:
                            Log.d(TAG, "onChanged: cache has been refreshed.");
                            Log.d(TAG, "onChanged: status: SUCCESS, #recipes: " + listResource.data.size());
                            mAdapter.hideLoading();
                            mAdapter.addList(listResource.data);
                            isDisplayExhausted(listResource);
                            break;
                    }
                }
            }
        });

        mFlickrBrowserViewModel.getViewState().observe(this, viewState -> {
            if (viewState != null) {
                switch (viewState) {
                    case PHOTO:
                        break;
                    case CATEGORY:
                        mAdapter.displayCategory();
                        break;
                }
            }
        });
    }

    private void isDisplayExhausted(Resource<List<Photo>> listResource) {
        if (listResource.message != null && mFlickrBrowserViewModel.isQueryExhausted()) {
            mAdapter.displayExhausted();
        }
    }

    @Override
    public void onBackPressed() {
        if (mFlickrBrowserViewModel.getViewState().getValue() == FlickrBrowserViewModel.ViewState.CATEGORY)
            super.onBackPressed();
        else {
            mFlickrBrowserViewModel.cancelRequest();
            displayCategory();
        }

    }
}