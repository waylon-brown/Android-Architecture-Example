package com.redditapp.util;

/**
 * Created by Waylon on 1/15/2017.
 */

public final class StringUtils {

	private StringUtils() {}

	public static final String getBearerToken(String token) {
		return "bearer " + token;
	}
}
