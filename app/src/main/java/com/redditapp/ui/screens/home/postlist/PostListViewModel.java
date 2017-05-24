package com.redditapp.ui.screens.home.postlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.redditapp.data.api.RxApiCallers;
import com.redditapp.data.models.listing.Listing;

public class PostListViewModel extends ViewModel {

	private RxApiCallers rxApiCallers;
	// Life-cycle aware reactive model
	private LiveData<Listing> listing;

	// TODO: We don't want this in our view, it should be injected into this ViewModel using a
	// ViewModelProvider.Factory like here:
	// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/viewmodel/GithubViewModelFactory.java
	public void init(RxApiCallers rxApiCallers) {
		// TODO: add loading into activity
//		getView().showLoading();

		// This is important since the ViewModel is created per Fragment
		if (this.listing != null) {
			return;
		}
		this.rxApiCallers = rxApiCallers;
		loadNewListing();
		return;
	}

	public void loadNewListing() {
		listing = rxApiCallers.getListing();
	}

	public LiveData<Listing> getListing() {
		return listing;
	}
}