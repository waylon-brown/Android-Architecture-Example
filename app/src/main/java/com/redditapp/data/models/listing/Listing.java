package com.redditapp.data.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Use https://www.reddit.com/hot.json as an example listing response.
 */
@RealmClass
public class Listing implements RealmModel {

	@PrimaryKey private int id;
	private String kind;
	private ListingData data;

	public Listing() {}

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

	public void setId(int id) {
		this.id = id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public ListingData getData() {
		return data;
	}

	public void setData(ListingData data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Listing listing = (Listing) o;

		if (id != listing.id) return false;
		if (kind != null ? !kind.equals(listing.kind) : listing.kind != null) return false;
		return data != null ? data.equals(listing.data) : listing.data == null;

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (kind != null ? kind.hashCode() : 0);
		result = 31 * result + (data != null ? data.hashCode() : 0);
		return result;
	}
}
