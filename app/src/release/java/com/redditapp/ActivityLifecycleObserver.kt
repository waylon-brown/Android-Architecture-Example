package com.redditapp

import android.app.Activity
import android.app.Application
import android.os.Bundle

/** A "view server" adaptation which automatically hooks itself up to all activities.

 * No-op version for release builds. This wasn't necessary because Timber is set up to not log
 * debug level for release anyways, it was mostly done to create the source sets and test that
 * they work.
 */
class ActivityLifecycleObserver : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, bundle: Bundle) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}