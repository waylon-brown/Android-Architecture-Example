package com.redditapp.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.redditapp.data.models.listing.PostData;

/**
 * Given a container width, this class is responsible for down-scaling the image
 * to improve performance and load the correct thumbnail.
 */
public class ImageLoader {

	private final Context context;
	private final ImageView imageView;
	private Integer calculatedImageWidth;
	private final int imageMaxDimensionPx;
	private final PostData postData;

	private ImageLoader(Builder builder) {
		context = builder.context;
		imageView = builder.imageView;
		calculatedImageWidth = builder.calculatedImageWidth;
		imageMaxDimensionPx = builder.imageMaxDimensionPx;
		postData = builder.postData;
	}

	public void loadImage() {
		int imageWidth = postData.getImageWidth();
		int imageHeight = postData.getImageHeight();

		// Set ImageView height
		RelativeLayout.LayoutParams postImageLayoutParams = (RelativeLayout.LayoutParams)imageView.getLayoutParams();
		float imageRatio = (float)imageHeight / (float)imageWidth;
		int imageViewHeight = (int)(calculatedImageWidth * imageRatio);
		postImageLayoutParams.height = imageViewHeight;
		imageView.setLayoutParams(postImageLayoutParams);

		// Downsize image to save memory
		if (imageHeight > imageMaxDimensionPx || imageWidth > imageMaxDimensionPx) {
			if (imageHeight > imageWidth) {
				imageWidth = imageMaxDimensionPx * imageWidth / imageHeight;
				imageHeight = imageMaxDimensionPx;
			} else if (imageWidth > imageHeight) {
				imageHeight = imageMaxDimensionPx * imageHeight / imageWidth;
				imageWidth = imageMaxDimensionPx;
			} else {
				imageWidth = imageMaxDimensionPx;
				imageHeight = imageMaxDimensionPx;
			}
		}

		DrawableRequestBuilder glideBuilder = Glide.with(context)
				.load(postData.getImageUrl())
				.crossFade()
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.override(imageWidth, imageHeight);

		// If gif, use thumbnail from API
		if (postData.getPostType() == PostData.PostType.GIF) {
			DrawableRequestBuilder thumbnailBuilder = Glide.with(context)
					.load(postData.getThumbnail());
			glideBuilder = glideBuilder.thumbnail(thumbnailBuilder);
		} else {
			// Otherwise if image, use thumbnail as down-scaled image
			glideBuilder = glideBuilder.thumbnail(0.5f);
		}
		glideBuilder.into(imageView);
	}

	public static final class Builder {
		private Context context;
		private ImageView imageView;
		private Integer calculatedImageWidth;
		private int imageMaxDimensionPx;
		private PostData postData;

		public Builder() {
		}

		@NonNull
		public Builder withContext(@NonNull Context val) {
			context = val;
			return this;
		}

		@NonNull
		public Builder withImageView(@NonNull ImageView val) {
			imageView = val;
			return this;
		}

		@NonNull
		public Builder withCalculatedImageWidth(@NonNull Integer val) {
			calculatedImageWidth = val;
			return this;
		}

		@NonNull
		public Builder withImageMaxDimensionPx(int val) {
			imageMaxDimensionPx = val;
			return this;
		}

		@NonNull
		public Builder withPostData(@NonNull PostData val) {
			postData = val;
			return this;
		}

		@NonNull
		public ImageLoader build() {
			return new ImageLoader(this);
		}
	}
}
