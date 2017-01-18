package com.redditapp.models.listing;

/**
 * Use https://www.reddit.com/hot.json as an example listing response.
 */
public class Listing {

	public final String kind;
	public final ListingData data;

	public Listing(String kind, ListingData data) {
		this.kind = kind;
		this.data = data;
	}
}
