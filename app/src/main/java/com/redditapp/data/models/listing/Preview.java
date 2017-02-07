package com.redditapp.data.models.listing;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Preview implements RealmModel {

	private RealmList<PreviewImage> images;

	public Preview() {}

	public RealmList<PreviewImage> getImages() {
		return images;
	}

	public void setImages(RealmList<PreviewImage> images) {
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
