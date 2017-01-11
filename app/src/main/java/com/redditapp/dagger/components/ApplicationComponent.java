package com.redditapp.dagger.components;

import com.redditapp.RedditApplication;
import com.redditapp.dagger.modules.ApplicationModule;
import com.redditapp.dagger.modules.NetworkModule;

import android.app.Application;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = { ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    // Places to inject
    void inject(RedditApplication app);

    // Exported for child-components.
    Application application();
    @Named(NetworkModule.NO_AUTH_HTTP_CLIENT) Retrofit nonAuthRetrofit();
    @Named(NetworkModule.AUTHENTICATED_HTTP_CLIENT) Retrofit authenticatedRetrofit();
}