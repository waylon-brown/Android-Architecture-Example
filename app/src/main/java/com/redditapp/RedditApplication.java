package com.redditapp;

import com.facebook.stetho.Stetho;
import com.redditapp.dagger.components.ApplicationComponent;
import com.redditapp.dagger.components.DaggerApplicationComponent;
import com.redditapp.dagger.modules.ApplicationModule;
import com.redditapp.util.CrashlyticsTree;
import com.squareup.leakcanary.LeakCanary;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import android.app.Application;

import javax.inject.Inject;

import io.realm.Realm;
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
            
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());
        } else {
            Timber.plant(new CrashlyticsTree());
        }

        buildComponentAndInject();
        registerActivityLifecycleCallbacks(activityLifecycleObserver);

        Realm.init(this);

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
