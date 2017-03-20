package com.redditapp.ui.base;

import com.redditapp.dagger.FieldInjector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseFragment<C> extends Fragment 
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
