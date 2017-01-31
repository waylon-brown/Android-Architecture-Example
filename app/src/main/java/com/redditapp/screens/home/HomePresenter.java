package com.redditapp.screens.home;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.redditapp.api.RxApiCallers;
import com.redditapp.base.mvp.BasePresenter;
import com.redditapp.data.RealmDao;
import com.redditapp.data.models.listing.Listing;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

public class HomePresenter extends BasePresenter<HomeActivity> {

	private RxApiCallers rxApiCallers;
	private RealmDao mRealmDao;

	@Inject
	public HomePresenter(RxApiCallers rxApiCallers,
						 RealmDao realmDao) {
		this.rxApiCallers = rxApiCallers;
		this.mRealmDao = realmDao;
	}

	protected void loadListing() {
		getView().showLoading();

		// First get access token, then get main feed
		DisposableSingleObserver observer = rxApiCallers
				.updateCurrentListing()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(new DisposableSingleObserver<Listing>() {
					@Override
					public void onSuccess(Listing value) {
						// Updated list upstream
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