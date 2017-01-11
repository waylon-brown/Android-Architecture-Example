package com.redditapp.dagger.modules;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.moshi.Moshi;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://www.reddit.com/api/v1/";
    private static final String REDDIT_CLIENT_ID = "5FOHjrI8cAlWfw";

    // Named providers
    public static final String NO_AUTH_HTTP_CLIENT = "No Authentication";   //Used for both Http client and Retrofit
    public static final String BASIC_AUTH_HTTP_CLIENT = "Basic Authentication - No User";    //Used for both Http client and Retrofit

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

    // Used to make multiple client types
    @Provides
    @Singleton
    OkHttpClient.Builder provideOkhttpBuilder(Cache cache) {
        return new OkHttpClient.Builder()
                // Uncomment to add Response logging, TODO: add to debug drawer
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache);

    }

    @Provides
    @Singleton
    @Named(NO_AUTH_HTTP_CLIENT)
    OkHttpClient provideOkhttpClient(OkHttpClient.Builder okhttpBuilder) {
        return okhttpBuilder.build();
    }

    @Provides
    @Singleton
    @Named(BASIC_AUTH_HTTP_CLIENT)
    OkHttpClient provideAuthenticatedOkhttpClient(OkHttpClient.Builder okHttpBuilder) {
        return okHttpBuilder
                .authenticator((route, response) -> {
                    String credential = Credentials.basic(REDDIT_CLIENT_ID, "");
                    return response.request().newBuilder()
                            .header("Authorization", credential)
                            .build();
                })
                .build();
    }

    // Used to make multiple client types
    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(Moshi moshi) {
        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL);
    }

    @Provides
    @Singleton
    @Named(NO_AUTH_HTTP_CLIENT)
    Retrofit provideRetrofit(Retrofit.Builder builder, @Named(NO_AUTH_HTTP_CLIENT) OkHttpClient okHttpClient) {
        return builder
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @Named(BASIC_AUTH_HTTP_CLIENT)
    Retrofit provideAuthenticatedRetrofit(Retrofit.Builder builder, @Named(BASIC_AUTH_HTTP_CLIENT) OkHttpClient okHttpClient) {
        return builder
                .client(okHttpClient)
                .build();
    }
}
