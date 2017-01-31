package com.redditapp.data;

import com.redditapp.data.models.AccessTokenResponse;
import com.redditapp.data.models.listing.Listing;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;

/**
 * We want to perform DB reads on the UI thread - indicated with {@link UiThread} annotations.
 *
 * We want to perform DB writes on the {@link io.reactivex.schedulers.Schedulers#computation()}
 * thread - indicated with {@link WorkerThread} annotations so that we don't block the IO thread
 * which does network operations.
 *
 * Each method that isn't on the UI thread gets the realm instance using
 * {@link Realm#getDefaultInstance()} so that the methods don't have to worry about what thread
 * they're called on since Realm objects can't be passed through threads.
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

	private Realm realmUi;
	// References need to be held for RealmObjects with change listeners
	private Listing listing;

	@Inject
	public RealmDao() {
		realmUi = Realm.getDefaultInstance();
	}

	/**
	 * Listing
     */

	@WorkerThread
	public void updateListing(Listing listing) {
		Realm realm = Realm.getDefaultInstance();
		listing.id = Keys.LISTING.value;
		realm.executeTransaction(realm1 -> RealmDao.this.listing = realm1.copyToRealmOrUpdate(listing));
		realm.close();
	}

	@UiThread
	public void setListingChangeListener(RealmChangeListener<Listing> changeListener) {
		listing = getListing();

		// If no listing in DB, create a blank one so that change listener works immediately
		// Writing on UI thread here so that Listing object is accessed on UI thread when setting listener
		if (listing == null) {
			realmUi.executeTransaction(realm -> RealmDao.this.listing = realmUi.createObject(Listing.class, Keys.LISTING.value));
		}
		RealmObject.addChangeListener(listing, changeListener);
	}

	@UiThread
	public Listing getListing() {
		return realmUi.where(Listing.class)
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

	@UiThread
	public boolean accessTokenExpired() {
		AccessTokenResponse accessToken = getAccessToken();

		// No access token was stored in Realm
		if (accessToken == null) {
			return true;
		}

		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis() >= accessToken.getExpiresAt();
	}

	@UiThread
	public AccessTokenResponse getAccessToken() {
		return realmUi.where(AccessTokenResponse.class)
					.equalTo("id", Keys.ACCESS_TOKEN.value)
					.findFirst();
	}
}
