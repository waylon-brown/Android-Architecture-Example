package com.redditapp.dagger.application;

import com.redditapp.RedditApplication;
import com.redditapp.ActivityLifecycleObserver;
import com.redditapp.ui.screens.home.HomeActivity;

public interface ApplicationGraph {
    void inject(RedditApplication app);
    void inject(HomeActivity activity);
//    ViewContainer viewContainer();
    //Picasso picasso();
    ActivityLifecycleObserver activityHierarchyServer();
}
