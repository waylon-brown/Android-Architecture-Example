package com.redditapp.base.dagger;

import android.app.Activity;

import com.redditapp.ApplicationComponent;
import com.redditapp.PerActivity;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.  Activity-level components
 * should extend this component.
 *
 * Extensions of this should define injection points for the activity and its fragments.
 */
@PerActivity // Subtypes of BaseActivityComponent should be decorated with @PerActivity.
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface BaseActivityComponent extends BaseComponent {
    Activity activity(); // Expose the activity to sub-graphs.
}