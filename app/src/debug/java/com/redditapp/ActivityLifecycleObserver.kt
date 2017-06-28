package com.redditapp

import android.app.Activity
import android.app.Application
import android.os.Bundle

import timber.log.Timber

/** A "view server" adaptation which automatically hooks itself up to all activities.  */
class ActivityLifecycleObserver : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, bundle: Bundle) {
        Timber.d("%s onActivityCreated", activity.javaClass.simpleName)
    }

    override fun onActivityStarted(activity: Activity) {
        Timber.d("%s onActivityStarted", activity.javaClass.simpleName)
    }

    override fun onActivityResumed(activity: Activity) {
        Timber.d("%s onActivityResumed", activity.javaClass.simpleName)
    }

    override fun onActivityPaused(activity: Activity) {
        Timber.d("%s onActivityPaused", activity.javaClass.simpleName)
    }

    override fun onActivityStopped(activity: Activity) {
        Timber.d("%s onActivityStopped", activity.javaClass.simpleName)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
        Timber.d("%s onActivitySaveInstanceState", activity.javaClass.simpleName)
    }

    override fun onActivityDestroyed(activity: Activity) {
        Timber.d("%s onActivityDestroyed", activity.javaClass.simpleName)
    }
}