package kiz.learnwithvel.browser.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.text.ParseException;

import kiz.learnwithvel.browser.R;
import kiz.learnwithvel.browser.adapter.BaseViewHolder;
import kiz.learnwithvel.browser.adapter.OnPhotoClickListener;
import kiz.learnwithvel.browser.model.Photo;

public class PhotoViewHolder extends BaseViewHolder implements View.OnClickListener {

    private ImageView image;
    private TextView title;
    private TextView published;

    private final OnPhotoClickListener listener;
    private final RequestManager manager;
    private final ViewPreloadSizeProvider<String> preload;
    private Photo photo;

    public PhotoViewHolder(@NonNull View itemView, final OnPhotoClickListener listener,
                           final RequestManager manager,
                           final ViewPreloadSizeProvider<String> preload) {
        super(itemView);
        this.listener = listener;
        this.manager = manager;
        this.preload = preload;
        this.image = itemView.findViewById(R.id.item_photo_image);
        this.title = itemView.findViewById(R.id.item_photo_title);
        this.published = itemView.findViewById(R.id.item_photo_published);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onBind(Photo photo) {
        super.onBind(photo);
        this.photo = photo;

        manager.load(format(photo.getMedia().getM()))
                .into(image);

        formattingDate(photo);

        if (photo.getTitle() != null) this.title.setText(photo.getTitle());
        if (photo.getPublished() != null) this.published.setText(getFormattedDate());

        preload.setView(image);
    }


    @Override
    protected void formattingDate(Photo photo) {
        try {
            formatDate(photo.getPublished());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String format(String media) {
        return media.replace("_m.", "_b.");
    }

    @Override
    protected void clear() {
        this.image.setBackground(ResourcesCompat.getDrawable(
                itemView.getResources(),
                R.drawable.white_background, null));
        this.title.setText("");
        this.published.setText("");
    }

    @Override
    public void onClick(View v) {
        this.listener.onClickPhoto(photo);
    }
}
