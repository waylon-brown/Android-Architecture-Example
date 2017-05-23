package com.redditapp.data.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.redditapp.BuildConfig;
import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.dagger.modules.BasicAuthNetworkModule;
import com.redditapp.dagger.modules.OauthNetworkModule;
import com.redditapp.data.SharedPrefsHelper;
import com.redditapp.data.models.AccessTokenResponse;
import com.redditapp.data.models.listing.Listing;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import timber.log.Timber;

public class RxApiCallers {

    private static final int API_CALL_TIMEOUT_SECONDS = 10;

    private Retrofit basicAuthRetrofit;
    private Retrofit oauthRetrofit;
    private SharedPrefsHelper sharedPrefsHelper;
    private final Context context;
    private final Moshi moshi;

    @Inject
    public RxApiCallers(@Named(BasicAuthNetworkModule.BASIC_AUTH_HTTP_CLIENT) Retrofit basicAuthRetrofit,
                        @Named(OauthNetworkModule.OAUTH_HTTP_CLIENT) Retrofit oauthRetrofit,
                        SharedPrefsHelper sharedPrefsHelper,
                        @Named(ActivityModule.ACTIVITY_CONTEXT) Context context,
                        Moshi moshi) {
        this.basicAuthRetrofit = basicAuthRetrofit;
        this.oauthRetrofit = oauthRetrofit;
        this.sharedPrefsHelper = sharedPrefsHelper;
        this.context = context;
        this.moshi = moshi;
    }

    public LiveData<Listing> getListing() {
        // Instead uses local JSON file
        if (BuildConfig.FLAVOR.equals("offline")) {
            return getOfflineListingFromJson();
        }
        // First get access token, then get listing
        final MutableLiveData<Listing> liveData = new MutableLiveData<>();
        //TODO: Because we're using Rx, we still need to manage lifecycle
        getUserAccessTokenObservable()
                .flatMap(response -> getRedditFrontPageObservable(response.getAccessToken()))
                .timeout(API_CALL_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .doOnSuccess(listing -> Listing.classifyPosts(listing))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Listing>() {
                    @Override
                    public void onSuccess(Listing listing) {
                        liveData.setValue(listing);
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
                        liveData.setValue(null);
                    }
                });
        return liveData;
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

    private LiveData<Listing> getOfflineListingFromJson() {
        String jsonString = loadJSONFromAsset();
        JsonAdapter<Listing> adapter = moshi.adapter(Listing.class);
        try {
            Listing listing = adapter.fromJson(jsonString);
            MutableLiveData liveData = new MutableLiveData<Listing>();
            liveData.setValue(listing);
            return liveData;
//            return Single.just(listing);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = context.getAssets().open("example_post_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
