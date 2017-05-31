package com.redditapp.ui.screens.home.postlist;

import android.arch.lifecycle.ViewModel;

import com.redditapp.data.api.RxApiCallers;
import com.redditapp.data.models.listing.Listing;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * It is important that the ViewModel must not know about the View.
 */
public class PostListViewModel extends ViewModel {

	// TODO: It would be better to inject this with Dagger, but that's currently tricky. See:
	// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/viewmodel/GithubViewModelFactory.java
	public Single<Listing> getListing(RxApiCallers rxApiCallers) {
		return rxApiCallers
				.getListing()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}