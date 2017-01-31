package com.redditapp.data.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Post implements RealmModel {

	public String kind;
	public PostData data;

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

		if (!kind.equals(post.kind)) return false;
		return data.equals(post.data);

	}

	@Override
	public int hashCode() {
		int result = kind.hashCode();
		result = 31 * result + data.hashCode();
		return result;
	}
}
