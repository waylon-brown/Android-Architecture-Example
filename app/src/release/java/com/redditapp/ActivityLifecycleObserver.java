package com.redditapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/** A "view server" adaptation which automatically hooks itself up to all activities.
 *
 * No-op version for release builds. This wasn't necessary because Timber is set up to not log
 * debug level for release anyways, it was mostly done to create the source sets and test that
 * they work.
 * */
public class ActivityLifecycleObserver implements Application.ActivityLifecycleCallbacks {
    @Override public void onActivityCreated(Activity activity, Bundle bundle) { }
    @Override public void onActivityStarted(Activity activity) { }
    @Override public void onActivityResumed(Activity activity) { }
    @Override public void onActivityPaused(Activity activity) { }
    @Override public void onActivityStopped(Activity activity) { }
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle bundle) { }
    @Override public void onActivityDestroyed(Activity activity) { }
}