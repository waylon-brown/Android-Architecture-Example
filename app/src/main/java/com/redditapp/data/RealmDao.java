package com.redditapp.data;

import android.support.annotation.WorkerThread;

import com.redditapp.data.models.AccessTokenResponse;
import com.redditapp.data.models.listing.Listing;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;

/**
 * We can do DB reads on either the UI thread (only if they're quick queries) or a background thread, but
 * we should always perform writes on a background thread where possible, indicated by {@link WorkerThread}
 * annotated methods.
 *
 * Each method creates its own Realm instance so that we don't worry about passing objects across threads,
 * which Realm hates with a passion.
 */
@Singleton
public class RealmDao {

	/**
	 * Primary key indicating we want to store/retrieve a particular item.
	 */
	enum Keys {
		LISTING(42),
		ACCESS_TOKEN(93);

		private int value;

		Keys(int key) {
			this.value = key;
		}
	}

	private static final int REFRESH_THRESHHOLD_SECONDS = 60;
	// References need to be held for RealmObjects with change listeners
	private Listing listing;

	@Inject
	public RealmDao() { }

	/**
	 * Listing
     */

	@WorkerThread
	public void updateListing(Listing listing) {
		Realm realm = Realm.getDefaultInstance();
		listing.setId(Keys.LISTING.value);
		realm.executeTransaction(realm1 -> RealmDao.this.listing = realm1.copyToRealmOrUpdate(listing));
		realm.close();
	}

	public void setListingChangeListener(RealmChangeListener<Listing> changeListener) {
		Realm realm = Realm.getDefaultInstance();
		listing = getListing();

		// If no listing in DB, create a blank one so that change listener works immediately
		// Writing on UI thread here so that Listing object is accessed on UI thread when setting listener
		if (listing == null) {
			realm.executeTransaction(realm1 -> RealmDao.this.listing = realm1.createObject(Listing.class, Keys.LISTING.value));
		}
		RealmObject.addChangeListener(listing, changeListener);
		realm.close();
	}

	public Listing getListing() {
		Realm realm = Realm.getDefaultInstance();
		return realm.where(Listing.class)
				.equalTo("id", Keys.LISTING.value)
				.findFirst();
	}

	/**
	 * Access token
	 */

	@WorkerThread
	public void updateAccessToken(AccessTokenResponse accessTokenResponse) {
		Realm realm = Realm.getDefaultInstance();
		// Only have a single Access Token in cache
		accessTokenResponse.id = Keys.ACCESS_TOKEN.value;

		// Calculate time of when the access token will need to be refreshed
		Calendar calendar = Calendar.getInstance();
		long now = calendar.getTimeInMillis();
		/**
		 * We make the "expires in" time to be {@link #REFRESH_THRESHHOLD_SECONDS} less than what it actually is
		 * to defensively make up for any lost time between the server registering this access token and
		 * this call being made.
 		 */
		long expiresInMillis = (accessTokenResponse.getExpiresIn() - REFRESH_THRESHHOLD_SECONDS) * 1000;
		accessTokenResponse.setExpiresAt(now + expiresInMillis);

		realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(accessTokenResponse));
		realm.close();
	}

	public boolean accessTokenExpired() {
		AccessTokenResponse accessToken = getAccessToken();

		// No access token was stored in Realm
		if (accessToken == null) {
			return true;
		}

		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis() >= accessToken.getExpiresAt();
	}

	public AccessTokenResponse getAccessToken() {
		Realm realm = Realm.getDefaultInstance();
		return realm.where(AccessTokenResponse.class)
					.equalTo("id", Keys.ACCESS_TOKEN.value)
					.findFirst();
	}
}
