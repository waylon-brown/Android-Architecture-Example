package com.redditapp.ui.screens.home.postlist;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redditapp.R;
import com.redditapp.RedditApplication;
import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.data.models.listing.Listing;
import com.redditapp.data.models.listing.PostData;
import com.redditapp.databinding.PostListFragmentBinding;
import com.redditapp.ui.ListingAdapter;
import com.redditapp.ui.ListingAdapterDataObserver;
import com.redditapp.ui.StaggeredGridItemDecoration;
import com.redditapp.ui.base.BaseFragment;
import com.redditapp.ui.screens.comments.CommentsActivity;
import com.redditapp.ui.screens.home.DaggerHomeComponent;
import com.redditapp.ui.screens.home.HomeComponent;
import com.redditapp.ui.screens.home.HomeView;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;


public class PostListFragment extends BaseFragment<HomeComponent> 
        implements PostListView, SwipeRefreshLayout.OnRefreshListener, ListingAdapter.OnPostClickListener {

    // Views
    private PostListFragmentBinding binding;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    
    @Inject PostListPresenter presenter;
    @Inject HomeView homeView;

    private ListingAdapter adapter;
    private boolean screenRotated = false;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Don't attach to parent, done for us by the PagerAdapter
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_postlist, container, false);
        this.recyclerView = binding.recyclerView;
        this.swipeRefreshLayout = binding.swipeRefreshLayout;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        onRefresh();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponentAndInject();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        presenter.dropView(this);
        super.onDestroy();
    }

    public void buildComponentAndInject() {
        if (component == null) {
            component = DaggerHomeComponent.builder()
                    .applicationComponent(RedditApplication.getComponent())
                    .activityModule(new ActivityModule(getActivity()))
                    .build();
        }
        component.inject(this);
    }

    protected void setupViews() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new ListingAdapter(this);
        recyclerView.addItemDecoration(new StaggeredGridItemDecoration(getContext()));
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                homeView.showFab(dy < 0);
            }
        });
        adapter.registerAdapterDataObserver(new ListingAdapterDataObserver(recyclerView));

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        screenRotated = true;
        super.onConfigurationChanged(newConfig);
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
    public void postClicked(PostData postData, List<Pair<View, String>> sharedElements) {
        CommentsActivity.startActivity(getActivity(), postData, sharedElements);
    }

    /**
     * PostListView and BaseView implementations
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
            homeView.showFab(false);
            Snackbar.make(getView(), getString(R.string.error_feed_no_internet), Snackbar.LENGTH_LONG)
                    .setDuration(Snackbar.LENGTH_LONG)
                    .show();
        } else if (throwable instanceof TimeoutException) {
            Snackbar.make(getView(), getString(R.string.error_feed_timeout), Snackbar.LENGTH_LONG)
                    .setDuration(Snackbar.LENGTH_LONG)
                    .show();
        } else {
            Snackbar.make(getView(), getString(R.string.error_feed_generic), Snackbar.LENGTH_LONG)
                    .setDuration(Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}
