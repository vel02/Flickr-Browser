package kiz.learnwithvel.browser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import kiz.learnwithvel.browser.model.Media;
import kiz.learnwithvel.browser.model.Photo;

public class PhotoDetailActivity extends BaseActivity {

    private static final String TAG = "PhotoDetailActivity";

    private TextView author;
    private TextView title;
    private TextView tags;

    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        this.author = findViewById(R.id.tv_photo_author);
        this.title = findViewById(R.id.tv_photo_title);
        this.tags = findViewById(R.id.tv_photo_tags);
        this.image = findViewById(R.id.imv_photo_thumbnail);

        getPhotoDetailIntent();
    }

    private void getPhotoDetailIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Photo photo = intent.getParcelableExtra("detail");
            Media media = intent.getParcelableExtra("media");
            if (photo != null && media != null) {
                this.author.setText(photo.getAuthor_id());
                this.title.setText(photo.getTitle());
                this.tags.setText(photo.getTags());


                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.white_background)
                        .error(R.drawable.white_background);

                Glide.with(this)
                        .setDefaultRequestOptions(options)
                        .load(format(media.getM()))
                        .into(image);
            }
        }
    }

    protected String format(String media) {
        return media.replace("_m.", "_b.");
    }
}
