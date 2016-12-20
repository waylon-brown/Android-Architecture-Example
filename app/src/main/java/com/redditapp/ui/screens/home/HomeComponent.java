package com.redditapp.ui.screens.home;

import com.redditapp.ApplicationComponent;
import com.redditapp.PerActivity;
import com.redditapp.base.dagger.ActivityModule;
import com.redditapp.base.dagger.BaseActivityComponent;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface HomeComponent extends BaseActivityComponent {
    void inject(HomeActivity homeActivity);
    //void inject(HomeFragment homeFragment);
}