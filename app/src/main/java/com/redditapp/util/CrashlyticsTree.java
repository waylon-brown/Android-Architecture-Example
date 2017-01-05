package com.redditapp.util;

import android.util.Log;

import timber.log.Timber;

/**
 * A tree which logs important information for crash reporting, and doesn't log Verbose or Debug
 */
public class CrashlyticsTree extends Timber.Tree {
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
