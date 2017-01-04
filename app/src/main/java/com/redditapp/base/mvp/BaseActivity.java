package com.redditapp.base.mvp;

import com.redditapp.dagger.FieldInjector;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public abstract class BaseActivity<T> extends AppCompatActivity
        implements BaseView, FieldInjector<T> {

    private static final String BF_UNIQUE_KEY = BaseActivity.class.getName() + ".unique.key";

    protected ObservableField<String> toolbarTitle = new ObservableField<>();

    private String uniqueKey;
    protected T component;

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
        component = buildComponentAndInject();

        // Data binding
        bindUi();
        toolbarTitle.set(getString(getToolbarTitle()));
    }

    @Override
    protected void onDestroy() {
        // Ends the ActivityScope
        component = null;
        super.onDestroy();
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
    protected abstract void bindUi();
    protected abstract void setupViews();
    @StringRes protected abstract int getToolbarTitle();
}
