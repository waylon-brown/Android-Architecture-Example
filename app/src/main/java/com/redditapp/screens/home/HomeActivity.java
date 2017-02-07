package com.redditapp.screens.home;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.redditapp.R;
import com.redditapp.RedditApplication;
import com.redditapp.mvp.BaseActivity;
import com.redditapp.dagger.components.DaggerHomeComponent;
import com.redditapp.dagger.components.HomeComponent;
import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.data.models.listing.Listing;
import com.redditapp.data.models.listing.PostData;
import com.redditapp.databinding.ActivityHomeBinding;
import com.redditapp.ui.InvalidateItemDecorDataObserver;
import com.redditapp.ui.ListingAdapter;
import com.redditapp.ui.StaggeredGridItemDecoration;

import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomeComponent, HomePresenter>
        implements HomeView, NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, ListingAdapter.OnPostClickListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.listing_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.listing_swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

    private ActivityHomeBinding binding;
    private ListingAdapter adapter;
    private boolean screenRotated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupViews();
        onRefresh();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        screenRotated = true;
        super.onConfigurationChanged(newConfig);
    }

    @OnClick(R.id.fab)
    public void fabClicked(View view) {
        Snackbar.make(fab, "Fab clicked", Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG)
                .show();
    }

    /**
     * BaseActivity implementations
     */

    @Override
    protected void bindUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.appBarHome.setToolbarTitle(toolbarTitle);
    }

    @Override
    protected void setupViews() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new ListingAdapter(this);
        recyclerView.addItemDecoration(new StaggeredGridItemDecoration(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            /**
             * If the screen just rotated, we want to recalculate list item heights and item decorations.
             *
             * More on this at {@link ListingAdapter.com.redditapp.ui.ListingAdapter.PostImageViewHolder#setImage}
              */
			if (screenRotated) {
				adapter.invalidateCardHeight();
                recyclerView.invalidateItemDecorations();
			}
			screenRotated = false;
		});
        adapter.registerAdapterDataObserver(new InvalidateItemDecorDataObserver(recyclerView));

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.home_activity_title;
    }

    /**
     * From {@link android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener}.
     */
    @Override
    public void onRefresh() {
        presenter.loadListing();
    }

    /**
     * From {@link ListingAdapter.OnPostClickListener}.
     */
    @Override
    public void postClicked(PostData postData) {
    }

    /**
     * HomeView and BaseView implementations
     */

    @Override
    public void showContent(Listing listing) {
        adapter.updateList(listing);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showError(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);

        if (throwable instanceof UnknownHostException) {
            Snackbar.make(fab, getString(R.string.error_feed_no_internet), Snackbar.LENGTH_LONG)
                    .setDuration(Snackbar.LENGTH_LONG)
                    .show();
        } else if (throwable instanceof TimeoutException) {
            Snackbar.make(fab, getString(R.string.error_feed_timeout), Snackbar.LENGTH_LONG)
                    .setDuration(Snackbar.LENGTH_LONG)
                    .show();
        } else {
            Snackbar.make(fab, getString(R.string.error_feed_generic), Snackbar.LENGTH_LONG)
                    .setDuration(Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * From {@link com.redditapp.dagger.FieldInjector}.
     */
    @Override
    public HomeComponent buildComponentAndInject() {
        if (component == null) {
            component = DaggerHomeComponent.builder()
                    .applicationComponent(RedditApplication.getComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        component.inject(this);
        return component;
    }

    /**
     * Android framework stuff
     */

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
