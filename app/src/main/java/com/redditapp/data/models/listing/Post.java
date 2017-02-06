package com.redditapp.data.models.listing;

import com.redditapp.ui.ListingAdapter;

public class Post {

	private final String kind;
	private final PostData data;

	public Post(String kind, PostData data) {
		this.kind = kind;
		this.data = data;
	}

	public String getKind() {
		return kind;
	}

	public PostData getData() {
		return data;
	}

	/**
	 * Used for DiffUtil in {@link ListingAdapter}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Post post = (Post) o;

		return data != null ? data.equals(post.data) : post.data == null;

	}

	@Override
	public int hashCode() {
		return data != null ? data.hashCode() : 0;
	}
}
