package com.redditapp.ui;

import com.redditapp.R;
import com.redditapp.data.models.listing.Listing;
import com.redditapp.data.models.listing.Post;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListingRecyclerViewAdapter extends RecyclerView.Adapter<ListingRecyclerViewAdapter.ListingViewHolder> implements Cloneable {

    private Listing listing;
    private OnPostClickListener clickListener;

    public interface OnPostClickListener {
        void postClicked(Post post);
    }

    public ListingRecyclerViewAdapter(OnPostClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_post, parent, false);
        return new ListingViewHolder(rowView, clickListener);
    }

    @Override
    public void onBindViewHolder(ListingViewHolder holder, int position) {
        holder.setPost(listing.getData().getPosts().get(position));
    }

    @Override
    public int getItemCount() {
        return listing == null ? 0
                : listing.getData().getPosts().size();
    }

    public void updateList(Listing newListing) {
        Timber.i("Updated listing.");
        Listing oldListing = Listing.copy(this.listing);
        this.listing = newListing;

        /**
         *
         */
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                if (oldListing == null
                        || oldListing.getData() == null
                        || oldListing.getData().getPosts() == null) {
                    return 0;
                }
                return oldListing.getData().getPosts().size();
            }

            @Override
            public int getNewListSize() {
                if (newListing == null
                        || newListing.getData() == null
                        || newListing.getData().getPosts() == null) {
                    return 0;
                }
                return newListing.getData().getPosts().size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldListing.getData().getPosts().get(oldItemPosition).equals(newListing.getData().getPosts().get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldListing.getData().getPosts().get(oldItemPosition).equals(newListing.getData().getPosts().get(newItemPosition));
            }
        }).dispatchUpdatesTo(this);
    }

    public class ListingViewHolder extends RecyclerView.ViewHolder {

        private Post post;
        @BindView(R.id.post_title) TextView postTitle;
        @BindView(R.id.post_author) TextView postAuthor;

        public ListingViewHolder(View itemView, final OnPostClickListener clickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> clickListener.postClicked(post));
        }

        public void setPost(Post post) {
            this.post = post;
            // TODO: data binding
            postTitle.setText(post.getData().getTitle());
            postAuthor.setText(post.getData().getAuthor());
        }
    }
}
