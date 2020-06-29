package kiz.learnwithvel.browser.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static AppExecutor instance;
    private final Executor diskIO = Executors.newSingleThreadExecutor();
    private final Executor mainThread = new MainThreadExecutor();

    private AppExecutor() {
    }

    public static AppExecutor getInstance() {
        if (instance == null) {
            instance = new AppExecutor();
        }
        return instance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {

        private Handler mainThread = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThread.post(command);
        }
    }
}
