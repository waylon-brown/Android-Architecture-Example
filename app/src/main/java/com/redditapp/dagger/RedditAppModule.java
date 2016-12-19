package com.redditapp.dagger;

import android.app.Activity;
import android.app.Application;

import com.redditapp.RedditApplication;
import com.redditapp.base.navigation.activity.ActivityScreenSwitcher;
import com.redditapp.ui.ActivityHierarchyServer;

import dagger.Module;
import dagger.Provides;

@Module
public final class RedditAppModule {
    private final RedditApplication app;

    public RedditAppModule(RedditApplication app) {
        this.app = app;
    }

    @Provides
    @ApplicationScope
    Application provideApplication() {
        return app;
    }

    /**
     * TODO: add these below to a UI module
     */

    @Provides
    @ApplicationScope
    ActivityScreenSwitcher provideActivityScreenSwitcher() {
        return new ActivityScreenSwitcher();
    }

    @Provides
    @ApplicationScope
    ActivityHierarchyServer provideActivityHierarchyServer(final ActivityScreenSwitcher screenSwitcher) {
        return new ActivityHierarchyServer.Empty() {
            @Override
            public void onActivityStarted(Activity activity) {
                screenSwitcher.attach(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                screenSwitcher.detach();
            }
        };
    }
}
