package com.redditapp.dagger.activity;

/**
 * Created by Waylon on 12/20/2016.
 */

public interface HasComponent<T extends BaseComponent> {
    T component();
    void inject();
}
