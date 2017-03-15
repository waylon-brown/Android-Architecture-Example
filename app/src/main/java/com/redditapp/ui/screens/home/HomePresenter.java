package com.redditapp.ui.screens.home;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.redditapp.data.api.RxApiCallers;
import com.redditapp.ui.base.BasePresenter;
import com.redditapp.data.models.listing.Listing;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class HomePresenter extends BasePresenter<HomeView> {

	private RxApiCallers rxApiCallers;

	@Inject
	public HomePresenter(RxApiCallers rxApiCallers) {
		this.rxApiCallers = rxApiCallers;
	}

	protected void loadListing() {
		getView().showLoading();

		// First get access token, then get main feed
		DisposableSingleObserver observer = rxApiCallers
				.getListing()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(new DisposableSingleObserver<Listing>() {
					@Override
					public void onSuccess(Listing listing) {
						getView().showContent(listing);
					}

					@Override
					public void onError(Throwable e) {
						Timber.e(e);
						if (e instanceof HttpException) {
							HttpException exception = (HttpException)e;
							if (exception.code() == 403) {
								// Shouldn't happen
								Timber.wtf("403 - Access code was out of date.");
							}
						}
						getView().showError(e);
					}
				});
		disposables.add(observer);
	}
}