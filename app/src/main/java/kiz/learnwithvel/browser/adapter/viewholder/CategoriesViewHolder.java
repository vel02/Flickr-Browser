package kiz.learnwithvel.browser.adapter.viewholder;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.RequestManager;

import de.hdodenhof.circleimageview.CircleImageView;
import kiz.learnwithvel.browser.R;
import kiz.learnwithvel.browser.adapter.BaseViewHolder;
import kiz.learnwithvel.browser.adapter.OnPhotoClickListener;
import kiz.learnwithvel.browser.model.Photo;
import kiz.learnwithvel.browser.util.Constants;

public class CategoriesViewHolder extends BaseViewHolder implements View.OnClickListener {

    private CircleImageView image;
    private TextView title;

    private final OnPhotoClickListener listener;
    private final RequestManager manager;

    public CategoriesViewHolder(@NonNull View itemView, final OnPhotoClickListener listener, final RequestManager manager) {
        super(itemView);
        this.listener = listener;
        this.manager = manager;
        this.image = itemView.findViewById(R.id.category_image);
        this.title = itemView.findViewById(R.id.category_title);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onBind(Photo photo) {
        super.onBind(photo);
        Uri path = Uri.parse("android.resource://" + itemView.getContext().getPackageName() + "/drawable/" + photo.getLink());
        manager.load(path).into(image);
        this.title.setText(photo.getDescription());
    }

    @Override
    protected void formattingDate(Photo photo) {

    }

    @Override
    protected String format(String media) {
        return null;
    }

    @Override
    protected void clear() {
        this.image.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(),
                R.drawable.white_background, null));
        this.title.setText("");
    }

    @Override
    public void onClick(View v) {
        listener.onClickCategory(Constants.DEFAULT_SEARCH_CATEGORIES[getAdapterPosition()]);
    }
}
