package com.redditapp.ui.screens.home;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.redditapp.R;
import com.redditapp.RedditApplication;
import com.redditapp.base.HasComponent;
import com.redditapp.base.mvp.BasePresenter;
import com.redditapp.base.mvp.BaseView;
import com.redditapp.dagger.DaggerRedditAppComponent;
import com.redditapp.dagger.RedditAppComponent;
import com.redditapp.base.mvp.BaseActivity;
import com.redditapp.dagger.RedditAppModule;
import com.redditapp.databinding.ActivityHomeBinding;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, HasComponent<RedditAppComponent>, BaseView {

    /**
     * Dagger
     */
    @Inject HomePresenter presenter;
    RedditAppComponent component;

    /**
     * Butterknife
     */
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.nav_view) NavigationView navigationView;

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupViews();

        Timber.d("Home activity started");
    }

    @Override
    protected void onDestroy() {
        component = null;
        super.onDestroy();
    }

    private void setupViews() {
        binding.appBarHome.setToolbarTitle(toolbarTitle);

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

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

    @Override
    public RedditAppComponent getComponent() {
        return null;
    }

    /**
     * BaseActivity implementations
     */

    // TODO: replace with a HomeComponent
    @Override
    protected void onCreateComponent(RedditAppComponent redditComponent) {
        component = DaggerRedditAppComponent.builder()
                .redditAppModule(new RedditAppModule(RedditApplication.get(this)))
                .build();
        component.inject(this);
    }

    @Override
    protected BasePresenter<? extends BaseView> presenter() {
        return presenter;
    }

    // TODO: figure out
    @Override
    protected int viewId() {
        return 0;
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.home_activity_title;
    }

    /**
     * BaseView implementations
     */

    @Override
    public void showLoading() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(Throwable throwable) {

    }

    public interface Injector {
        void inject(HomeActivity view);
    }
}
