package com.redditapp.ui;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.redditapp.R;
import com.redditapp.data.models.listing.Listing;
import com.redditapp.data.models.listing.Oembed;
import com.redditapp.data.models.listing.Post;
import com.redditapp.data.models.listing.PostData;
import com.redditapp.data.models.listing.Source;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListingAdapter extends RecyclerView.Adapter implements Cloneable {

    private Listing listing;
    private OnPostClickListener clickListener;
    // TODO: address this changing on device rotation
    private int cardViewWidth = -1;

    public interface OnPostClickListener {
        void postClicked(Post post);
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
            View imageRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post_image, parent, false);
            return new PostImageViewHolder(imageRowView, viewType, clickListener);
        }
        View textRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post_text, parent, false);
        return new PostTextViewHolder(textRowView, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post post = listing.getData().getPosts().get(position);
        if (holder instanceof PostImageViewHolder) {
            PostImageViewHolder postImageViewHolder = (PostImageViewHolder)holder;
            postImageViewHolder.setPost(post);
        } else {
            PostTextViewHolder postTextViewHolder = (PostTextViewHolder)holder;
            postTextViewHolder.setPost(post);
        }
    }

    @Override
    public int getItemViewType(int position) {
        PostData postData = listing.getData().getPosts().get(position).getData();
        if (postData.getPostType() == PostData.PostType.IMAGE
                || postData.getPostType() == PostData.PostType.GFYCAT
                || postData.getPostType() == PostData.PostType.IMGUR_GIF) {
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

    // TODO: make subclass of PostTextViewHolder?
    public class PostImageViewHolder extends RecyclerView.ViewHolder {

        private final int IMAGE_MAX_DIMENSION_PX = 300;

        private Post post;
        private Context context;
        private int viewType;

        @BindView(R.id.post_image) ImageView postImage;
        @BindView(R.id.post_title) TextView postTitle;
        @BindView(R.id.post_description) TextView postDescription;
        @BindView(R.id.post_subreddit) TextView postSubreddit;
        @BindView(R.id.post_comments) TextView postComments;
        @BindView(R.id.post_points) TextView postPoints;
        @BindView(R.id.card_view) CardView cardView;

        public PostImageViewHolder(View itemView, int viewType, final OnPostClickListener clickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.context = itemView.getContext();
            this.viewType = viewType;

            itemView.setOnClickListener(v -> clickListener.postClicked(post));
        }

        public void setPost(Post post) {
            this.post = post;
            // TODO: data binding
            postTitle.setText(post.getData().getTitle());
            // TODO: get actual time ago
            postDescription.setText("5 hours ago by u/" + post.getData().getAuthor() + " to");
            postSubreddit.setText(getSubredditSpannableText());
            postComments.setText(post.getData().getNumComments() + " comments");
            postPoints.setText(post.getData().getScore() + " pts");

            setImage();
        }

        public Spannable getSubredditSpannableText() {
            Spannable spannable = new SpannableString("r/" + post.getData().getSubreddit() + " {gmd-launch} " + post.getData().getDomain());
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
                    0, 2 + post.getData().getSubreddit().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannable;

        }

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
                        setImageDimensions();
                    }
                });
            } else {
                setImageDimensions();
            }
        }

        /**
         * Before loading the image, we need to manually set the heights based off image dimensions from the API,
         * since the lazy loading of images will cause item heights to shift and the Staggered Grid Layout to
         * constantly shuffle posts.
         */
        private void setImageDimensions() {
            String url = "";
            int imageWidth = 0;
            int imageHeight = 0;
            PostData.PostType postType = post.getData().getPostType();
            if (postType == PostData.PostType.IMAGE) {
                Source source = post.getData().getPreview().getImages().get(0).getSource();
                url = source.getUrl();
                imageWidth = source.getWidth();
                imageHeight = source.getHeight();
            } else if (postType == PostData.PostType.GFYCAT) {
                Oembed oembed = post.getData().getMedia().getOembed();
                url = oembed.getThumbnailUrl();
                imageWidth = oembed.getWidth();
                imageHeight = oembed.getHeight();
            } else if (postType == PostData.PostType.IMGUR_GIF) {
                Source source = post.getData().getPreview().getImages().get(0).getVariants().getGif().getSource();
                url = source.getUrl();
                imageWidth = source.getWidth();
                imageHeight = source.getHeight();
            }

            // Set ImageView height
            RelativeLayout.LayoutParams postImageLayoutParams = (RelativeLayout.LayoutParams)postImage.getLayoutParams();
            float imageRatio = (float)imageHeight / (float)imageWidth;
            int imageViewHeight = (int)(cardViewWidth * imageRatio);
            postImageLayoutParams.height = imageViewHeight;
            postImage.setLayoutParams(postImageLayoutParams);

            // Downsize to save memory
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

            DrawableRequestBuilder glideBuilder = Glide.with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(imageWidth, imageHeight);

            // If gif, use thumbnail from API
            if (postType == PostData.PostType.GFYCAT || postType == PostData.PostType.IMGUR_GIF) {
                String thumbnailUrl = post.getData().getThumbnail(); // Gifs only
                DrawableRequestBuilder thumbnailBuilder = Glide.with(context)
                        .load(thumbnailUrl);
                glideBuilder = glideBuilder.thumbnail(thumbnailBuilder);
            } else {
                // Otherwise if image, use thumbnail as down-scaled image
                glideBuilder = glideBuilder.thumbnail(0.5f);
            }
            glideBuilder.into(postImage);
        }
    }

    public class PostTextViewHolder extends RecyclerView.ViewHolder {

        private Post post;
        @BindView(R.id.post_title) TextView postTitle;
        @BindView(R.id.post_author) TextView postAuthor;

        public PostTextViewHolder(View itemView, final OnPostClickListener clickListener) {
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
