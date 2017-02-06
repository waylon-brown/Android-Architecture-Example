package com.redditapp.data.models.listing;

import com.squareup.moshi.Json;

public class Oembed {

	private int width;
	private int height;
	@Json(name = "thumbnail_url")
	private String thumbnailUrl;

	public Oembed() {}

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

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Oembed oembed = (Oembed) o;

		if (width != oembed.width) return false;
		if (height != oembed.height) return false;
		return thumbnailUrl != null ? thumbnailUrl.equals(oembed.thumbnailUrl) : oembed.thumbnailUrl == null;

	}

	@Override
	public int hashCode() {
		int result = width;
		result = 31 * result + height;
		result = 31 * result + (thumbnailUrl != null ? thumbnailUrl.hashCode() : 0);
		return result;
	}
}
