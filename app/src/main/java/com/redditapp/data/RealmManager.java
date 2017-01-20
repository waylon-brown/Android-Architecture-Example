package com.redditapp.data;

import com.redditapp.data.models.listing.Listing;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;

@Singleton
public class RealmManager {

	private Realm realm;
	// In-memory cache
	private Listing listing;

	@Inject
	public RealmManager(Realm realm) {
		this.realm = realm;
	}

	public void updateListingAsync(Listing newListing) {
		newListing.id = 42;
		realm.executeTransactionAsync(realm1 -> {
			listing = realm1.copyToRealmOrUpdate(newListing);
		});
	}

	public void setListingChangeListener(RealmChangeListener<Listing> changeListener) {
		listing = getListing();
		RealmObject.addChangeListener(listing, changeListener);
	}

	private Listing getListing() {
		if (listing == null) {
			listing = realm.where(Listing.class).findFirst();
		}
		//TODO: remove 2 null checks?
		if (listing == null) {
			// TODO: fix primary key
			realm.beginTransaction();
			listing = realm.createObject(Listing.class, 42);
			realm.commitTransaction();
		}
		return listing;
	}
}
