package com.redditapp

import android.app.Application
import android.os.StrictMode
import com.redditapp.dagger.ApplicationComponent
import com.redditapp.dagger.DaggerApplicationComponent
import com.redditapp.dagger.modules.ApplicationModule
import com.redditapp.util.CrashlyticsTree
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class RedditApplication : Application() {

    // Allow static access
    companion object {
        lateinit var component: ApplicationComponent
    }
    
    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        // Automatically compiles a no-op version if build type is release
        LeakCanary.install(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            // Log if we're doing anything crazy on the main thread
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build())
        } else {
            Timber.plant(CrashlyticsTree())
        }

        buildComponentAndInject()
        registerActivityLifecycleCallbacks(ActivityLifecycleObserver())

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    fun buildComponentAndInject() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        component.inject(this)
    }
}
