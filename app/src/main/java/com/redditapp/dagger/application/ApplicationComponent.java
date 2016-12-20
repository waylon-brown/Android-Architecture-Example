package com.redditapp.dagger.application;

import android.app.Application;

import com.redditapp.DaggerApplicationComponent;
import com.redditapp.RedditApplication;
import com.redditapp.ui.screens.home.HomeActivity;

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
    void inject(HomeActivity activity);

    // Exported for child-components.
    Application application();
//    ActivityHierarchyServer activityHierarchyServer();

    /**
     * An initializer that creates the graph from an application.
     */
    final class Initializer {
        public static ApplicationComponent init(RedditApplication app) {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(app))
                    .build();
        }
        private Initializer() {} // No instances.
    }
}