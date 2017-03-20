package com.redditapp.ui.screens.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.redditapp.BuildConfig;
import com.redditapp.R;
import com.redditapp.RedditApplication;
import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.databinding.ActivityHomeBinding;
import com.redditapp.ui.base.BaseActivity;

public class HomeActivity extends BaseActivity<HomeComponent>
        implements HomeView, NavigationView.OnNavigationItemSelectedListener {

    // Views
    private ActivityHomeBinding binding;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    HomePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();

        if (BuildConfig.FLAVOR.equals("offline")) {
            Snackbar.make(binding.getRoot(), R.string.snackbar_offline_mode, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        pagerAdapter = null;
        super.onDestroy();
    }

    /**
     * BaseActivity implementations
     */

    @Override
    protected void bindUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setToolbarTitle(toolbarTitle);
        this.toolbar = binding.toolbar;
        this.drawerLayout = binding.drawerLayout;
        this.navView = binding.navView;
        this.viewPager = binding.viewPager;
        this.fab = binding.fab;
    }

    @Override
    protected void setupViews() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        
        // TODO: get pageradapter from dagger?
        pagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                showFab(position == 0);
            }
            
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        getSupportFragmentManager();
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.home_activity_title);
    }

    /**
     * From {@link HomeView}
     */
    
    @Override
    public void showFab(boolean show) {
        if (show && !fab.isShown()) {
            fab.show();
        } else if (!show && fab.isShown()) {
            fab.hide();
        }
    }

    @Override
    public void showLoading() {
        // TODO
    }

    @Override
    public void showError(Throwable throwable) {
        // TODO
    }

    /**
     * From {@link com.redditapp.dagger.FieldInjector}.
     */
    @Override
    public void buildComponentAndInject() {
        if (component == null) {
            component = DaggerHomeComponent.builder()
                    .applicationComponent(RedditApplication.getComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        component.inject(this);
    }

    /**
     * Android framework stuff
     */

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
