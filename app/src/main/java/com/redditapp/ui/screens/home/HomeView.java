package com.redditapp.ui.screens.home;

import com.redditapp.data.models.listing.Listing;
import com.redditapp.ui.base.BaseView;

public interface HomeView extends BaseView {
    void showContent(Listing listing);
    void showFab(boolean show);
}
