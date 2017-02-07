package com.redditapp.dagger.modules;

import com.redditapp.util.jsonadapters.PostListJsonAdapter;
import com.redditapp.util.jsonadapters.PreviewImageListJsonAdapter;
import com.squareup.moshi.Moshi;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Main dependencies for {@link OkHttpClient} and {@link retrofit2.Retrofit}.
 * 
 * Used in {@link BasicAuthNetworkModule} and {@link OauthNetworkModule}.
 */
@Module
public class MainNetworkModule {

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
        return new Moshi.Builder()
                .add(new PostListJsonAdapter())
                .add(new PreviewImageListJsonAdapter())
                .build();
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
