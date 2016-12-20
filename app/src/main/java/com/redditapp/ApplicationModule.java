package com.redditapp;

import android.app.Application;

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

    /**
     * Expose the application to the graph.
     */
    @Provides @Singleton
    Application provideApplication() {
        return app;
    }
}
