package kiz.learnwithvel.browser;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BaseActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    @Override
    public void setContentView(int layoutResID) {
        @SuppressLint("InflateParams") ConstraintLayout root = (ConstraintLayout)
                getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout container = root.findViewById(R.id.activity_container);
        mProgressBar = root.findViewById(R.id.progressbar);
        getLayoutInflater().inflate(layoutResID, container, true);
        super.setContentView(root);
    }

    protected void showProgressBar(boolean show) {
        mProgressBar.setVisibility((show) ? View.VISIBLE : View.INVISIBLE);
    }

}
