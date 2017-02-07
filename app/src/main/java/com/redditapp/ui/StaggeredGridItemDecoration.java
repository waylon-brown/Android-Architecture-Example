package com.redditapp.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class StaggeredGridItemDecoration extends RecyclerView.ItemDecoration {

	int marginInPx;

	public StaggeredGridItemDecoration(int marginInPx) {
		this.marginInPx = marginInPx;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		int position = parent.getChildAdapterPosition(view);
		StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams)view.getLayoutParams();
		// Span index of 0 == left column, 1 == right column
		int spanIndex = lp.getSpanIndex();

		outRect.top = marginInPx;
		if (spanIndex == 0) {
			outRect.left = marginInPx;
			outRect.right = Math.round((float)marginInPx / 2);
		} else {
			outRect.left = Math.round((float)marginInPx / 2);
			outRect.right = marginInPx;
		}
	}
}
