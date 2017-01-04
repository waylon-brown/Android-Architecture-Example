package com.redditapp.dagger.activity;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Dependencies that the activities need, using the
 * {@link PerActivity} scope to conform the lives of the dependencies to the
 * life of the Activity.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return activity;
    }
}
