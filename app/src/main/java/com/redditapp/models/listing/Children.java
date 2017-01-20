package com.redditapp.models.listing;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class Children implements RealmModel {

	public String kind;
	public ChildData data;

	public Children() {
	}

	public Children(String kind, ChildData data) {
		this.kind = kind;
		this.data = data;
	}
}
