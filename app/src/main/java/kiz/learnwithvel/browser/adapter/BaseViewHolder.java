package kiz.learnwithvel.browser.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kiz.learnwithvel.browser.model.Photo;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private static final String DATE_OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private static final String DATE_NEW_FORMAT = "MMMM d, yyyy";
    private String formattedDate;


    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.formattedDate = "";
    }

    protected void formatDate(String input) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat(DATE_OLD_FORMAT, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(DATE_NEW_FORMAT, Locale.ENGLISH);
        Date date = inputFormat.parse(input);
        assert date != null;
        formattedDate = outputFormat.format(date);
    }

    protected abstract void formattingDate(Photo photo);

    protected abstract String format(String media);

    protected abstract void clear();

    public void onBind(Photo photo) {
        this.clear();
    }

    public String getFormattedDate() {
        return formattedDate;
    }
}
