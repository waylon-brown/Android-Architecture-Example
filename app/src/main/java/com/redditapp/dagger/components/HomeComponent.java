package com.redditapp.dagger.components;

import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.dagger.ActivityScope;
import com.redditapp.screens.home.HomeActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface HomeComponent extends BaseActivityComponent {
    void inject(HomeActivity homeActivity);
    //void inject(HomeFragment homeFragment);
}