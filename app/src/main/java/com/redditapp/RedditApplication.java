package com.redditapp;

import com.redditapp.dagger.components.ApplicationComponent;
import com.redditapp.dagger.components.DaggerApplicationComponent;
import com.redditapp.dagger.modules.ApplicationModule;
import com.redditapp.util.CrashlyticsTree;
import com.squareup.leakcanary.LeakCanary;

import android.app.Application;
import android.os.StrictMode;

import javax.inject.Inject;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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

            // Log if we're doing anything crazy on the main thread
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        } else {
            Timber.plant(new CrashlyticsTree());
        }

        buildComponentAndInject();
        registerActivityLifecycleCallbacks(activityLifecycleObserver);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


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
