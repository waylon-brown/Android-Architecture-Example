package com.redditapp.data.models;


import com.squareup.moshi.Json;

public class AccessTokenResponse {
    @Json(name="access_token") public final String accessToken;
    @Json(name="token_type") public final String tokenType;
    @Json(name="device_id") public final String deviceId;
    @Json(name="expires_in") public final int expiresIn;
    public final String scope;

    public AccessTokenResponse(String accessToken, String tokenType, String deviceId, int expiresIn, String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.deviceId = deviceId;
        this.expiresIn = expiresIn;
        this.scope = scope;
    }
}