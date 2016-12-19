package com.redditapp.base.mvp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.redditapp.RedditApplication;
import com.redditapp.dagger.RedditAppComponent;
import com.redditapp.ui.ViewContainer;

import java.util.UUID;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String BF_UNIQUE_KEY = BaseActivity.class.getName() + ".unique.key";

//    @Inject
//    ViewContainer viewContainer;

    private String uniqueKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle params = getIntent().getExtras();
        if (params != null) {
            onExtractParams(params);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(BF_UNIQUE_KEY)) {
            uniqueKey = savedInstanceState.getString(BF_UNIQUE_KEY);
        } else {
            uniqueKey = UUID.randomUUID().toString();
        }

        super.onCreate(savedInstanceState);

        RedditApplication app = RedditApplication.get(this);
        onCreateComponent(app.getComponent());
//        if (viewContainer == null) {
//            throw new IllegalStateException("You forgot to inject using component.inject(this) in onCreateComponent() of your activity.");
//        }
        Registry.add(this, viewId(), presenter());

        final LayoutInflater layoutInflater = getLayoutInflater();
//        ViewGroup container = viewContainer.forActivity(this);
//        layoutInflater.inflate(layoutId(), container);
        setContentView(getLayoutId());
    }

    // No-op by default
    protected void onExtractParams(@NonNull Bundle params) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BF_UNIQUE_KEY, uniqueKey);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        uniqueKey = savedInstanceState.getString(BF_UNIQUE_KEY);
    }

    public String uniqueKey() {
        return uniqueKey;
    }

    /**
     * Derived activity is responsible to create and store it's component.
     */
    protected abstract void onCreateComponent(RedditAppComponent redditComponent);
    protected abstract @LayoutRes int getLayoutId();
    protected abstract BasePresenter<? extends BaseView> presenter();
    @IdRes protected abstract int viewId();
}
