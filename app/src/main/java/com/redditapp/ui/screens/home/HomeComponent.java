package com.redditapp.ui.screens.home;

import com.redditapp.dagger.application.ApplicationComponent;
import com.redditapp.dagger.activity.PerActivity;
import com.redditapp.dagger.activity.ActivityModule;
import com.redditapp.dagger.activity.BaseActivityComponent;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface HomeComponent extends BaseActivityComponent {
    void inject(HomeActivity homeActivity);
    //void inject(HomeFragment homeFragment);
}