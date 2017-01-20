package com.redditapp.dagger.components;

import android.app.Application;

import com.redditapp.RedditApplication;
import com.redditapp.dagger.modules.ApplicationModule;
import com.redditapp.dagger.modules.BasicAuthNetworkModule;
import com.redditapp.dagger.modules.MainNetworkModule;
import com.redditapp.dagger.modules.OauthNetworkModule;
import com.redditapp.data.RealmManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;
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

    // TODO: use subcomponenets so child graphs have access to this graph
    // Exported for child-components.
    Application application();
    @Named(BasicAuthNetworkModule.BASIC_AUTH_HTTP_CLIENT) Retrofit basicAuthRetrofit();
    @Named(OauthNetworkModule.OAUTH_HTTP_CLIENT) Retrofit oauthRetrofit();
    Realm realm();
    RealmManager realmManager();
}