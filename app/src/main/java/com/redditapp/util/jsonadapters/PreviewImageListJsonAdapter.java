package com.redditapp.util.jsonadapters;

import com.redditapp.data.models.listing.PreviewImage;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.Collection;

import io.realm.RealmList;

/**
 * Allows {@link com.squareup.moshi.Moshi} to parse RealmLists.
 *
 * Unfortunately {@link com.squareup.moshi.JsonAdapter}s don't work with generics and throw an error
 * if you try so one of these needs to be made for each new {@link RealmList} type.
 */
public class PreviewImageListJsonAdapter {

	@ToJson
	public Collection<PreviewImage> toJson(RealmList<PreviewImage> realmList) {
		return realmList;
	}

	@FromJson
	public RealmList<PreviewImage> fromJson(Collection<PreviewImage> collection) {
		RealmList<PreviewImage> realmList = new RealmList<>();
		realmList.addAll(collection);
		return realmList;
	}
}
