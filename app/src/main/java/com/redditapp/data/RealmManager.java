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

	// Latest listing in in-memory cache
	private Listing listing;
	private static final int LISTING_KEY = 42;

	@Inject
	public RealmManager(Realm realm) {
		this.realm = realm;
	}

	public void updateListingAsync(Listing newListing) {
		// Only have a single Listing in cache
		newListing.id = LISTING_KEY;
		realm.executeTransactionAsync(realm1 -> {
			listing = realm1.copyToRealmOrUpdate(newListing);
		});
	}

	public void setListingChangeListener(RealmChangeListener<Listing> changeListener) {
		listing = getListing();
		RealmObject.addChangeListener(listing, changeListener);
	}

	public Listing getListing() {
		if (listing == null) {
			listing = realm.where(Listing.class).findFirst();
		}
		// If listing both wasn't in in-memory cache or in the DB, then add a blank one to the DB
		if (listing == null) {
			realm.beginTransaction();
			listing = realm.createObject(Listing.class, LISTING_KEY);
			realm.commitTransaction();
		}
		return listing;
	}
}
