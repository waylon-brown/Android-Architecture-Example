package com.redditapp.util;


import com.redditapp.R;
import com.redditapp.data.models.listing.PostData;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public final class StringUtils {

	private StringUtils() {}

	public static Spannable getPostDescriptionSpannableText(boolean oneLine, Context context, PostData postData) {
		String firstPart = "5 hours ago by u/" + postData.getAuthor() + " to ";
		if (oneLine) {
			firstPart += "\n";
		}
		Spannable spannable = new SpannableString(firstPart + "r/" + postData.getSubreddit() + " {gmd-launch} " + postData.getDomain());
		spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
				firstPart.length(), firstPart.length() + 2 + postData.getSubreddit().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}

	public static Spannable getCommentTitleSpannableText(Context context) {
		Spannable spannable = new SpannableString("u/ThisUser 72 days ago | 14000 points");
		spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
				0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}
}
