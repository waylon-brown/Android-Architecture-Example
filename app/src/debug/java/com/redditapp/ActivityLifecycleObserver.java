package com.redditapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import timber.log.Timber;

/** A "view server" adaptation which automatically hooks itself up to all activities. */
public class ActivityLifecycleObserver implements Application.ActivityLifecycleCallbacks {
    @Override public void onActivityCreated(Activity activity, Bundle bundle) {
        Timber.d("LIFECYCLE: %s onActivityCreated", activity.getClass().getName());
    }
    @Override public void onActivityStarted(Activity activity) {
        Timber.d("LIFECYCLE: %s onActivityStarted", activity.getClass().getName());
    }
    @Override public void onActivityResumed(Activity activity) {
        Timber.d("LIFECYCLE: %s onActivityResumed", activity.getClass().getName());
    }
    @Override public void onActivityPaused(Activity activity) {
        Timber.d("LIFECYCLE: %s onActivityPaused", activity.getClass().getName());
    }
    @Override public void onActivityStopped(Activity activity) {
        Timber.d("LIFECYCLE: %s onActivityStopped", activity.getClass().getName());
    }
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Timber.d("LIFECYCLE: %s onActivitySaveInstanceState", activity.getClass().getName());
    }
    @Override public void onActivityDestroyed(Activity activity) {
        Timber.d("LIFECYCLE: %s onActivityDestroyed", activity.getClass().getName());
    }
}