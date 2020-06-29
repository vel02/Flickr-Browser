package kiz.learnwithvel.browser.adapter;

import kiz.learnwithvel.browser.model.Photo;

public interface OnPhotoClickListener {

    void onClickPhoto(Photo photo);

    void onClickCategory(String query);

}
