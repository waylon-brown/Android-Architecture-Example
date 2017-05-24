package com.redditapp.ui.screens.home.postlist;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.redditapp.data.api.RxApiCallers;
import com.redditapp.data.models.listing.Listing;

import javax.inject.Inject;

/**
 * It is important that the ViewModel must not know about the View.
 */
public class PostListViewModel extends ViewModel {

	private RxApiCallers rxApiCallers;
	// Life-cycle aware reactive model
	private LiveData<Listing> listing;

	@Inject
	PostListViewModel(RxApiCallers rxApiCallers) {
		this.rxApiCallers = rxApiCallers;
	}

	// Makes sure LiveData is set
	public void init(LifecycleOwner lifecycleOwner) {
		// TODO: add loading into activity
//		getView().showLoading();

		// This is important since the ViewModel is created per Fragment
		if (this.listing != null) {
			return;
		}
		listing = rxApiCallers.getListing(lifecycleOwner);
	}

	public void loadNewListing(LifecycleOwner lifecycleOwner) {
		// TODO: see if this works without setting new listing
		rxApiCallers.getListing(lifecycleOwner);
	}

	public LiveData<Listing> getListing() {
		return listing;
	}
}