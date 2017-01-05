package com.redditapp;

import com.redditapp.dagger.components.ApplicationComponent;
import com.redditapp.dagger.components.DaggerApplicationComponent;
import com.redditapp.dagger.modules.ApplicationModule;
import com.redditapp.util.CrashlyticsTree;
import com.squareup.leakcanary.LeakCanary;

import android.app.Application;

import javax.inject.Inject;

import timber.log.Timber;

public class RedditApplication extends Application {

    private static ApplicationComponent component;

    @Inject
    ActivityLifecycleObserver activityLifecycleObserver;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        // Automatically compiles a no-op version if build type is release
        LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashlyticsTree());
        }

        buildComponentAndInject();
        registerActivityLifecycleCallbacks(activityLifecycleObserver);
    }

    public void buildComponentAndInject() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        component.inject(this);
    }

    public static ApplicationComponent getComponent() {
        return component;
    }
}
