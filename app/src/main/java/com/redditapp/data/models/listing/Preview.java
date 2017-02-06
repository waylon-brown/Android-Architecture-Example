package com.redditapp.data.models.listing;

import java.util.List;

public class Preview {

	private List<PreviewImage> images;

	public Preview() {}

	public List<PreviewImage> getImages() {
		return images;
	}

	public void setImages(List<PreviewImage> images) {
		this.images = images;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Preview preview = (Preview) o;

		return images != null ? images.equals(preview.images) : preview.images == null;

	}

	@Override
	public int hashCode() {
		return images != null ? images.hashCode() : 0;
	}
}
