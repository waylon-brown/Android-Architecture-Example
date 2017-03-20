package com.redditapp.dagger.modules;

import com.redditapp.dagger.ActivityScope;
import com.redditapp.ui.screens.home.HomeView;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Dependencies that {@link com.redditapp.ui.screens.home.HomeActivity} and its fragments need.
 */
@Module
public class HomeModule {

    /**
     * @param activity is provided by {@link ActivityModule}.
     */
    @Provides
    @ActivityScope
    HomeView activity(Activity activity) {
        return (HomeView)activity;
    }
}
