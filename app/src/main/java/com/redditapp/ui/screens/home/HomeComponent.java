package com.redditapp.ui.screens.home;

import com.redditapp.dagger.ActivityScope;
import com.redditapp.dagger.ApplicationComponent;
import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.dagger.modules.HomeModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, HomeModule.class})
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
    // Add any fragments that this activity may use
    //void inject(HomeFragment homeFragment);
}