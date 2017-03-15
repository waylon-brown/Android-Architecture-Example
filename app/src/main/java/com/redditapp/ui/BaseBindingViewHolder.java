package com.redditapp.ui;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public abstract class BaseBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

	T binding;

	public BaseBindingViewHolder(T binding) {
		super(binding.getRoot());
		this.binding = binding;
	}
	
	protected abstract void bindData();
}