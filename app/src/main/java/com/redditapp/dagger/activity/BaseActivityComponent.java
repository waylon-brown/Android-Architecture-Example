package com.redditapp.dagger.activity;

import com.redditapp.dagger.application.ApplicationComponent;

import android.app.Activity;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.  Activity-level components
 * should extend this component.
 *
 * Extensions of this should define injection points for the activity and its fragments.
 */
@PerActivity // Subtypes of BaseActivityComponent should be decorated with @PerActivity.
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface BaseActivityComponent {
    Activity activity(); // Expose the activity to sub-graphs.
}