package com.redditapp.ui.screens.home.postlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.redditapp.data.api.RxApiCallers;
import com.redditapp.data.models.listing.Listing;

public class PostListViewModel extends ViewModel {

	private RxApiCallers rxApiCallers;
	// Life-cycle aware reactive model
	private LiveData<Listing> listing;

	// TODO: It would be better to inject this with Dagger, but that's currently tricky. See:
	// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/viewmodel/GithubViewModelFactory.java

	public void init(RxApiCallers rxApiCallers) {
		this.rxApiCallers = rxApiCallers;
		// Only load completely new listing in init if not already done
		if (this.listing == null) {
			loadNewListing();
		}
	}

	public void loadNewListing() {
		listing = rxApiCallers.getListing();
	}

	public LiveData<Listing> getListing() {
		return listing;
	}
}