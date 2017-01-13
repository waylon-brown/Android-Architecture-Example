package com.redditapp.base.mvp;

import com.redditapp.models.AccessTokenResponse;

public interface BaseView {
    void showLoading();
    void showContent(String response);
    void showEmpty();
    void showError(Throwable throwable);
}
