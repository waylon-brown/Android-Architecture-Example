package com.redditapp.base.mvp;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.redditapp.RedditApplication;
import com.redditapp.dagger.RedditAppComponent;

import java.util.UUID;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String BF_UNIQUE_KEY = BaseActivity.class.getName() + ".unique.key";

//    @Inject
//    ViewContainer viewContainer;

    protected ObservableField<String> toolbarTitle = new ObservableField<>();

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
        Registry.add(this, 0, getPresenter()); //viewId(), getPresenter());

        // Data binding
        bindUi();
        toolbarTitle.set(getString(getToolbarTitle()));
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
    protected abstract void bindUi();
    protected abstract void setupViews();
    protected abstract BasePresenter<? extends BaseView> getPresenter();
    @StringRes protected abstract int getToolbarTitle();
}
