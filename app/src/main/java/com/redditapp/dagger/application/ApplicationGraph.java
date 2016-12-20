package com.redditapp.dagger.application;

import com.redditapp.RedditApplication;
import com.redditapp.ui.ActivityHierarchyServer;
import com.redditapp.ui.ViewContainer;
import com.redditapp.ui.screens.home.HomeActivity;

public interface ApplicationGraph {
    void inject(RedditApplication app);
    void inject(HomeActivity activity);
//    ViewContainer viewContainer();
    //Picasso picasso();
    ActivityHierarchyServer activityHierarchyServer();
}
