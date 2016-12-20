package com.redditapp.dagger.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.redditapp.RedditApplication;
import com.redditapp.ActivityLifecycleObserver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Application-wide dependencies.
 */
@Module
public final class ApplicationModule {
    private final RedditApplication app;

    public ApplicationModule(RedditApplication app) {
        this.app = app;
    }

    @Provides @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides @Singleton
    Context provideContext() {
        return app;
    }

    @Provides @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides @Singleton
    ActivityLifecycleObserver provideActivityHierarchyServer() {
        return new ActivityLifecycleObserver();
    }
}
