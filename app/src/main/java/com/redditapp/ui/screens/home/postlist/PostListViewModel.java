package com.redditapp.ui.screens.home.postlist;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.redditapp.data.api.RxApiCallers;
import com.redditapp.data.models.listing.Listing;

import javax.inject.Inject;

import retrofit2.http.HEAD;

/**
 * It is important that the ViewModel must not know about the View.
 */
public class PostListViewModel extends ViewModel {

	private RxApiCallers rxApiCallers;
	// Life-cycle aware reactive model
	private LiveData<Listing> listing;

	// TODO: We don't want this in our view, it should be injected into this ViewModel using a
	// ViewModelProvider.Factory like here:
	// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/viewmodel/GithubViewModelFactory.java
	public void init(LifecycleOwner lifecycleOwner, RxApiCallers rxApiCallers) {
		// TODO: add loading into activity
//		getView().showLoading();

		// This is important since the ViewModel is created per Fragment
		if (this.listing != null) {
			return;
		}
		this.rxApiCallers = rxApiCallers;
		listing = loadNewListing(lifecycleOwner);
	}

	public LiveData<Listing> loadNewListing(LifecycleOwner lifecycleOwner) {
		// TODO: see if this works without setting new listing
		return rxApiCallers.getListing(lifecycleOwner);
	}

	public LiveData<Listing> getListing() {
		return listing;
	}
}