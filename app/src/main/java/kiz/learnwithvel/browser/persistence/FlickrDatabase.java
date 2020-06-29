package kiz.learnwithvel.browser.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import kiz.learnwithvel.browser.model.Photo;

@Database(entities = {Photo.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class FlickrDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "flickr_db";
    private static FlickrDatabase instance;

    public static FlickrDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    FlickrDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

}
