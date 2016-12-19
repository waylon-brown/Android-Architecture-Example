package com.redditapp.dagger;

import com.redditapp.RedditApplication;

import dagger.Component;

@ApplicationScope
@Component(modules = { RedditAppModule.class})
public interface RedditAppComponent extends RedditAppGraph {
    /**
     * An initializer that creates the graph from an application.
     */
    final class Initializer {
        public static RedditAppComponent init(RedditApplication app) {
            return DaggerRedditAppComponent.builder()
                    .redditAppModule(new RedditAppModule(app))
                    .build();
        }
        private Initializer() {} // No instances.
    }
}