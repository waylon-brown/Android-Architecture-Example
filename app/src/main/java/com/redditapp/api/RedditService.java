package com.redditapp.api;

import com.redditapp.models.AccessTokenResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RedditService {

    String GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client";

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("access_token")
    Observable<AccessTokenResponse> getNoUserAccessToken(@Field("grant_type") String grantType, @Field("device_id") String deviceId);

    // use body example
//    Observable<AccessTokenResponse> getNoUserAccessToken(@Body AccessTokenRequest user);
}