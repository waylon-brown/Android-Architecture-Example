package com.redditapp.ui;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.redditapp.R;
import com.redditapp.data.models.listing.Listing;
import com.redditapp.data.models.listing.Oembed;
import com.redditapp.data.models.listing.Post;
import com.redditapp.data.models.listing.PostData;
import com.redditapp.data.models.listing.Preview;
import com.redditapp.data.models.listing.Source;

import android.content.Context;
import android.os.Build;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

public class ListingRecyclerViewAdapter extends RecyclerView.Adapter implements Cloneable {

    private Listing listing;
    private OnPostClickListener clickListener;
    // TODO: address this changing on device rotation
    private int cardViewWidth = -1;

    public interface OnPostClickListener {
        void postClicked(Post post);
    }

    enum ItemType {
        IMAGE,
        GFYCAT,
        IMGUR_GIF,
        TEXT
    }

    public ListingRecyclerViewAdapter(OnPostClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ItemType.IMAGE.ordinal() || viewType == ItemType.GFYCAT.ordinal() || viewType == ItemType.IMGUR_GIF.ordinal()) {
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

    // TODO: make this cleaner
    @Override
    public int getItemViewType(int position) {
        PostData postData = listing.getData().getPosts().get(position).getData();
        Preview preview = postData.getPreview();

        // Post is a gfycat GIF
        if (postData.getPostHint() != null
                && postData.getPostHint().equals("rich:video")
                && postData.getMedia() != null
                && postData.getMedia().getOembed() != null
                && postData.getMedia().getOembed().getThumbnailUrl() != null) {
            return ItemType.GFYCAT.ordinal();
        }

        if (postData.getPostHint() != null
                && postData.getPostHint().equals("link")
                && postData.getPreview() != null
                && postData.getPreview().getImages() != null
                && postData.getPreview().getImages().get(0) != null
                && postData.getPreview().getImages().get(0).getVariants() != null
                && postData.getPreview().getImages().get(0).getVariants().getGif() != null
                && postData.getPreview().getImages().get(0).getVariants().getGif().getSource() != null
                && postData.getPreview().getImages().get(0).getVariants().getGif().getSource().getUrl() != null) {
            return ItemType.IMGUR_GIF.ordinal();
        }

        // Post has a preview image
        if (preview != null
                && preview != null
                && preview.getImages().size() > 0
                && preview.getImages().get(0).getSource() != null) {
            return ItemType.IMAGE.ordinal();
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

    // TODO: make subclass of PostTextViewHolder?
    public class PostImageViewHolder extends RecyclerView.ViewHolder {

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
            postSubreddit.setText("r/" + post.getData().getSubreddit());
            postComments.setText(post.getData().getNumComments() + " comments");
            postPoints.setText(post.getData().getScore() + " pts");

            setImage();
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
            if (viewType == ItemType.IMAGE.ordinal()) {
                Source source = post.getData().getPreview().getImages().get(0).getSource();
                url = source.getUrl();
                imageWidth = source.getWidth();
                imageHeight = source.getHeight();
            } else if (viewType == ItemType.GFYCAT.ordinal()) {
                Oembed oembed = post.getData().getMedia().getOembed();
                url = oembed.getThumbnailUrl();
                imageWidth = oembed.getWidth();
                imageHeight = oembed.getHeight();
            } else if (viewType == ItemType.IMGUR_GIF.ordinal()) {
                Source source = post.getData().getPreview().getImages().get(0).getVariants().getGif().getSource();
                url = source.getUrl();
                imageWidth = source.getWidth();
                imageHeight = source.getHeight();
            }

            // Set image height
            RelativeLayout.LayoutParams postImageLayoutParams = (RelativeLayout.LayoutParams)postImage.getLayoutParams();
            float imageRatio = (float)imageHeight / (float)imageWidth;
            int imageViewHeight = (int)(cardViewWidth * imageRatio);
            postImageLayoutParams.height = imageViewHeight;
            postImage.setLayoutParams(postImageLayoutParams);

            Glide.with(context)
                    .load(url)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(postImage);
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
