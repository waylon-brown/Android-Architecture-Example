package com.redditapp.models.listing;

import com.squareup.moshi.Json;

import java.util.List;

public class ChildData {

	 @Json(name = "contest_mode")
	 public final boolean contestMode;
	 @Json(name = "banned_by")
	 public final String bannedBy;
	 public final String domain;
	 public final String subbreddit;
	 @Json(name = "selftext_html")
	 public final String selfTextHtml;
	 public final String selfText;
	 public final String likes;
	 @Json(name = "suggested_sort")
	 public final String suggestedSort;

	 // TODO: resolve list
	 @Json(name = "user_reports")
	 public final List<Object> userReports;

	 // TODO: resolve object
	 @Json(name = "secure_media")
	 public final Object secureMedia;

	 public final boolean saved;
	 public final String id;
	 public final int gilded;

	 // TODO: resolve object
	 @Json(name = "secure_media_embed")
	 public final Object secureMediaEmbed;

	 public final boolean clicked;
	 @Json(name = "report_reasons")
	 public final String reportReasons;
	 public final String author;

	 // TODO: resolve object
	 public final Object media;

	 public final String name;
	 public final int score;
	 @Json(name = "approved_by")
	 public final String approvedBy;
	 @Json(name = "over_18")
	 public final boolean over18;
	 @Json(name = "removal_reason")
	 public final String removalReason;
	 public final boolean hidden;

	 // TODO: resolve object
	 public final Object preview;

	 public final String thumbnail;
	 @Json(name = "subreddit_id")
	 public final String subredditID;
	 public final boolean edited;
	 @Json(name = "link_flair_css_class")
	 public final String linkFlairCssClass;
	 @Json(name = "author_flair_css_class")
	 public final String authorFlairCssClass;
	 public final int downs;

	 // TODO: resolve list
	 @Json(name = "mod_reports")
	 public final List<Object> modReports;

	 public final boolean archived;

	 // TODO: resolve object
	 @Json(name = "media_embed")
	 public final Object mediaEmbed;

	 @Json(name = "post_hint")
	 public final String postHint;
	 @Json(name = "is_self")
	 public final boolean isSelf;
	 @Json(name = "hide_score")
	 public final boolean hideScore;
	 public final boolean spoiler;
	 public final String permalink;
	 public final boolean locked;
	 public final boolean stickied;
	 public final double created;
	 public final String url;
	 @Json(name = "author_flair_text")
	 public final String authorFlairText;
	 public final boolean quarantine;
	 public final String title;
	 @Json(name = "created_utc")
	 public final double createdUTC;
	 @Json(name = "link_flair_text")
	 public final String linkFlairText;
	 public final String distinguished;
	 @Json(name = "num_comments")
	 public final int numComments;
	 public final boolean visited;
	 @Json(name = "num_reports")
	 public final String numReports;
	 public final int ups;

	 public ChildData(boolean contestMode, String bannedBy, String domain, String subbreddit, String selfTextHtml, String selfText, String likes, String suggestedSort, List<Object> userReports, String secureMedia, boolean saved, String id, int gilded, Object secureMediaEmbed, boolean clicked, String reportReasons, String author, String media, String name, int score, String approvedBy, boolean over18, String removalReason, boolean hidden, Object preview, String thumbnail, String subredditID, boolean edited, String linkFlairCssClass, String authorFlairCssClass, int downs, List<Object> modReports, boolean archived, Object mediaEmbed, String postHint, boolean isSelf, boolean hideScore, boolean spoiler, String permalink, boolean locked, boolean stickied, double created, String url, String authorFlairText, boolean quarantine, String title, double createdUTC, String linkFlairText, String distinguished, int numComments, boolean visited, String numReports, int ups) {
		  this.contestMode = contestMode;
		  this.bannedBy = bannedBy;
		  this.domain = domain;
		  this.subbreddit = subbreddit;
		  this.selfTextHtml = selfTextHtml;
		  this.selfText = selfText;
		  this.likes = likes;
		  this.suggestedSort = suggestedSort;
		  this.userReports = userReports;
		  this.secureMedia = secureMedia;
		  this.saved = saved;
		  this.id = id;
		  this.gilded = gilded;
		  this.secureMediaEmbed = secureMediaEmbed;
		  this.clicked = clicked;
		  this.reportReasons = reportReasons;
		  this.author = author;
		  this.media = media;
		  this.name = name;
		  this.score = score;
		  this.approvedBy = approvedBy;
		  this.over18 = over18;
		  this.removalReason = removalReason;
		  this.hidden = hidden;
		  this.preview = preview;
		  this.thumbnail = thumbnail;
		  this.subredditID = subredditID;
		  this.edited = edited;
		  this.linkFlairCssClass = linkFlairCssClass;
		  this.authorFlairCssClass = authorFlairCssClass;
		  this.downs = downs;
		  this.modReports = modReports;
		  this.archived = archived;
		  this.mediaEmbed = mediaEmbed;
		  this.postHint = postHint;
		  this.isSelf = isSelf;
		  this.hideScore = hideScore;
		  this.spoiler = spoiler;
		  this.permalink = permalink;
		  this.locked = locked;
		  this.stickied = stickied;
		  this.created = created;
		  this.url = url;
		  this.authorFlairText = authorFlairText;
		  this.quarantine = quarantine;
		  this.title = title;
		  this.createdUTC = createdUTC;
		  this.linkFlairText = linkFlairText;
		  this.distinguished = distinguished;
		  this.numComments = numComments;
		  this.visited = visited;
		  this.numReports = numReports;
		  this.ups = ups;
	 }
}
