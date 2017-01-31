package com.redditapp.data.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Use https://www.reddit.com/hot.json as an example listing response.
 */
@RealmClass
public class Listing implements RealmModel {

	@PrimaryKey
	public int id;

	public String kind;
	public ListingData data;

	public Listing() {}

	public Listing(String kind, ListingData data) {
		this.kind = kind;
		this.data = data;
	}

	public static Listing copy(Listing other) {
		if (other == null) {
			return null;
		}
		Listing copy = new Listing();
		copy.id = other.getId();
		copy.kind = other.getKind();
		copy.data = ListingData.copy(other.getData());
		return copy;
	}

	public int getId() {
		return id;
	}

	public String getKind() {
		return kind;
	}

	public ListingData getData() {
		return data;
	}
}
