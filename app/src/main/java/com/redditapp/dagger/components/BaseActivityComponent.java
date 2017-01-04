package com.redditapp.dagger.components;

import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.dagger.ActivityScope;

import android.app.Activity;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.  Activity-level components
 * should extend this component.
 *
 * Extensions of this should define injection points for the activity and its fragments.
 */
@ActivityScope // Subtypes of BaseActivityComponent should be decorated with @ActivityScope.
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface BaseActivityComponent {
    Activity activity(); // Expose the activity to sub-graphs.
}