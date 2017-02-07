package com.redditapp.ui;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.redditapp.data.models.listing.PostData;

public abstract class BasePostViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

	protected T dataBinding;
	protected PostData postData;

	public BasePostViewHolder(T dataBinding) {
		super(dataBinding.getRoot());
		this.dataBinding = dataBinding;
	}

	public void setPost(PostData postData) {
		this.postData = postData;
		bindData();
		/**
		 * Needed because RecyclerView hates it if you don't do this, more at
		 * https://realm.io/news/data-binding-android-boyar-mount/
		 */
		dataBinding.executePendingBindings();
	}

	protected abstract void bindData();
}