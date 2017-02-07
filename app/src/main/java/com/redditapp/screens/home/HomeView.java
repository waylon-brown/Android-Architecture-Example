package com.redditapp.screens.home;

import com.redditapp.mvp.BaseView;
import com.redditapp.data.models.listing.Listing;

public interface HomeView extends BaseView {
    void showContent(Listing listing);
}
