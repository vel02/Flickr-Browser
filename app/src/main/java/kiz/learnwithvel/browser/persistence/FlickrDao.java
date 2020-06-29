package kiz.learnwithvel.browser.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kiz.learnwithvel.browser.model.Media;
import kiz.learnwithvel.browser.model.Photo;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FlickrDao {

    @Insert(onConflict = IGNORE)
    long[] insertPhotos(Photo... photos);

    @Insert(onConflict = REPLACE)
    void insertPhoto(Photo photo);

    @Query("UPDATE photos SET title = :title, link = :link, published = :published, " +
            "description = :description, tags = :tags, media = :media " +
            "WHERE author_id = :author_id")
    void updatePhoto(String author_id, String title, String link, String published,
                     String description, String tags, Media media);

    @Query("SELECT * FROM photos WHERE tags LIKE '%'|| :query || '%' ORDER BY published")
    LiveData<List<Photo>> searchPhoto(String query);

}
