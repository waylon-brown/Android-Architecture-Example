package com.redditapp.dagger;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the activity to be memoized in the
 * correct component.
 *
 * Using an activity scope because it:
 * * Provides the ability to inject objects which require the activity to be constructed.
 * * Allows for the use of singletons on a per-activity basis. This is a great way to manage a resource that is shared by a bunch of fragments in an activity.
 * * Keeps the global object graph clear of things that can be used only by activities.
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}