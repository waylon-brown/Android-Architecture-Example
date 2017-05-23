package com.redditapp.ui.base;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.redditapp.dagger.FieldInjector;

public abstract class BaseFragment<C> extends LifecycleFragment
        implements BaseView, FieldInjector {
    
    protected C component;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponentAndInject();
    }

    @Override
    public void onDestroy() {
        // Ends the ActivityScope
        component = null;
        super.onDestroy();
    }
    
    protected abstract void setupViews();
}
