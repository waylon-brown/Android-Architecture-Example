package com.redditapp.base.mvp;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends BaseView> {

    private WeakReference<V> view = null;
    protected final CompositeDisposable disposables = new CompositeDisposable();

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
        this.view = null;
        onDestroy();
    }

    protected V getView() {
        return view.get();
    }

    protected void onDestroy() {
        disposables.dispose();
    }
}
