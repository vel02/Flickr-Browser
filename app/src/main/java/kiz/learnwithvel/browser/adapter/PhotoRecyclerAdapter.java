package kiz.learnwithvel.browser.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kiz.learnwithvel.browser.R;
import kiz.learnwithvel.browser.adapter.viewholder.PhotoViewHolder;
import kiz.learnwithvel.browser.model.Photo;

public class PhotoRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = "PhotoRecyclerAdapter";

    private List<Photo> mPhotoList;
    private OnPhotoClickListener mListener;

    public PhotoRecyclerAdapter(OnPhotoClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_photo_list_item, parent, false);
        return new PhotoViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
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

}
