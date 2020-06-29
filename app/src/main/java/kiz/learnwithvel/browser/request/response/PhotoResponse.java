package kiz.learnwithvel.browser.request.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import kiz.learnwithvel.browser.model.Photo;

public class PhotoResponse {

    @SerializedName("title")
    @Expose()
    private String title;

    @SerializedName("link")
    @Expose()
    private String link;

    @SerializedName("items")
    @Expose()
    private List<Photo> photoList;

    @Override
    public String toString() {
        return "PhotoResponse{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", photoList=" + photoList +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }
}
