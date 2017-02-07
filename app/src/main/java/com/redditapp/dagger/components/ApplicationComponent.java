package com.redditapp.dagger.components;

import android.app.Application;
import android.content.Context;

import com.redditapp.RedditApplication;
import com.redditapp.dagger.modules.ApplicationModule;
import com.redditapp.dagger.modules.BasicAuthNetworkModule;
import com.redditapp.dagger.modules.MainNetworkModule;
import com.redditapp.dagger.modules.OauthNetworkModule;
import com.squareup.moshi.Moshi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        MainNetworkModule.class,
        BasicAuthNetworkModule.class,
        OauthNetworkModule.class
})
public interface ApplicationComponent {
    // Places to inject
    void inject(RedditApplication app);

    // Exported for child-components.
    Application application();
    Context context();
    Moshi moshi();
    @Named(BasicAuthNetworkModule.BASIC_AUTH_HTTP_CLIENT) Retrofit basicAuthRetrofit();
    @Named(OauthNetworkModule.OAUTH_HTTP_CLIENT) Retrofit oauthRetrofit();
}