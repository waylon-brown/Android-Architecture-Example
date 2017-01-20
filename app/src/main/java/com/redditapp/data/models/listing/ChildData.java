package com.redditapp.data.models.listing;

import com.squareup.moshi.Json;

import java.util.List;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.RealmClass;

@RealmClass
public class ChildData implements RealmModel {

	@Json(name = "contest_mode")
	public boolean contestMode;
	@Json(name = "banned_by")
	public String bannedBy;
	public String domain;
	public String subbreddit;
	@Json(name = "selftext_html")
	public String selfTextHtml;
	public String selfText;
	public String likes;
	@Json(name = "suggested_sort")
	public String suggestedSort;

	// TODO: resolve list
	@Json(name = "user_reports")
	@Ignore
	public List<Object> userReports;

	// TODO: resolve object
	@Ignore
	@Json(name = "secure_media")
	public Object secureMedia;

	public boolean saved;
	public String id;
	public int gilded;

	// TODO: resolve object
	@Ignore
	@Json(name = "secure_media_embed")
	public Object secureMediaEmbed;

	public boolean clicked;
	@Json(name = "report_reasons")
	public String reportReasons;
	public String author;

	// TODO: resolve object
	@Ignore
	public Object media;

	public String name;
	public int score;
	@Json(name = "approved_by")
	public String approvedBy;
	@Json(name = "over_18")
	public boolean over18;
	@Json(name = "removal_reason")
	public String removalReason;
	public boolean hidden;

	// TODO: resolve object
	@Ignore
	public Object preview;

	public String thumbnail;
	@Json(name = "subreddit_id")
	public String subredditID;

	//TODO: can be either a boolean (false) or double (time)
	@Ignore
	public transient double edited;

	@Json(name = "link_flair_css_class")
	public String linkFlairCssClass;
	@Json(name = "author_flair_css_class")
	public String authorFlairCssClass;
	public int downs;

	// TODO: resolve list
	@Json(name = "mod_reports")
	@Ignore
	public List<Object> modReports;

	public boolean archived;

	// TODO: resolve object
	@Json(name = "media_embed")
	@Ignore
	public Object mediaEmbed;

	@Json(name = "post_hint")
	public String postHint;
	@Json(name = "is_self")
	public boolean isSelf;
	@Json(name = "hide_score")
	public boolean hideScore;
	public boolean spoiler;
	public String permalink;
	public boolean locked;
	public boolean stickied;
	public double created;
	public String url;
	@Json(name = "author_flair_text")
	public String authorFlairText;
	public boolean quarantine;
	public String title;
	@Json(name = "created_utc")
	public double createdUTC;
	@Json(name = "link_flair_text")
	public String linkFlairText;
	public String distinguished;
	@Json(name = "num_comments")
	public int numComments;
	public boolean visited;
	@Json(name = "num_reports")
	public String numReports;
	public int ups;

	public ChildData() {}

	public ChildData(boolean contestMode, String bannedBy, String domain, String subbreddit, String selfTextHtml, String selfText, String likes, String suggestedSort, List<Object> userReports, String secureMedia, boolean saved, String id, int gilded, Object secureMediaEmbed, boolean clicked, String reportReasons, String author, String media, String name, int score, String approvedBy, boolean over18, String removalReason, boolean hidden, Object preview, String thumbnail, String subredditID, double edited, String linkFlairCssClass, String authorFlairCssClass, int downs, List<Object> modReports, boolean archived, Object mediaEmbed, String postHint, boolean isSelf, boolean hideScore, boolean spoiler, String permalink, boolean locked, boolean stickied, double created, String url, String authorFlairText, boolean quarantine, String title, double createdUTC, String linkFlairText, String distinguished, int numComments, boolean visited, String numReports, int ups) {
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
