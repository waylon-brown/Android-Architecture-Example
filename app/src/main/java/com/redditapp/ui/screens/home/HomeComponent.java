package com.redditapp.ui.screens.home;

import com.redditapp.dagger.ActivityScope;
import com.redditapp.dagger.ApplicationComponent;
import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.dagger.modules.HomeModule;
import com.redditapp.ui.screens.home.account.AccountFragment;
import com.redditapp.ui.screens.home.postlist.PostListFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, 
        modules = {ActivityModule.class, HomeModule.class})
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
    // Any fragments that this activity may use
    void inject(PostListFragment fragment);
    void inject(AccountFragment fragment);
}