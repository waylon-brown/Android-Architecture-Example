package com.redditapp.ui;

import com.jellyknife.Bind;
import com.jellyknife.JellyKnife;
import com.redditapp.BuildConfig;
import com.redditapp.R;
import com.redditapp.data.models.FakeComment;
import com.redditapp.data.models.listing.PostData;
import com.redditapp.databinding.CommentBinding;
import com.redditapp.databinding.CommentsPostBinding;
import com.redditapp.util.CommentCardViewManager;
import com.redditapp.util.ImageLoader;
import com.redditapp.util.StringUtils;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter {

	private PostData postData;
	private List<FakeComment> commentList;

	enum ItemType {
		HEADER,
		COMMENT
	}

	public CommentsAdapter(PostData postData) {
		this.postData = postData;
		commentList = generateFakeComments();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == ItemType.HEADER.ordinal()) {
			return HeaderViewHolder.create(parent);
		}
		return CommentsViewHolder.create(parent, commentList);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof HeaderViewHolder) {
			HeaderViewHolder headerHolder = (HeaderViewHolder)holder;
			headerHolder.setPost(postData);
		} else {
			CommentsViewHolder commentsHolder = (CommentsViewHolder)holder;
			// We're passing in the comment position relative to the comment list rather than the header + comment list, hence the "-1"
			commentsHolder.setComment(commentList.get(position - 1), position - 1);
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return ItemType.HEADER.ordinal();
		}
		return ItemType.COMMENT.ordinal();
	}

	@Override
	public int getItemCount() {
		// Header plus comments list
		return 1 + commentList.size();
	}
	
	

	public static class HeaderViewHolder extends BasePostViewHolder<CommentsPostBinding> {

        // Can be much higher res than the images in the list
        private final int IMAGE_MAX_DIMENSION_PX = 1200;

        // Views
        @Bind
        public TextView postDescription;
        @Bind public CardView cardView;
        @Bind public ImageView postImage;
        
        private Context context;
		
		static HeaderViewHolder create(ViewGroup parent) {
			CommentsPostBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_comments_post, parent, false);
			return new HeaderViewHolder(binding);
		}

		public HeaderViewHolder(CommentsPostBinding binding) {
			super(binding);
            JellyKnife.bind(this, binding);
			this.context = itemView.getContext();
		}

		@Override
		protected void bindData() {
			binding.setPostData(postData);
            postDescription.setText(StringUtils.getPostDescriptionSpannableText(false, context, postData));

			// Offline mode doesn't have image loading
			if (!BuildConfig.FLAVOR.equals("offline")) {
				getContainerWidthAndSetImage();
			}
		}

        private void getContainerWidthAndSetImage() {
            ViewTreeObserver viewTreeObserver = cardView.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        cardView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    int cardViewWidth = cardView.getMeasuredWidth();
                    new ImageLoader.Builder()
                            .withContext(context)
                            .withImageView(postImage)
                            .withCalculatedImageWidth(cardViewWidth)
                            .withPostData(postData)
                            .withImageMaxDimensionPx(IMAGE_MAX_DIMENSION_PX)
                            .build()
                            .loadImage();
                }
            });
        }

    }

	public static class CommentsViewHolder extends BaseBindingViewHolder<CommentBinding> {

        // Views
        @Bind public TextView commentTitleText;
        @Bind public FrameLayout commentLayout;
        @Bind public ConstraintLayout commentDataContainer;
        
		private Context context;
        private List<FakeComment> commentList;
        private FakeComment comment;
        private int commentIndex;

        static CommentsViewHolder create(ViewGroup parent, List<FakeComment> commentList) {
            CommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_comment, parent, false);
            return new CommentsViewHolder(binding, commentList);
        }

		public CommentsViewHolder(CommentBinding binding, List<FakeComment> commentList) {
			super(binding);
            JellyKnife.bind(this, binding);
			this.context = itemView.getContext();
            this.commentList = commentList;
        }

		/**
		 * @param comment
		 * @param position, relative to the comment list (ignores the header list item)
		 */
		void setComment(FakeComment comment, int position) {
			this.comment = comment;
			this.commentIndex = position;
			
			bindData();
			/**
			 * Needed because RecyclerView hates it if you don't do this, more at
			 * https://realm.io/news/data-binding-android-boyar-mount/
			 */
			binding.executePendingBindings();
		}

		@Override
		protected void bindData() {
			binding.setComment(comment);
			commentTitleText.setText(StringUtils.getCommentTitleSpannableText(context));

			new CommentCardViewManager.Builder()
					.withContext(itemView.getContext())
					.withComment(comment)
					.withCommentList(commentList)
					.withCommentIndex(commentIndex)
					.withCommentLayout(commentLayout)
					.withCommentDataContainer(commentDataContainer)
					.build()
					.addCardViews();
		}
	}

	// TODO: remove
	private List<FakeComment> generateFakeComments() {
		List<FakeComment> fakeComments = new ArrayList<>();
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(3));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(3));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(3));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(3));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(3));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(3));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(3));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(3));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(3));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		fakeComments.add(new FakeComment(3));
		fakeComments.add(new FakeComment(0));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(1));
		fakeComments.add(new FakeComment(2));
		return fakeComments;
	}
}
