package com.redditapp.mvp;

public interface BaseView {
    void showLoading();
    void showError(Throwable throwable);
}
