package com.redditapp.dagger.components;

import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.dagger.ActivityScope;
import com.redditapp.screens.home.HomeActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
    // Add any fragments that this activity may use
    //void inject(HomeFragment homeFragment);
}