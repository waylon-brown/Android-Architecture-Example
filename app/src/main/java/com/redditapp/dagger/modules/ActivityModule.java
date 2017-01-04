package com.redditapp.dagger.modules;

import com.redditapp.dagger.ActivityScope;

import android.app.Activity;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dependencies that the activities need, using the
 * {@link ActivityScope} scope to conform the lives of the dependencies to the
 * life of the Activity.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity activity() {
        return activity;
    }

    @Provides
    @ActivityScope
    @Named("ActivityContext")
    Context provideContext() {
        return activity;
    }
}
