package com.redditapp.dagger.modules;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.moshi.Moshi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Used in every call to the Reddit API except the initial one to get the access token, which is taken
 * care of in {@link BasicAuthNetworkModule}.
 */
@Module
public class OauthNetworkModule {

    public static final String OAUTH_BASE_URL = "https://oauth.reddit.com/";
    public static final String OAUTH_HTTP_CLIENT = "Oauth Authentication";   //Used for both Http client and Retrofit

    @Provides
    @Singleton
    @Named(OAUTH_HTTP_CLIENT)
    Retrofit.Builder provideRetrofitBuilder(Moshi moshi) {
        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(OAUTH_BASE_URL);
    }

    @Provides
    @Singleton
    @Named(OAUTH_HTTP_CLIENT)
    OkHttpClient provideOkhttpClient(OkHttpClient.Builder okhttpBuilder) {
        return okhttpBuilder.build();
    }

    @Provides
    @Singleton
    @Named(OAUTH_HTTP_CLIENT)
    Retrofit provideRetrofit(@Named(OAUTH_HTTP_CLIENT) Retrofit.Builder builder, @Named(OAUTH_HTTP_CLIENT) OkHttpClient okHttpClient) {
        return builder
                .client(okHttpClient)
                .build();
    }
}
