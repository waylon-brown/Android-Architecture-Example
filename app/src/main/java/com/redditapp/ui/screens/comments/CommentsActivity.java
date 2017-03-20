package com.redditapp.ui.screens.comments;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.redditapp.R;
import com.redditapp.RedditApplication;
import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.data.models.listing.PostData;
import com.redditapp.databinding.CommentsActivityBinding;
import com.redditapp.ui.CommentsAdapter;
import com.redditapp.ui.base.BaseActivity;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class CommentsActivity extends BaseActivity<CommentsComponent>
		implements CommentsView {

	private static final String INTENT_KEY_POSTDATA = "intent_key_postdata";

	@Inject
	Moshi moshi;
	@Inject
	CommentsPresenter presenter;

    // Views
    private CommentsActivityBinding binding;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    
	private CommentsAdapter adapter;
    private PostData postData;

	public static void startActivity(Activity fromActivity, PostData postData, @NonNull List<Pair<View, String>> sharedElements) {
		Moshi tempMoshi = new Moshi.Builder().build();
		JsonAdapter<PostData> postDataJsonAdapter = tempMoshi.adapter(PostData.class);
		String postDataJson = postDataJsonAdapter.toJson(postData);

		Intent intent = new Intent(fromActivity, CommentsActivity.class);
		intent.putExtra(INTENT_KEY_POSTDATA, postDataJson);
		fromActivity.startActivity(intent);
        // TODO: shared element transitions
//		ActivityOptionsCompat options = ActivityOptionsCompat.
//				makeSceneTransitionAnimation(fromActivity, sharedElements.toArray(new Pair[sharedElements.size()]));
//		fromActivity.startActivity(intent, options.toBundle());
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter.takeView(this);

		String postDataJson = getIntent().getStringExtra(INTENT_KEY_POSTDATA);
		try {
			this.postData = moshi.adapter(PostData.class).fromJson(postDataJson);
			toolbarTitle.set("r/" + postData.getSubreddit());
		} catch (IOException e) {
			Timber.e(e);
			toolbarTitle.set(getString(R.string.home_activity_title));
		}

		setupViews();
	}

	@Override
	protected void onDestroy() {
		presenter.dropView(this);
		super.onDestroy();
	}

	@Override
	protected void bindUi() {
		binding = DataBindingUtil.setContentView(this, R.layout.activity_comments);
        binding.setToolbarTitle(toolbarTitle);
		this.toolbar = binding.toolbar;
		this.recyclerView = binding.recyclerView;
	}

	@Override
	protected void setupViews() {
		setSupportActionBar(toolbar);
		// Show back button in Toolbar
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		adapter = new CommentsAdapter(postData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
	}

	/**
	 * Set after data is passed in in {@link #onCreate(Bundle)}.
	 */
	@Override
	protected String getToolbarTitle() {
		return "";
	}

	@Override
	public void showLoading() {

	}

	@Override
	public void showError(Throwable throwable) {

	}

	@Override
	public void buildComponentAndInject() {
		if (component == null) {
			component = DaggerCommentsComponent.builder()
					.applicationComponent(RedditApplication.getComponent())
					.activityModule(new ActivityModule(this))
					.build();
		}
		component.inject(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Back button pressed
		switch(item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
