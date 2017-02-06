package com.redditapp.data.models.listing;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;


public class ListingData {

	private final String modhash;
	@Json(name = "children") private final List<Post> posts;
	private final String after;
	private final String before;

	public ListingData(String modhash, List<Post> posts, String after, String before) {
		this.modhash = modhash;
		this.posts = posts;
		this.after = after;
		this.before = before;
	}

	public static ListingData copy(ListingData other) {
		if (other == null) {
			return null;
		}
		List<Post> postsCopy = null;
		if (other.getPosts() != null) {
			postsCopy = new ArrayList<>();
			postsCopy.addAll(other.getPosts());
		}
		return new ListingData(other.getModhash(), postsCopy, other.getAfter(), other.getBefore());
	}

	public String getModhash() {
		return modhash;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public String getAfter() {
		return after;
	}

	public String getBefore() {
		return before;
	}

}
