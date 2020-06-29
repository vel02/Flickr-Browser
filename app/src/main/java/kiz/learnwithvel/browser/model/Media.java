package kiz.learnwithvel.browser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Media implements Parcelable {

    private String m;

    public Media(String m) {
        this.m = m;
    }

    public Media() {
    }

    protected Media(Parcel in) {
        m = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Media> CREATOR = new Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    @Override
    public String toString() {
        return "Media{" +
                "media='" + m + '\'' +
                '}';
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
}
