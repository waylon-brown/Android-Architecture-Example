package com.redditapp.ui;

import android.support.v7.widget.RecyclerView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * When using item decorations with {@link android.support.v7.widget.StaggeredGridLayoutManager}, when the
 * items move the item decorations need to be updated to reflect their new margins.
 */
public class ListingAdapterDataObserver extends RecyclerView.AdapterDataObserver {

    private final RecyclerView recyclerView;

    public ListingAdapterDataObserver(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onChanged() {
        super.onChanged();
        invalidateItemDecorations();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        invalidateItemDecorations();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        super.onItemRangeChanged(positionStart, itemCount, payload);
        invalidateItemDecorations();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        invalidateItemDecorations();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        invalidateItemDecorations();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        invalidateItemDecorations();
    }

    private void invalidateItemDecorations() {
        long moveMillis = recyclerView.getItemAnimator().getMoveDuration();
        Observable.empty()
                .timer(moveMillis, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Long value) {
                        recyclerView.invalidateItemDecorations();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
