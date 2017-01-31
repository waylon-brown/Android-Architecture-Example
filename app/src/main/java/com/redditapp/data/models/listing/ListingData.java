package com.redditapp.data.models.listing;

import com.squareup.moshi.Json;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class ListingData implements RealmModel {

	public String modhash;
	@Json(name = "children")
	public RealmList<Post> posts;
	public String after;
	public String before;

	public ListingData() {
	}

	public static ListingData copy(ListingData other) {
		if (other == null) {
			return null;
		}
		ListingData copy = new ListingData();
		copy.modhash = other.getModhash();
		if (other.getPosts() != null) {
			copy.posts = new RealmList<>();
			copy.posts.addAll(other.getPosts());
		}
		copy.after = other.getAfter();
		copy.before = other.getBefore();
		return copy;
	}

	public String getModhash() {
		return modhash;
	}

	public RealmList<Post> getPosts() {
		return posts;
	}

	public String getAfter() {
		return after;
	}

	public String getBefore() {
		return before;
	}

}
