package com.redditapp.ui.base;

import android.arch.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

// TODO: remove
public class BaseViewModel<V extends BaseView> extends ViewModel {

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
