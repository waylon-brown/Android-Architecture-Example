package com.redditapp.data.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Media implements RealmModel {

	private String type;
	private Oembed oembed;

	public Media() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Oembed getOembed() {
		return oembed;
	}

	public void setOembed(Oembed oembed) {
		this.oembed = oembed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Media media = (Media) o;

		if (type != null ? !type.equals(media.type) : media.type != null) return false;
		return oembed != null ? oembed.equals(media.oembed) : media.oembed == null;

	}

	@Override
	public int hashCode() {
		int result = type != null ? type.hashCode() : 0;
		result = 31 * result + (oembed != null ? oembed.hashCode() : 0);
		return result;
	}
}
