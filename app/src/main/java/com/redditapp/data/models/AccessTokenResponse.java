package com.redditapp.data.models;


import com.squareup.moshi.Json;

public class AccessTokenResponse {

    @Json(name="access_token") private String accessToken;
    @Json(name="token_type") private String tokenType;
    @Json(name="device_id") private String deviceId;
    @Json(name="expires_in") private int expiresIn; // In seconds
    private String scope;

    // Manually calculated from time first retrieved + expiresIn
    private long expiresAt = 0;

    public AccessTokenResponse() {}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }
}