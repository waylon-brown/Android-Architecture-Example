package com.redditapp.api;

import com.redditapp.dagger.modules.BasicAuthNetworkModule;
import com.redditapp.dagger.modules.OauthNetworkModule;
import com.redditapp.data.RealmDao;
import com.redditapp.data.models.AccessTokenResponse;
import com.redditapp.data.models.listing.Listing;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RxApiCallers {

    private static final int API_CALL_TIMEOUT_SECONDS = 10;

    private RealmDao mRealmDao;
    private Retrofit basicAuthRetrofit;
    private Retrofit oauthRetrofit;

    @Inject
    public RxApiCallers(RealmDao realmDao,
                        @Named(BasicAuthNetworkModule.BASIC_AUTH_HTTP_CLIENT) Retrofit basicAuthRetrofit,
                        @Named(OauthNetworkModule.OAUTH_HTTP_CLIENT) Retrofit oauthRetrofit) {
        this.mRealmDao = realmDao;
        this.basicAuthRetrofit = basicAuthRetrofit;
        this.oauthRetrofit = oauthRetrofit;
    }

    public Single<Listing> updateCurrentListing() {
        return getUserAccessTokenObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(response -> Single.just(response.getAccessToken()))
                .observeOn(Schedulers.io())
                .flatMap(accessToken -> getRedditFrontPageObservable(accessToken))
                .timeout(API_CALL_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .observeOn(Schedulers.computation())
                .doOnSuccess(listing -> mRealmDao.updateListing(listing));
    }

    /**
     * Return cached token or retrieve a new one if needed
     */
    private Single<AccessTokenResponse> getUserAccessTokenObservable() {
        // Retrieve a new access token, save to DB
        if (mRealmDao.accessTokenExpired()) {
            return basicAuthRetrofit
                    .create(RedditService.class)
                    .getNoUserAccessToken(RedditService.GRANT_TYPE, UUID.randomUUID().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .doOnSuccess(accessTokenResponse -> mRealmDao.updateAccessToken(accessTokenResponse));
        }
        // Re-use stored access token as it hasn't expired
        return Single.just(mRealmDao.getAccessToken())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    private Single<Listing> getRedditFrontPageObservable(String accessToken) {
        return oauthRetrofit.create(RedditService.class)
                .getFrontPageListing("bearer " + accessToken);
    }
}
