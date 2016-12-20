package com.redditapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.redditapp.dagger.application.ApplicationComponent;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class RedditApplication extends Application {
    private ApplicationComponent component;

//    @Inject\ activityHierarchyServer;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashlyticsTree());
        }

        buildComponentAndInject();
//        registerActivityLifecycleCallbacks(activityHierarchyServer);
    }

    public void buildComponentAndInject() {
        component = ApplicationComponent.Initializer.init(this);
        component.inject(this);
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    public static RedditApplication get(Context context) {
        return (RedditApplication)context.getApplicationContext();
    }

    /** A tree which logs important information for crash reporting. */
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
