package com.redditapp.ui.annotation;

import com.redditapp.base.navigation.activity.ActivityScreenSwitcher;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScreenSwitcherServer {
    /**
     * Todo: remove? use it?
     *
     * Was added to {@link com.redditapp.ui.UiModule#provideActivityHierarchyServer(ActivityScreenSwitcher)}
     * initially then removed because of http://stackoverflow.com/a/41228063/2082140
     */
}