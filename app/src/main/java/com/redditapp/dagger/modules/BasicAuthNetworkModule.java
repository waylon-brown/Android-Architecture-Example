package com.redditapp.dagger.modules;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.moshi.Moshi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static com.redditapp.dagger.modules.MainNetworkModule.BASIC_AUTH_BASE_URL;

@Module
public class BasicAuthNetworkModule {

    public static final String BASIC_AUTH_HTTP_CLIENT = "Basic Authentication";    //Used for both Http client and Retrofit

    @Provides
    @Singleton
    @Named(BASIC_AUTH_HTTP_CLIENT)
    Retrofit.Builder provideRetrofitBuilder(Moshi moshi) {
        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASIC_AUTH_BASE_URL);
    }

    @Provides
    @Singleton
    @Named(BASIC_AUTH_HTTP_CLIENT)
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder okHttpBuilder) {
        return okHttpBuilder
                .authenticator((route, response) -> {
                    String credential = Credentials.basic(MainNetworkModule.REDDIT_CLIENT_ID, "");
                    return response.request().newBuilder()
                            .header("Authorization", credential)
                            .build();
                })
                .build();
    }

    @Provides
    @Singleton
    @Named(BASIC_AUTH_HTTP_CLIENT)
    Retrofit provideRetrofit(@Named(BASIC_AUTH_HTTP_CLIENT) Retrofit.Builder builder, @Named(BASIC_AUTH_HTTP_CLIENT) OkHttpClient okHttpClient) {
        return builder
                .client(okHttpClient)
                .build();
    }

}
