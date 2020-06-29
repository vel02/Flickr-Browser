package kiz.learnwithvel.browser;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kiz.learnwithvel.browser.adapter.OnPhotoClickListener;
import kiz.learnwithvel.browser.adapter.PhotoRecyclerAdapter;
import kiz.learnwithvel.browser.util.Utilities;

import static kiz.learnwithvel.browser.util.Testing.testRequestRetrofit;

public class FlickrBrowserActivity extends BaseActivity implements OnPhotoClickListener {

    private static final String TAG = "FlickrBrowserActivity";


    @Override
    public void onClick(int position) {
        Log.d(TAG, "onClick() " + position);

    }

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
        // ======= Adapter ======= //
        initRecyclerAdapter();

        // ======= Search ======= //
        initSearch();

        // ======= Test ======= //
        testRequestRetrofit("luffy", TAG, mAdapter);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerAdapter() {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PhotoRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSearch() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    testRequestRetrofit(query, TAG, mAdapter);
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


}