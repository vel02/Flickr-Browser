package kiz.learnwithvel.browser.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kiz.learnwithvel.browser.R;
import kiz.learnwithvel.browser.adapter.viewholder.CategoriesViewHolder;
import kiz.learnwithvel.browser.adapter.viewholder.ExhaustedViewHolder;
import kiz.learnwithvel.browser.adapter.viewholder.LoadingViewHolder;
import kiz.learnwithvel.browser.adapter.viewholder.PhotoViewHolder;
import kiz.learnwithvel.browser.model.Photo;
import kiz.learnwithvel.browser.util.Constants;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder>
        implements ListPreloader.PreloadModelProvider<String> {

    private static final String TAG = "PhotoRecyclerAdapter";

    private static final String INDICATOR_VIEW_LOADING = "kiz.learnwithvel.browser.LOADING";
    private static final String INDICATOR_VIEW_CATEGORY = "kiz.learnwithvel.browser.CATEGORY";
    private static final String INDICATOR_VIEW_EXHAUSTED = "kiz.learnwithvel.browser.EXHAUSTED";
    private static final int VIEW_TYPE_PHOTO = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private static final int VIEW_TYPE_CATEGORY = 3;
    private static final int VIEW_TYPE_EXHAUSTED = 4;

    private List<Photo> mPhotoList;
    private OnPhotoClickListener mListener;
    private RequestManager mRequestManager;
    private ViewPreloadSizeProvider<String> mPreload;

    public PhotoRecyclerAdapter(OnPhotoClickListener listener, RequestManager requestManager, ViewPreloadSizeProvider<String> preload) {
        mListener = listener;
        mRequestManager = requestManager;
        mPreload = preload;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_LOADING:
                return new LoadingViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_loading_list_item, parent, false));
            case VIEW_TYPE_EXHAUSTED:
                return new ExhaustedViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_exhausted_list_item, parent, false));
            case VIEW_TYPE_CATEGORY:
                return new CategoriesViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_category_list_item, parent, false),
                        mListener, mRequestManager);
            case VIEW_TYPE_PHOTO:
            default:
                return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_photo_list_item, parent, false),
                        mListener, mRequestManager, mPreload);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        int view = getItemViewType(position);
        if (view == VIEW_TYPE_PHOTO)
            holder.onBind(mPhotoList.get(position));
        else if (view == VIEW_TYPE_CATEGORY)
            holder.onBind(mPhotoList.get(position));
    }

    @Override
    public int getItemCount() {
        return ((mPhotoList != null)
                && (mPhotoList.size() > 0)
                ? mPhotoList.size() : 0);
    }

    public void addList(List<Photo> list) {
        mPhotoList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mPhotoList.get(position).getTitle()) {
            case INDICATOR_VIEW_LOADING:
                return VIEW_TYPE_LOADING;
            case INDICATOR_VIEW_CATEGORY:
                return VIEW_TYPE_CATEGORY;
            case INDICATOR_VIEW_EXHAUSTED:
                return VIEW_TYPE_EXHAUSTED;
        }
        return VIEW_TYPE_PHOTO;
    }

    public void displayCategory() {
        clearPhotoList();
        for (int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORIES.length; i++) {
            Photo photo = new Photo();
            photo.setTitle(INDICATOR_VIEW_CATEGORY);
            photo.setDescription(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            photo.setLink(Constants.DEFAULT_SEARCH_CATEGORIES_IMAGES[i]);
            mPhotoList.add(photo);
        }
        notifyDataSetChanged();
    }

    public void displayOnlyLoading() {
        clearPhotoList();
        Photo loading = new Photo();
        loading.setTitle(INDICATOR_VIEW_LOADING);
        mPhotoList.add(loading);
        notifyDataSetChanged();
    }

    public void displayExhausted() {
        clearPhotoList();
        Photo exhausted = new Photo();
        exhausted.setTitle(INDICATOR_VIEW_EXHAUSTED);
        mPhotoList.add(exhausted);
        notifyDataSetChanged();
    }

    public void hideLoading() {
        if (isLoading()) {
            if (mPhotoList.get(0).getTitle().equals(INDICATOR_VIEW_LOADING)) {
                mPhotoList.remove(0);
            } else if (mPhotoList.get(mPhotoList.size() - 1).getTitle()
                    .equals(INDICATOR_VIEW_LOADING)) {
                mPhotoList.remove(mPhotoList.size() - 1);
            }
            notifyDataSetChanged();
        }
    }

    private void clearPhotoList() {
        if (mPhotoList == null) {
            mPhotoList = new ArrayList<>();
        } else mPhotoList.clear();
        notifyDataSetChanged();
    }

    private boolean isLoading() {
        if (mPhotoList != null) {
            if (mPhotoList.size() > 0) {
                return (mPhotoList.get(mPhotoList.size() - 1).getTitle().equals(INDICATOR_VIEW_LOADING));
            }
        }
        return false;
    }

    @NonNull
    @Override
    public List<String> getPreloadItems(int position) {
        String url = "";
        if (mPhotoList.get(position).getMedia() != null) {
            url = mPhotoList.get(position).getMedia().getM();
        }
        if (TextUtils.isEmpty(url)) {
            return Collections.emptyList();
        } else return Collections.singletonList(url);

    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull String item) {
        return mRequestManager.load(item);
    }
}
