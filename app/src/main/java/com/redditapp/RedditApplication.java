package com.redditapp;

import com.redditapp.dagger.application.ApplicationComponent;
import com.squareup.leakcanary.LeakCanary;

import android.app.Application;
import android.util.Log;

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
        component = ApplicationComponent.Initializer.init(this);
        component.inject(this);
    }

    public static ApplicationComponent getComponent() {
        return component;
    }

    /** A tree which logs important information for crash reporting, and doesn't log Verbose or Debug */
    //TODO: Also don't log i? Which levels should be logged in release?
    private static class CrashlyticsTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            //TODO: implement Crashlytics
//            FakeCrashLibrary.log(priority, tag, message);
            if (t != null) {
                if (priority == Log.ERROR) {
//                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
//                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
