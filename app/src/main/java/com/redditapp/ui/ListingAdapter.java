package com.redditapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.redditapp.R;
import com.redditapp.data.models.listing.Listing;
import com.redditapp.data.models.listing.Post;
import com.redditapp.data.models.listing.PostData;
import com.redditapp.databinding.PostImageBinding;
import com.redditapp.databinding.PostTextBinding;
import com.redditapp.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListingAdapter extends RecyclerView.Adapter implements Cloneable {

    private Listing listing;
    private OnPostClickListener clickListener;
    private int cardViewWidth = -1;

    public interface OnPostClickListener {
        void postClicked(PostData postData);
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

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
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
     * Recalculate image post heights in {@link PostImageViewHolder#setImage()}.
     */
    public void invalidateCardHeight() {
        cardViewWidth = -1;
        notifyDataSetChanged();
    }

    public class PostTextViewHolder extends BasePostViewHolder<PostTextBinding> {

        @BindView(R.id.post_description) TextView postDescription;
        @BindView(R.id.post_subreddit) TextView postSubreddit;

        public PostTextViewHolder(PostTextBinding postTextBinding) {
            super(postTextBinding);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindData() {
            dataBinding.setPostData(postData);
            dataBinding.setOnPostClickListener(clickListener);

            postSubreddit.setText(StringUtils.getSubredditSpannableText(itemView.getContext(), postData));
            // TODO: get actual time ago
            postDescription.setText("5 hours ago by u/" + postData.getAuthor() + " to");
        }
    }

    public class PostImageViewHolder extends BasePostViewHolder<PostImageBinding> {

        private final int IMAGE_MAX_DIMENSION_PX = 300;

        @BindView(R.id.post_image) ImageView postImage;
        @BindView(R.id.post_description) TextView postDescription;
        @BindView(R.id.post_subreddit) TextView postSubreddit;
        @BindView(R.id.card_view) CardView cardView;

        public PostImageViewHolder(PostImageBinding postImageBinding) {
            super(postImageBinding);
            ButterKnife.bind(this, itemView);
        }

        protected void bindData() {
            dataBinding.setPostData(postData);
            dataBinding.setOnPostClickListener(clickListener);

            postSubreddit.setText(StringUtils.getSubredditSpannableText(itemView.getContext(), postData));
            // TODO: get actual time ago
            postDescription.setText("5 hours ago by u/" + postData.getAuthor() + " to");

            setImage();
        }

        /**
         * Before loading the image, we need to manually set the heights based off image dimensions from the API,
         * since the lazy loading of images will cause item heights to shift and the Staggered Grid Layout to
         * constantly shuffle posts.
         */
        private void setImage() {
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
                        onCardViewWidthSet();
                    }
                });
            } else {
                onCardViewWidthSet();
            }
        }

        private void onCardViewWidthSet() {
            int imageWidth = postData.getImageWidth();
            int imageHeight = postData.getImageHeight();

            // Set ImageView height
            RelativeLayout.LayoutParams postImageLayoutParams = (RelativeLayout.LayoutParams)postImage.getLayoutParams();
            float imageRatio = (float)imageHeight / (float)imageWidth;
            int imageViewHeight = (int)(cardViewWidth * imageRatio);
            postImageLayoutParams.height = imageViewHeight;
            postImage.setLayoutParams(postImageLayoutParams);

            // Downsize image to save memory
            if (imageHeight > IMAGE_MAX_DIMENSION_PX || imageWidth > IMAGE_MAX_DIMENSION_PX) {
                if (imageHeight > imageWidth) {
                    imageWidth = IMAGE_MAX_DIMENSION_PX * imageWidth / imageHeight;
                    imageHeight = IMAGE_MAX_DIMENSION_PX;
                } else if (imageWidth > imageHeight) {
                    imageHeight = IMAGE_MAX_DIMENSION_PX * imageHeight / imageWidth;
                    imageWidth = IMAGE_MAX_DIMENSION_PX;
                } else {
                    imageWidth = IMAGE_MAX_DIMENSION_PX;
                    imageHeight = IMAGE_MAX_DIMENSION_PX;
                }
            }

            DrawableRequestBuilder glideBuilder = Glide.with(itemView.getContext())
                .load(postData.getImageUrl())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(imageWidth, imageHeight);

            // If gif, use thumbnail from API
            if (postData.getPostType() == PostData.PostType.GIF) {
                DrawableRequestBuilder thumbnailBuilder = Glide.with(itemView.getContext())
                        .load(postData.getThumbnail())
                        .crossFade();
                glideBuilder = glideBuilder.thumbnail(thumbnailBuilder);
            } else {
                // Otherwise if image, use thumbnail as down-scaled image
                glideBuilder = glideBuilder.thumbnail(0.5f);
            }
            glideBuilder.into(postImage);
        }
    }
}
