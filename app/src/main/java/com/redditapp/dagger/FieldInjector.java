package com.redditapp.dagger;

/**
 * Force implementers to build the component and inject the class to perform
 * field injection (or method injection).
 *
 * Should only be used on classes that are instantiated by outside frameworks,
 * such as activities, as we can use constructor injection for everything else.
 */

public interface FieldInjector<T> {

    /**
     * Must also inject itself into the component before returning.
     */
    T buildComponentAndInject();
}
