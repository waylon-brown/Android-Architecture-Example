package com.redditapp.dagger.modules;

import android.app.Application;

import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
public class MainNetworkModule {

    public static final String BASIC_AUTH_BASE_URL = "https://www.reddit.com/api/v1/";
    public static final String OAUTH_BASE_URL = "https://oauth.reddit.com/";
    public static final String REDDIT_CLIENT_ID = "5FOHjrI8cAlWfw";

    @Provides
    @Singleton
    //TODO: verify good cache size
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Moshi provideMoshi() {
        return new Moshi.Builder().build();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkhttpBuilder(Cache cache) {
        return new OkHttpClient.Builder()
                // Uncomment to add Response logging, TODO: add to debug drawer
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache);

    }
}
