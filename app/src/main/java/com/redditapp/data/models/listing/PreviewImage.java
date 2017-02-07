package com.redditapp.data.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class PreviewImage implements RealmModel {

	private Source source;
	private Variants variants;

	public PreviewImage() {}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Variants getVariants() {
		return variants;
	}

	public void setVariants(Variants variants) {
		this.variants = variants;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PreviewImage that = (PreviewImage) o;

		if (source != null ? !source.equals(that.source) : that.source != null) return false;
		return variants != null ? variants.equals(that.variants) : that.variants == null;

	}

	@Override
	public int hashCode() {
		int result = source != null ? source.hashCode() : 0;
		result = 31 * result + (variants != null ? variants.hashCode() : 0);
		return result;
	}
}
