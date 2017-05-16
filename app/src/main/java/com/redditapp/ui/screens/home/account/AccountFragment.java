package com.redditapp.ui.screens.home.account;

import com.jellyknife.DataBinding;
import com.jellyknife.JellyKnife;
import com.redditapp.R;
import com.redditapp.RedditApplication;
import com.redditapp.dagger.modules.ActivityModule;
import com.redditapp.databinding.AccountFragmentBinding;
import com.redditapp.ui.ListingAdapter;
import com.redditapp.ui.base.BaseFragment;
import com.redditapp.ui.screens.home.DaggerHomeComponent;
import com.redditapp.ui.screens.home.HomeComponent;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AccountFragment extends BaseFragment<HomeComponent> {

    // Views
    @DataBinding public AccountFragmentBinding binding;
    
//    @Inject
//    PostListPresenter presenter;

    private ListingAdapter adapter;
    private boolean screenRotated = false;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        JellyKnife.bind(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponentAndInject();
//        presenter.takeView(this);
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

    @Override
    public void onDestroy() {
//        presenter.dropView(this);
        super.onDestroy();
    }

    protected void setupViews() {
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        adapter = new ListingAdapter(this);
//        recyclerView.addItemDecoration(new StaggeredGridItemDecoration(getContext()));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//        recyclerView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
//            /**
//             * If the screen just rotated, we want to recalculate list item heights and item decorations.
//             *
//             * More on this at {@link ListingAdapter.com.redditapp.ui.ListingAdapter.PostImageViewHolder#setImage}
//             */
//            if (screenRotated) {
//                adapter.invalidateCardHeight();
//                recyclerView.invalidateItemDecorations();
//            }
//            screenRotated = false;
//        });
//        adapter.registerAdapterDataObserver(new ListingAdapterDataObserver(recyclerView));
//
//        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        screenRotated = true;
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void showLoading() {
//        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showError(Throwable throwable) {
//        swipeRefreshLayout.setRefreshing(false);
//
//        if (throwable instanceof UnknownHostException) {
//            Snackbar.make(fab, getString(R.string.error_feed_no_internet), Snackbar.LENGTH_LONG)
//                    .setDuration(Snackbar.LENGTH_LONG)
//                    .show();
//        } else if (throwable instanceof TimeoutException) {
//            Snackbar.make(fab, getString(R.string.error_feed_timeout), Snackbar.LENGTH_LONG)
//                    .setDuration(Snackbar.LENGTH_LONG)
//                    .show();
//        } else {
//            Snackbar.make(fab, getString(R.string.error_feed_generic), Snackbar.LENGTH_LONG)
//                    .setDuration(Snackbar.LENGTH_LONG)
//                    .show();
//        }
    }
}
