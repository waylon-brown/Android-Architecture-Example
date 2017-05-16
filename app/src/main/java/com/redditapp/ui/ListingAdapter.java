package com.redditapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.util.Pair;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.jellyknife.Bind;
import com.jellyknife.JellyKnife;
import com.redditapp.R;
import com.redditapp.data.models.listing.Listing;
import com.redditapp.data.models.listing.Post;
import com.redditapp.data.models.listing.PostData;
import com.redditapp.databinding.PostImageBinding;
import com.redditapp.databinding.PostTextBinding;
import com.redditapp.util.ImageLoader;
import com.redditapp.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ListingAdapter extends RecyclerView.Adapter {

    private Listing listing;
    private OnPostClickListener clickListener;
    private int cardViewWidth = -1;

    public interface OnPostClickListener {
        // Shared elements would be much better suited in a HashMap, but ActivityOptionsCompat expects this...
        void postClicked(PostData postData, List<Pair<View, String>> sharedElements);
    }

    enum ItemType {
        TEXT,
        IMAGE,
        VIDEO
    }

    public ListingAdapter(OnPostClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ItemType.IMAGE.ordinal()) {
            PostImageBinding postImageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_post_image, parent, false);
            return new PostImageViewHolder(postImageBinding);
        }
		PostTextBinding postTextBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_post_text, parent, false);
		return new PostTextViewHolder(postTextBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post post = listing.getData().getPosts().get(position);
        ((BasePostViewHolder)holder).setPost(post.getData());
        if (holder instanceof PostImageViewHolder) {
            PostImageViewHolder postImageViewHolder = (PostImageViewHolder)holder;
            postImageViewHolder.setPost(post.getData());
        } else {
            PostTextViewHolder postTextViewHolder = (PostTextViewHolder)holder;
            postTextViewHolder.setPost(post.getData());
        }
    }

    @Override
    public int getItemViewType(int position) {
        PostData postData = listing.getData().getPosts().get(position).getData();
        if (postData.getPostType() == PostData.PostType.IMAGE
                || postData.getPostType() == PostData.PostType.GIF) {
            return ItemType.IMAGE.ordinal();
        } else if (postData.getPostType() == PostData.PostType.VIDEO) {
            return ItemType.VIDEO.ordinal();
        }
        return ItemType.TEXT.ordinal();
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
         * Only update different items in the list.
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

    /**
     * Recalculate image post heights in {@link PostImageViewHolder#getContainerWidthAndSetImage()}.
     */
    public void invalidateCardHeight() {
        cardViewWidth = -1;
        notifyDataSetChanged();
    }

    public class PostTextViewHolder extends BasePostViewHolder<PostTextBinding> {

        @Bind public TextView postDescription;
        
        public PostTextViewHolder(PostTextBinding postTextBinding) {
            super(postTextBinding);
            JellyKnife.bind(this, postTextBinding);
        }

        @Override
        protected void bindData() {
            binding.setPostData(postData);
            itemView.setOnClickListener(v -> clickListener.postClicked(postData, null));

            // TODO: get actual time ago
            postDescription.setText(StringUtils.getPostDescriptionSpannableText(false, itemView.getContext(), postData));
        }
    }

    public class PostImageViewHolder extends BasePostViewHolder<PostImageBinding> {

        private final int IMAGE_MAX_DIMENSION_PX = 300;

        @Bind public TextView postTitle;
        @Bind public TextView postDescription;
        @Bind public CardView cardView;
        @Bind public ImageView postImage;

        public PostImageViewHolder(PostImageBinding postImageBinding) {
            super(postImageBinding);
            JellyKnife.bind(this, postImageBinding);
        }

        protected void bindData() {
            binding.setPostData(postData);
            itemView.setOnClickListener(v -> clickListener.postClicked(postData, getSharedElements()));
            // TODO: get actual time ago
            postDescription.setText(StringUtils.getPostDescriptionSpannableText(false, itemView.getContext(), postData));
            getContainerWidthAndSetImage();
        }

        /**
         * Before loading the image, we need to manually set the heights based off image dimensions from the API,
         * since the lazy loading of images will cause item heights to shift and the Staggered Grid Layout to
         * constantly shuffle posts.
         */
        private void getContainerWidthAndSetImage() {
            if (cardViewWidth < 0) {
                ViewTreeObserver viewTreeObserver = cardView.getViewTreeObserver();
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            cardView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                        cardViewWidth = cardView.getMeasuredWidth();
                        loadImage();
                    }
                });
            } else {
                loadImage();
            }
        }

        private void loadImage() {
            new ImageLoader.Builder()
                    .withContext(itemView.getContext())
                    .withImageView(postImage)
                    .withCalculatedImageWidth(cardViewWidth)
                    .withPostData(postData)
                    .withImageMaxDimensionPx(IMAGE_MAX_DIMENSION_PX)
                    .build()
                    .loadImage();
        }

        private List<Pair<View, String>> getSharedElements() {
            List<Pair<View, String>> pairList = new ArrayList<>();
            pairList.add(Pair.create(postTitle, itemView.getContext().getString(R.string.shared_element_post_title)));
            return pairList;
        }
    }
}
