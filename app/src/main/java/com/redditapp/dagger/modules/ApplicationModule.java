package com.redditapp.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.redditapp.ActivityLifecycleObserver;
import com.redditapp.RedditApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Application-wide dependencies.
 */
@Module
public final class ApplicationModule {
    private final RedditApplication app;

    public ApplicationModule(RedditApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    @Named("ApplicationContext")
    Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    ActivityLifecycleObserver provideActivityLifecycleObserver() {
        return new ActivityLifecycleObserver();
    }

    @Provides
    @Singleton
    Realm provideRealm(@Named("ApplicationContext") Context context) {
        Realm.init(context);
        return Realm.getDefaultInstance();
    }
}
