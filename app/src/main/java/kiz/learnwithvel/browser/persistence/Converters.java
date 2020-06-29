package kiz.learnwithvel.browser.persistence;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import kiz.learnwithvel.browser.model.Media;

public class Converters {

    @TypeConverter
    public Media fromString(String value) {
        Type type = new TypeToken<Media>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public String fromMedia(Media media) {
        return new Gson().toJson(media);
    }

}
