package com.redditapp.base.mvp;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.redditapp.dagger.FieldInjector;

import java.util.UUID;

import javax.inject.Inject;

/**
 * All activities extending this have a Dagger component, as well as a presenter
 * to separate business logic from views and keep code testable. They also perform
 * field injection from {@link FieldInjector} since they are created by the
 * framework and therefore can't do constructor injection.
 *
 * @param <C> Dagger component containing dependencies that exist during the
 *           lifecycle of the activity
 * @param <P> Presenter for the activity
 */
public abstract class BaseActivity<C, P extends BasePresenter> extends AppCompatActivity
        implements BaseView, FieldInjector<C> {

    private static final String BF_UNIQUE_KEY = BaseActivity.class.getName() + ".unique.key";

    protected ObservableField<String> toolbarTitle = new ObservableField<>();

    private String uniqueKey;
    protected C component;
    @Inject protected P presenter;

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
        presenter.takeView(this);
        bindUi();
        toolbarTitle.set(getString(getToolbarTitle()));
    }

    @Override
    protected void onDestroy() {
        // Ends the ActivityScope
        component = null;
        presenter.dropView(this);
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
