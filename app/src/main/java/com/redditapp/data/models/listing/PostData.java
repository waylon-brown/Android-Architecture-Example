package com.redditapp.data.models.listing;

import android.databinding.BaseObservable;

import com.squareup.moshi.Json;

public class PostData extends BaseObservable {

	public enum PostType {
		TEXT,
		IMAGE,
		GIF,
		VIDEO
	}
	// Mutable
	private PostType postType;
	private String imageUrl;
	private int imageWidth;
	private int imageHeight;

	// POJOs
	private final Media media;
	private final Preview preview;

	// Primitives
	@Json(name = "contest_mode")
	private final boolean contestMode;
	@Json(name = "banned_by")
	private final String bannedBy;
	private final String domain;
	private final String subreddit;
	@Json(name = "selftext_html")
	private final String selfTextHtml;
	private final String selfText;
	private final String likes;
	@Json(name = "suggested_sort")
	private final String suggestedSort;
	private final boolean saved;
	private String id;
	private final int gilded;
	private final boolean clicked;
	@Json(name = "report_reasons")
	private final String reportReasons;
	private final String author;
	private final String name;
	private final int score;
	@Json(name = "approved_by")
	private final String approvedBy;
	@Json(name = "over_18")
	private final boolean over18;
	@Json(name = "removal_reason")
	private final String removalReason;
	private final boolean hidden;
	private final String thumbnail;
	@Json(name = "subreddit_id")
	private final String subredditID;
	@Json(name = "link_flair_css_class")
	private final String linkFlairCssClass;
	@Json(name = "author_flair_css_class")
	private final String authorFlairCssClass;
	private final int downs;
	private final boolean archived;
	@Json(name = "post_hint")
	private final String postHint;
	@Json(name = "is_self")
	private final boolean isSelf;
	@Json(name = "hide_score")
	private final boolean hideScore;
	private final boolean spoiler;
	private final String permalink;
	private final boolean locked;
	private final boolean stickied;
	private final double created;
	private final String url;
	@Json(name = "author_flair_text")
	private final String authorFlairText;
	private final boolean quarantine;
	private final String title;
	@Json(name = "created_utc")
	private final double createdUTC;
	@Json(name = "link_flair_text")
	private final String linkFlairText;
	private final String distinguished;
	@Json(name = "num_comments")
	private final int numComments;
	private final boolean visited;
	@Json(name = "num_reports")
	private final String numReports;
	private final int ups;

	// TODO: resolve these objects from API
//	@Json(name = "user_reports")
//	private List<Object> userReports;
//	@Json(name = "secure_media")
//	private Object secureMedia;
//	@Json(name = "secure_media_embed")
//	private Object secureMediaEmbed;
	// Can be either a boolean (false) or double (time)
//	private transient double edited;
//	@Json(name = "mod_reports")
//	private List<Object> modReports;
//	@Json(name = "media_embed")
//	private Object mediaEmbed;


	public PostData(PostType postType, Media media, Preview preview, boolean contestMode, String bannedBy, String domain, String subreddit, String selfTextHtml, String selfText, String likes, String suggestedSort, boolean saved, String id, int gilded, boolean clicked, String reportReasons, String author, String name, int score, String approvedBy, boolean over18, String removalReason, boolean hidden, String thumbnail, String subredditID, String linkFlairCssClass, String authorFlairCssClass, int downs, boolean archived, String postHint, boolean isSelf, boolean hideScore, boolean spoiler, String permalink, boolean locked, boolean stickied, double created, String url, String authorFlairText, boolean quarantine, String title, double createdUTC, String linkFlairText, String distinguished, int numComments, boolean visited, String numReports, int ups) {
		this.postType = postType;
		this.media = media;
		this.preview = preview;
		this.contestMode = contestMode;
		this.bannedBy = bannedBy;
		this.domain = domain;
		this.subreddit = subreddit;
		this.selfTextHtml = selfTextHtml;
		this.selfText = selfText;
		this.likes = likes;
		this.suggestedSort = suggestedSort;
		this.saved = saved;
		this.id = id;
		this.gilded = gilded;
		this.clicked = clicked;
		this.reportReasons = reportReasons;
		this.author = author;
		this.name = name;
		this.score = score;
		this.approvedBy = approvedBy;
		this.over18 = over18;
		this.removalReason = removalReason;
		this.hidden = hidden;
		this.thumbnail = thumbnail;
		this.subredditID = subredditID;
		this.linkFlairCssClass = linkFlairCssClass;
		this.authorFlairCssClass = authorFlairCssClass;
		this.downs = downs;
		this.archived = archived;
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

	public void setPostType(PostType postType) {
		this.postType = postType;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public PostType getPostType() {
		return postType;
	}

	public Media getMedia() {
		return media;
	}

	public Preview getPreview() {
		return preview;
	}

	public boolean isContestMode() {
		return contestMode;
	}

	public String getBannedBy() {
		return bannedBy;
	}

	public String getDomain() {
		return domain;
	}

	public String getSubreddit() {
		return subreddit;
	}

	public String getSelfTextHtml() {
		return selfTextHtml;
	}

	public String getSelfText() {
		return selfText;
	}

	public String getLikes() {
		return likes;
	}

	public String getSuggestedSort() {
		return suggestedSort;
	}

	public boolean isSaved() {
		return saved;
	}

	public String getId() {
		return id;
	}

	public int getGilded() {
		return gilded;
	}

	public boolean isClicked() {
		return clicked;
	}

	public String getReportReasons() {
		return reportReasons;
	}

	public String getAuthor() {
		return author;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public boolean isOver18() {
		return over18;
	}

	public String getRemovalReason() {
		return removalReason;
	}

	public boolean isHidden() {
		return hidden;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public String getSubredditID() {
		return subredditID;
	}

	public String getLinkFlairCssClass() {
		return linkFlairCssClass;
	}

	public String getAuthorFlairCssClass() {
		return authorFlairCssClass;
	}

	public int getDowns() {
		return downs;
	}

	public boolean isArchived() {
		return archived;
	}

	public String getPostHint() {
		return postHint;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public boolean isHideScore() {
		return hideScore;
	}

	public boolean isSpoiler() {
		return spoiler;
	}

	public String getPermalink() {
		return permalink;
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean isStickied() {
		return stickied;
	}

	public double getCreated() {
		return created;
	}

	public String getUrl() {
		return url;
	}

	public String getAuthorFlairText() {
		return authorFlairText;
	}

	public boolean isQuarantine() {
		return quarantine;
	}

	public String getTitle() {
		return title;
	}

	public double getCreatedUTC() {
		return createdUTC;
	}

	public String getLinkFlairText() {
		return linkFlairText;
	}

	public String getDistinguished() {
		return distinguished;
	}

	public int getNumComments() {
		return numComments;
	}

	public boolean isVisited() {
		return visited;
	}

	public String getNumReports() {
		return numReports;
	}

	public int getUps() {
		return ups;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * Used in {link Post}
	 *
	 * Equals on: title, url, author, subreddit
     */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PostData postData = (PostData) o;

		if (subreddit != null ? !subreddit.equals(postData.subreddit) : postData.subreddit != null)
			return false;
		if (author != null ? !author.equals(postData.author) : postData.author != null)
			return false;
		if (url != null ? !url.equals(postData.url) : postData.url != null) return false;
		return title != null ? title.equals(postData.title) : postData.title == null;

	}

	@Override
	public int hashCode() {
		int result = subreddit != null ? subreddit.hashCode() : 0;
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (url != null ? url.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		return result;
	}
}
