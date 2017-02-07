package com.redditapp.data.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Source implements RealmModel {

	private String url;
	private int width;
	private int height;

	public Source() {}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Source source = (Source) o;

		if (width != source.width) return false;
		if (height != source.height) return false;
		return url != null ? url.equals(source.url) : source.url == null;

	}

	@Override
	public int hashCode() {
		int result = url != null ? url.hashCode() : 0;
		result = 31 * result + width;
		result = 31 * result + height;
		return result;
	}
}
