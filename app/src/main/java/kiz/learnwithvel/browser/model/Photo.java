package kiz.learnwithvel.browser.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "photos")
public class Photo implements Parcelable {

    @PrimaryKey
    @NonNull
    private String author_id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "link")
    private String link;
    @ColumnInfo(name = "published")
    private String published;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "tags")
    private String tags;
    @ColumnInfo(name = "media")
    @SerializedName("media")
    @Expose()
    private Media media;
    @ColumnInfo(name = "timestamp")
    private int timestamp;

    public Photo(@NonNull String author_id, String title, String link, String published,
                 String description, String tags, Media media, int timestamp) {
        this.author_id = author_id;
        this.title = title;
        this.link = link;
        this.published = published;
        this.description = description;
        this.tags = tags;
        this.media = media;
        this.timestamp = timestamp;
    }

    public Photo() {
        author_id = "";
    }

    protected Photo(Parcel in) {
        author_id = Objects.requireNonNull(in.readString());
        title = in.readString();
        link = in.readString();
        published = in.readString();
        description = in.readString();
        tags = in.readString();
        timestamp = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author_id);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(published);
        dest.writeString(description);
        dest.writeString(tags);
        dest.writeInt(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "Photo{" +
                "author_id='" + author_id + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", published='" + published + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", media=" + media +
                ", timestamp=" + timestamp +
                '}';
    }

    @NonNull
    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(@NonNull String author_id) {
        this.author_id = author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
