package kiz.learnwithvel.browser.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FlickrBrowserViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public FlickrBrowserViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FlickrBrowserViewModel(application);
    }
}
