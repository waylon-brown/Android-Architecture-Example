package com.redditapp.screens.home;

import com.redditapp.api.RedditService;
import com.redditapp.base.mvp.BasePresenter;
import com.redditapp.dagger.modules.BasicAuthNetworkModule;
import com.redditapp.dagger.modules.OauthNetworkModule;
import com.redditapp.data.RealmManager;
import com.redditapp.data.models.AccessTokenResponse;
import com.redditapp.data.models.listing.Listing;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import timber.log.Timber;

public class HomePresenter extends BasePresenter<HomeActivity> {

	private Retrofit basicAuthRetrofit;
	private Retrofit oauthRetrofit;
	private RealmManager realmManager;

	@Inject
	public HomePresenter(@Named(BasicAuthNetworkModule.BASIC_AUTH_HTTP_CLIENT) Retrofit basicAuthRetrofit,
						 @Named(OauthNetworkModule.OAUTH_HTTP_CLIENT) Retrofit oauthRetrofit,
						 RealmManager realmManager) {
		this.basicAuthRetrofit = basicAuthRetrofit;
		this.oauthRetrofit = oauthRetrofit;
		this.realmManager = realmManager;
	}

	@Override
	protected void onLoad() {

		// First get access token, then get main feed
		DisposableSingleObserver observer = getUserAccessTokenObservable()
				.flatMap(response -> getRedditFrontPageObservable(response.accessToken))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(new DisposableSingleObserver<Listing>() {
					@Override
					public void onSuccess(Listing value) {
//							  getView().showContent(value);
						realmManager.updateListingAsync(value);
					}

					@Override
					public void onError(Throwable e) {
						Timber.e(e);
						getView().showError(e);
					}
				});
		disposables.add(observer);
	}

	/**
	 * Return cached token or retrieve a new one if needed
	 * <p>
	 * TODO: cache access token
	 */
	private Single<AccessTokenResponse> getUserAccessTokenObservable() {
		return basicAuthRetrofit.create(RedditService.class)
				.getNoUserAccessToken(RedditService.GRANT_TYPE, UUID.randomUUID().toString());
	}

	private Single<Listing> getRedditFrontPageObservable(String accessToken) {
		return oauthRetrofit.create(RedditService.class)
				.getFrontPageListing("bearer " + accessToken);
	}
}