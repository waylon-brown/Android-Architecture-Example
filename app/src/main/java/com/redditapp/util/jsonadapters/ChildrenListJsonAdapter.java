package com.redditapp.util.jsonadapters;

import com.redditapp.models.listing.Children;
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
public class ChildrenListJsonAdapter {

	@ToJson
	public Collection<Children> toJson(RealmList<Children> realmList) {
		return realmList;
	}

	@FromJson
	public RealmList<Children> fromJson(Collection<Children> collection) {
		RealmList<Children> realmList = new RealmList<>();
		realmList.addAll(collection);
		return realmList;
	}
}
