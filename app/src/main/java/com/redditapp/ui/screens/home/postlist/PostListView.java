package com.redditapp.ui.screens.home.postlist;

import com.redditapp.ui.base.BaseView;
import com.redditapp.data.models.listing.Listing;

public interface PostListView extends BaseView {
    void showContent(Listing listing);
}
