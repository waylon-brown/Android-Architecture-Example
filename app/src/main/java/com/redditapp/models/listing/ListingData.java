package com.redditapp.models.listing;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class ListingData implements RealmModel {

	public String modhash;
	public RealmList<Children> children;
	public String after;
	public String before;

	public ListingData() {
	}

	public ListingData(String modhash, RealmList<Children> children, String after, String before) {
		this.modhash = modhash;
		this.children = children;
		this.after = after;
		this.before = before;
	}
}
