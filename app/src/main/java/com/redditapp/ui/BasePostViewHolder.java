package com.redditapp.ui;

import android.databinding.ViewDataBinding;

import com.redditapp.data.models.listing.PostData;

public abstract class BasePostViewHolder<T extends ViewDataBinding> extends BaseBindingViewHolder<T> {

	protected PostData postData;

	BasePostViewHolder(T binding) {
		super(binding);
	}

	public void setPost(PostData postData) {
		this.postData = postData;
		bindData();
		/**
		 * Needed because RecyclerView hates it if you don't do this, more at
		 * https://realm.io/news/data-binding-android-boyar-mount/
		 */
		binding.executePendingBindings();
	}
}