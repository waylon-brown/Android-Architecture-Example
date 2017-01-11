package com.redditapp.api;

import com.redditapp.models.AccessTokenRequest;
import com.redditapp.models.AccessTokenResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RedditService {


    @POST("access_token")
    Observable<AccessTokenResponse> getNoUserAccessToken(@Body AccessTokenRequest user);
}