package com.redditapp.data;

import com.redditapp.data.models.listing.Listing;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class RealmManager {

	private Realm realm;
	// In-memory cache
	private Listing listing;

	@Inject
	public RealmManager(Realm realm) {
		this.realm = realm;
	}

	public void updateListingAsync(Listing newListing) {
		realm.executeTransactionAsync(realm1 -> listing = realm1.copyToRealm(newListing));
	}

	public void setListingChangeListener(RealmChangeListener changeListener) {

	}

	public Listing getListing() {
		if (listing == null) {
			listing = realm.where(Listing.class).findFirst();
		}
		return listing;
	}
}
