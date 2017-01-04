package com.redditapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import timber.log.Timber;

/** A "view server" adaptation which automatically hooks itself up to all activities. */
public class ActivityLifecycleObserver implements Application.ActivityLifecycleCallbacks {
    @Override public void onActivityCreated(Activity activity, Bundle bundle) {
        Timber.d("%s onActivityCreated", activity.getClass().getSimpleName());
    }
    @Override public void onActivityStarted(Activity activity) {
        Timber.d("%s onActivityStarted", activity.getClass().getSimpleName());
    }
    @Override public void onActivityResumed(Activity activity) {
        Timber.d("%s onActivityResumed", activity.getClass().getSimpleName());
    }
    @Override public void onActivityPaused(Activity activity) {
        Timber.d("%s onActivityPaused", activity.getClass().getSimpleName());
    }
    @Override public void onActivityStopped(Activity activity) {
        Timber.d("%s onActivityStopped", activity.getClass().getSimpleName());
    }
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Timber.d("%s onActivitySaveInstanceState", activity.getClass().getSimpleName());
    }
    @Override public void onActivityDestroyed(Activity activity) {
        Timber.d("%s onActivityDestroyed", activity.getClass().getSimpleName());
    }
}