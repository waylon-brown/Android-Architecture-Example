package com.redditapp.base.mvp;

public interface BaseView {
    void showLoading();
    void showError(Throwable throwable);
}
