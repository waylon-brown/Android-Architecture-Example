package com.redditapp.util;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.redditapp.R;
import com.redditapp.data.models.listing.PostData;

public final class StringUtils {

	private StringUtils() {}

	public static Spannable getSubredditSpannableText(Context context, PostData postData) {
		Spannable spannable = new SpannableString("r/" + postData.getSubreddit() + " {gmd-launch} " + postData.getDomain());
		spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
				0, 2 + postData.getSubreddit().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}
}
