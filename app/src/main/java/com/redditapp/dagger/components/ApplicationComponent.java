package com.redditapp.dagger.components;

import com.redditapp.RedditApplication;
import com.redditapp.dagger.modules.ApplicationModule;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = { ApplicationModule.class})
public interface ApplicationComponent {
    // Places to inject
    void inject(RedditApplication app);

    // Exported for child-components.
    Application application();
}