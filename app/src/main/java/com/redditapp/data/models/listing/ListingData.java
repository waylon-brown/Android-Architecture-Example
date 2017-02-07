package com.redditapp.data.models.listing;

import com.squareup.moshi.Json;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class ListingData implements RealmModel {

	private String modhash;
	@Json(name = "children") private RealmList<Post> posts;
	private String after;
	private String before;

	public ListingData() { }

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

	public void setModhash(String modhash) {
		this.modhash = modhash;
	}

	public RealmList<Post> getPosts() {
		return posts;
	}

	public void setPosts(RealmList<Post> posts) {
		this.posts = posts;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ListingData that = (ListingData) o;

		if (modhash != null ? !modhash.equals(that.modhash) : that.modhash != null) return false;
		if (posts != null ? !posts.equals(that.posts) : that.posts != null) return false;
		if (after != null ? !after.equals(that.after) : that.after != null) return false;
		return before != null ? before.equals(that.before) : that.before == null;

	}

	@Override
	public int hashCode() {
		int result = modhash != null ? modhash.hashCode() : 0;
		result = 31 * result + (posts != null ? posts.hashCode() : 0);
		result = 31 * result + (after != null ? after.hashCode() : 0);
		result = 31 * result + (before != null ? before.hashCode() : 0);
		return result;
	}
}
