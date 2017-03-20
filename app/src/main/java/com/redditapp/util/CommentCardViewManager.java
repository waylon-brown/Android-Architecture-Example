package com.redditapp.util;

import com.redditapp.R;
import com.redditapp.data.models.FakeComment;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Each list item has it's own set of CardViews, and based on it's level and where it is in the list
 * it needs to look as if it's part of the overall CardViews.
 */
public class CommentCardViewManager {

    private final Context context;
    private final FakeComment comment;
    private final List<FakeComment> commentList;
    private final int commentIndex;
    private final FrameLayout commentLayout;    // Root of layout
    private final ConstraintLayout commentDataContainer;    // Child of root, container of TextViews

    private CommentCardViewManager(Builder builder) {
        context = builder.context;
        comment = builder.comment;
        commentList = builder.commentList;
        commentIndex = builder.commentIndex;
        commentLayout = builder.commentLayout;
        commentDataContainer = builder.commentDataContainer;
    }

    public void addCardViews() {
        resetViews();

        int marginPx = context.getResources().getDimensionPixelSize(R.dimen.comments_card_stacked_margin);
        List<Pair<Boolean, Boolean>> topBottomMarginRulesList = getTopBottomMarginRulesList();

        // We want to add the number of nested cardviews as the comment level + 1
        CardView parentCard = null;
        for (int i = 0; i <= comment.level; i++) {
            CardView cardView = new CardView(context);
            cardView.setCardElevation(context.getResources().getDimensionPixelSize(R.dimen.comments_card_elevation));
            cardView.setUseCompatPadding(true);

            Pair<Boolean, Boolean> topBottomMarginRules = topBottomMarginRulesList.get(i);
            int topMarginPx = 0;
            int bottomMarginPx = 0;
            // Top not closed, or the comment is at the bottom level (top margin should be reduced)
            if (!topBottomMarginRules.first || comment.level == 0) {
                topMarginPx = -marginPx;
            }
            // Bottom not closed
            if (!topBottomMarginRules.second) {
                bottomMarginPx = -marginPx;
            } else {
                bottomMarginPx = Math.round(marginPx / 2f);
            }

            // Outermost card, added to the actual layout root itself rather than another CardView
            if (parentCard == null) {
                commentLayout.addView(cardView);

                // Set margins, only difference with outermost card is it has a negative left margin 
                // and different LayoutParams.
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)commentLayout.getLayoutParams();
                layoutParams.setMargins(-marginPx, topMarginPx, -marginPx, bottomMarginPx);

            } else {	// An inner card, added as a child to another CardView
                parentCard.addView(cardView);

                // Set margins
                CardView.LayoutParams layoutParams = (CardView.LayoutParams)cardView.getLayoutParams();
                layoutParams.setMargins(marginPx, topMarginPx, -marginPx, bottomMarginPx);

            }
            parentCard = cardView;
        }
        /**
         * Last cardview contains the comment content.
         */
        // Disables the intrinsic padding so that we can set our own
        parentCard.setPreventCornerOverlap(false);
        parentCard.addView(commentDataContainer);
    }

    private void resetViews() {
        // Remove commentDataContainer from its parent, whether that's the commentLayout or a parentCard. 
        // It could be either since layouts are recycled.
        ((ViewGroup)commentDataContainer.getParent()).removeView(commentDataContainer);

        // Clear out all previous cards
        commentLayout.removeAllViews();
    }

    /**
     * Doing iteration over recursion because recursion is a bag of dicks.
     *
     * Rules to setting up the CardViews for a given comment:
     * * There are comment level + 1 nested CardViews.
     * * Innermost card
     *   * Top: Always close.
     *   * Bottom: Close if it doesn't have children, otherwise keep it open.
     * * Other cards
     *   * Top: Always open.
     *   * Bottom: If this comment is the last of the parent's children, or if the parent of this comment
     *             is the last of its parent's children (recursive), close that given card.
     *
     * @return List of pairs of margins, which is ordered from outer card to inner card (same order
     *         as how the cards are created, though we compile the list in reverse order for
     *         logic's sake.
     */
    private List<Pair<Boolean, Boolean>> getTopBottomMarginRulesList() {
        List<Pair<Boolean, Boolean>> rulesList = new ArrayList<>();

        boolean topClosed = false;
        boolean bottomClosed = false;
        int previousCommentIndex = commentIndex;

        for(int i = comment.level; i >= 0; i--) {
            // Inner-most card
            if (i == comment.level) {
                topClosed = true;
                bottomClosed = !commentHasChildren();
            } else {
                topClosed = false;
                // If the inner card of this one had an open bottom, then this one must as well.
                if (!bottomClosed) {
                    bottomClosed = false;
                } else {
                    bottomClosed = isPreviousIndexLastChildOfItsParent(previousCommentIndex);
                }
                previousCommentIndex = getParentIndex(previousCommentIndex);
            }
            Pair<Boolean, Boolean> rule = Pair.create(topClosed, bottomClosed);
            // Adding to list in reverse order, so that it is ordered from outer to inner (order of card creation).
            rulesList.add(0, rule);

        }
        return rulesList;
    }

    // is there another card at this level after it before it goes back to parent level (-1)
    private boolean isPreviousIndexLastChildOfItsParent(int index) {
        int commentLevel = commentList.get(index).level;

        for(int i = index + 1; i < commentList.size(); i++) {
            int currentLevel = commentList.get(i).level;
            // We reached another at the level of its parent or less
            if (currentLevel < commentLevel) {
                return true;
            }
            // Another comment at the same level, it's not the last of its parent's children
            if (currentLevel == commentLevel) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return Given the index of a comment, return the index of its parent.
     */
    private int getParentIndex(int currentIndex) {
        int currentCommentLevel = commentList.get(currentIndex).level;
        for (int i = currentIndex; i >= 0; i--) {
            FakeComment previousComment = commentList.get(i);
            if (previousComment.level == currentCommentLevel - 1) {
                return i;
            }
        }
        return -1;
    }

    private boolean commentHasChildren() {
        // If there's a comment afterwards
        if (commentList.size() - 1 > commentIndex) {
            FakeComment nextComment = commentList.get(commentIndex + 1);
            if (nextComment.level == comment.level + 1) {
                return true;
            }
        }
        return false;
    }

    public static final class Builder {
        private Context context;
        private FakeComment comment;
        private List<FakeComment> commentList;
        private int commentIndex = -1;
        private FrameLayout commentLayout;
        private ConstraintLayout commentDataContainer;

        public Builder() {
        }

        public Builder withContext(Context val) {
            context = val;
            return this;
        }

        public Builder withComment(FakeComment val) {
            comment = val;
            return this;
        }

        public Builder withCommentList(List<FakeComment> val) {
            commentList = val;
            return this;
        }

        public Builder withCommentIndex(int val) {
            commentIndex = val;
            return this;
        }

        public Builder withCommentLayout(FrameLayout val) {
            commentLayout = val;
            return this;
        }

        public Builder withCommentDataContainer(ConstraintLayout val) {
            commentDataContainer = val;
            return this;
        }

        public CommentCardViewManager build() throws IllegalStateException {
            // Using builder pattern so to prevent longer constructor, each parameter
            // is still required.
            if (context == null || comment == null || commentIndex == -1 
                    || commentLayout == null || commentDataContainer == null
                    || commentList == null) {
                throw new IllegalStateException();
            }
            return new CommentCardViewManager(this);
        }
    }
}
