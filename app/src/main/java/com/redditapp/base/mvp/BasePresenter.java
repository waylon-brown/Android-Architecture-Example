package com.redditapp.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends BaseView> {

    private WeakReference<V> view = null;
    protected final CompositeDisposable disposables = new CompositeDisposable();

    // Load has been called for the current view
    private boolean loaded;

    public final void takeView(V view) {
        if (view == null) {
            throw new NullPointerException("Taken view can't be null.");
        }

        // Drop any previous views
        if (this.view != null) {
            dropView(this.view.get());
        }

        this.view = new WeakReference<V>(view);
    }

    public final void dropView(V view) {
        if (view == null) {
            throw new NullPointerException("Dropped view can't be null.");
        }
        loaded = false;
        this.view = null;
        onDestroy();
    }

    protected V getView() {
        return view.get();
    }

    protected void onLoad() {
    }

    protected void onDestroy() {
        disposables.dispose();
    }

    protected void onRestore(@NonNull Bundle savedInstanceState) {
    }

    protected void onSave(@NonNull Bundle outState) {
    }
}
