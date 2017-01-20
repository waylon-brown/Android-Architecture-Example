package com.redditapp.data.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Use https://www.reddit.com/hot.json as an example listing response.
 */
@RealmClass
public class Listing implements RealmModel {

	public String kind;
	public ListingData data;

	public Listing() {}

	public Listing(String kind, ListingData data) {
		this.kind = kind;
		this.data = data;
	}
}
