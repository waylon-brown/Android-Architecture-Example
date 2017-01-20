package com.redditapp.base.mvp;

public interface BaseView {
    void showLoading();
    void showContent(String response);
    void showEmpty();
    void showError(Throwable throwable);
}
