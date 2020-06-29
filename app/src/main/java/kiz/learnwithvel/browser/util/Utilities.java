package kiz.learnwithvel.browser.util;

import androidx.appcompat.widget.SearchView;

public class Utilities {

    public static void clearSearch(SearchView view) {
        view.clearFocus();
        view.setQuery("", false);
        view.setIconified(true);
    }
}
