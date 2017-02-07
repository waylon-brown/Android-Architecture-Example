package com.redditapp.data.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Post implements RealmModel {

	private String kind;
	private PostData data;

	public Post() {
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public PostData getData() {
		return data;
	}

	public void setData(PostData data) {
		this.data = data;
	}

	/**
	 * Used for DiffUtil in {@link com.redditapp.ui.ListingRecyclerViewAdapter}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Post post = (Post) o;

		if (kind != null ? !kind.equals(post.kind) : post.kind != null) return false;
		return data != null ? data.equals(post.data) : post.data == null;

	}

	@Override
	public int hashCode() {
		int result = kind != null ? kind.hashCode() : 0;
		result = 31 * result + (data != null ? data.hashCode() : 0);
		return result;
	}

}
