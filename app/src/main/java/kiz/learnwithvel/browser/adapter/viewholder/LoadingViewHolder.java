package kiz.learnwithvel.browser.adapter.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import kiz.learnwithvel.browser.adapter.BaseViewHolder;
import kiz.learnwithvel.browser.model.Photo;

public class LoadingViewHolder extends BaseViewHolder {
    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    protected void formattingDate(Photo photo) {

    }

    @Override
    protected String format(String media) {
        return null;
    }

    @Override
    protected void clear() {

    }
}
