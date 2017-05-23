package com.redditapp.ui.screens.home.postlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.redditapp.data.api.RxApiCallers;
import com.redditapp.data.models.listing.Listing;

import javax.inject.Inject;

public class PostListViewModel extends ViewModel {

	private RxApiCallers rxApiCallers;
	// Life-cycle aware reactive model
	private LiveData<Listing> listing;

	@Inject
	PostListViewModel(RxApiCallers rxApiCallers) {
		this.rxApiCallers = rxApiCallers;
	}

	// Makes sure LiveData is set
	public PostListViewModel init() {
		// TODO: add loading into activity
//		getView().showLoading();

		// This is important since the ViewModel is created per Fragment
		if (this.listing != null) {
			return this;
		}
		loadNewListing();
		return this;
	}

	public void loadNewListing() {
		listing = rxApiCallers.getListing();
	}

	public LiveData<Listing> getListing() {
		return listing;
	}
}