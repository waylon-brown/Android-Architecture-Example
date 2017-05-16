package com.redditapp.ui.screens.comments;

import com.redditapp.dagger.ActivityScope;
import com.redditapp.dagger.ApplicationComponent;
import com.redditapp.dagger.modules.ActivityModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface CommentsComponent {
	void inject(CommentsActivity commentsActivity);
	
	// Add any fragments that this activity may use
	//void inject(HomeFragment homeFragment);
}