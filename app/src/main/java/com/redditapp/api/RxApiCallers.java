package com.redditapp.api;

import com.redditapp.dagger.modules.BasicAuthNetworkModule;
import com.redditapp.dagger.modules.OauthNetworkModule;
import com.redditapp.data.SharedPrefsHelper;
import com.redditapp.data.models.AccessTokenResponse;
import com.redditapp.data.models.listing.Listing;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RxApiCallers {

    private static final int API_CALL_TIMEOUT_SECONDS = 10;

    private Retrofit basicAuthRetrofit;
    private Retrofit oauthRetrofit;
    private SharedPrefsHelper sharedPrefsHelper;

    @Inject
    public RxApiCallers(@Named(BasicAuthNetworkModule.BASIC_AUTH_HTTP_CLIENT) Retrofit basicAuthRetrofit,
                        @Named(OauthNetworkModule.OAUTH_HTTP_CLIENT) Retrofit oauthRetrofit,
                        SharedPrefsHelper sharedPrefsHelper) {
        this.basicAuthRetrofit = basicAuthRetrofit;
        this.oauthRetrofit = oauthRetrofit;
        this.sharedPrefsHelper = sharedPrefsHelper;
    }

    public Single<Listing> getListing() {
        return getUserAccessTokenObservable()
                .flatMap(response -> getRedditFrontPageObservable(response.getAccessToken()))
                .timeout(API_CALL_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .doOnSuccess(listing -> Listing.classifyPosts(listing))
                .subscribeOn(Schedulers.io());
    }

    /**
     * Return cached token or retrieve a new one if needed.
     */
    private Single<AccessTokenResponse> getUserAccessTokenObservable() {
        AccessTokenResponse storedAccessTokenResponse = sharedPrefsHelper.getAccessToken();
        if (storedAccessTokenResponse != null) {
            return Single.just(storedAccessTokenResponse);
        }
        return basicAuthRetrofit
                .create(RedditService.class)
                .getNoUserAccessToken(RedditService.GRANT_TYPE, UUID.randomUUID().toString())
                .doOnSuccess(accessTokenResponse -> sharedPrefsHelper.storeAccessToken(accessTokenResponse));
    }

    private Single<Listing> getRedditFrontPageObservable(String accessToken) {
        return oauthRetrofit.create(RedditService.class)
                .getFrontPageListing("bearer " + accessToken);
    }
}
