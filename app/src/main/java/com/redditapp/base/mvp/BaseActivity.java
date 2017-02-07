package com.redditapp.base.mvp;

import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.redditapp.dagger.FieldInjector;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

    protected ObservableField<String> toolbarTitle = new ObservableField<>();

    protected C component;
    @Inject protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle params = getIntent().getExtras();
        if (params != null) {
            onExtractParams(params);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // No-op by default
    protected void onExtractParams(@NonNull Bundle params) {
    }
    /**
     * Derived activity is responsible to create and store it's component.
     */
    protected abstract void bindUi();
    protected abstract void setupViews();

    @StringRes protected abstract int getToolbarTitle();
}
