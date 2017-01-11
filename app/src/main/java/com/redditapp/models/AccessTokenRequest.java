package com.redditapp.models;

import com.squareup.moshi.Json;

//TODO: use as example for HTML body
public class AccessTokenRequest {

    private static final String GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client";

    @Json(name="grant_type") public final String grantType;
    @Json(name="device_id") public final String deviceId;

    private AccessTokenRequest(String grantType, String deviceId) {
        this.grantType = grantType;
        this.deviceId = deviceId;
    }

    // Static factory method to keep class immutable
    public static AccessTokenRequest create(String deviceId) {
        return new AccessTokenRequest(GRANT_TYPE, deviceId);
    }

}
