package kiz.learnwithvel.browser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    private String author_id;
    private String title;
    private String link;
    private String published;
    private String description;
    private String tags;
    @SerializedName("media")
    @Expose()
    private Media media;
    private int timestamp;

    public Photo(String author_id, String title, String link, String published,
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
    }

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

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
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
