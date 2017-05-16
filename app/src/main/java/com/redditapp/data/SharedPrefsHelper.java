package com.redditapp.data;

import com.redditapp.data.models.AccessTokenResponse;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.Calendar;

import javax.inject.Inject;

import timber.log.Timber;

public class SharedPrefsHelper {

	private final int TOKEN_EXPIRATION_THRESHHOLD_SECONDS = 60;
	private final String SHARED_PREFS_FILE_KEY = "shared_prefs_file_reddit";
	private final String KEY_ACCESS_TOKEN = "access_token";

	private final SharedPreferences sharedPreferences;
	private final Moshi moshi;

	@Inject
	public SharedPrefsHelper(Context context, Moshi moshi) {
		this.moshi = moshi;
		sharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE_KEY, Context.MODE_PRIVATE);
	}

	public void storeAccessToken(AccessTokenResponse accessTokenResponse) {
		// Calculate time of when the access token will need to be refreshed
		Calendar calendar = Calendar.getInstance();
		long now = calendar.getTimeInMillis();
		/**
		 * We make the "expires in" time to be {@link #t TOKEN_EXPIRATION_THRESHHOLD_SECONDS} less than what it actually
		 * is to defensively make up for any lost time between the server registering this access token and
		 * this call being made.
		 */
		long expiresInMillis = (accessTokenResponse.getExpiresIn() - TOKEN_EXPIRATION_THRESHHOLD_SECONDS) * 1000;
		// TODO: add to debug drawer
//		accessTokenResponse.setExpiresAt(now + 10 * 1000);
		accessTokenResponse.setExpiresAt(now + expiresInMillis);

		JsonAdapter<AccessTokenResponse> jsonAdapter = moshi.adapter(AccessTokenResponse.class);
		String json = jsonAdapter.toJson(accessTokenResponse);

		sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, json).apply();
	}

	/**
	 * @return AccessTokenResponse if valid, or null if doesn't exist or expired.
	 */
	public AccessTokenResponse getAccessToken() {
		String json = sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
		if (json != null) {
			JsonAdapter<AccessTokenResponse> jsonAdapter = moshi.adapter(AccessTokenResponse.class);
			try {
				AccessTokenResponse accessTokenResponse = jsonAdapter.fromJson(json);
				if (!isAccessTokenExpired(accessTokenResponse)) {
					return accessTokenResponse;
				}
			} catch (IOException e) {
				// Shouldn't happen if an access code exists
				Timber.wtf(e);
			}
		}
		return null;
	}

	private boolean isAccessTokenExpired(AccessTokenResponse accessTokenResponse) {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis() >= accessTokenResponse.getExpiresAt();
	}
}
