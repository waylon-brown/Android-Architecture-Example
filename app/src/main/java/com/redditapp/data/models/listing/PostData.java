package com.redditapp.data.models.listing;

import com.squareup.moshi.Json;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class PostData implements RealmModel {

	// POJOs
	private Media media;
	private Preview preview;

	// Primitives
	@Json(name = "contest_mode")
	private boolean contestMode;
	@Json(name = "banned_by")
	private String bannedBy;
	private String domain;
	private String subreddit;
	@Json(name = "selftext_html")
	private String selfTextHtml;
	private String selfText;
	private String likes;
	@Json(name = "suggested_sort")
	private String suggestedSort;
	private boolean saved;
	private String id;
	private int gilded;
	private boolean clicked;
	@Json(name = "report_reasons")
	private String reportReasons;
	private String author;
	private String name;
	private int score;
	@Json(name = "approved_by")
	private String approvedBy;
	@Json(name = "over_18")
	private boolean over18;
	@Json(name = "removal_reason")
	private String removalReason;
	private boolean hidden;
	private String thumbnail;
	@Json(name = "subreddit_id")
	private String subredditID;
	@Json(name = "link_flair_css_class")
	private String linkFlairCssClass;
	@Json(name = "author_flair_css_class")
	private String authorFlairCssClass;
	private int downs;
	private boolean archived;
	@Json(name = "post_hint")
	private String postHint;
	@Json(name = "is_self")
	private boolean isSelf;
	@Json(name = "hide_score")
	private boolean hideScore;
	private boolean spoiler;
	private String permalink;
	private boolean locked;
	private boolean stickied;
	private double created;
	private String url;
	@Json(name = "author_flair_text")
	private String authorFlairText;
	private boolean quarantine;
	private String title;
	@Json(name = "created_utc")
	private double createdUTC;
	@Json(name = "link_flair_text")
	private String linkFlairText;
	private String distinguished;
	@Json(name = "num_comments")
	private int numComments;
	private boolean visited;
	@Json(name = "num_reports")
	private String numReports;
	private int ups;

	// TODO: resolve list
//	@Json(name = "user_reports")
//	@Ignore
//	private List<Object> userReports;

	// TODO: resolve object
//	@Ignore
//	@Json(name = "secure_media")
//	private Object secureMedia;

	// TODO: resolve object
//	@Ignore
//	@Json(name = "secure_media_embed")
//	private Object secureMediaEmbed;

	//TODO: can be either a boolean (false) or double (time)
//	@Ignore
//	private transient double edited;

	// TODO: resolve list
//	@Json(name = "mod_reports")
//	@Ignore
//	private List<Object> modReports;

	// TODO: resolve object
//	@Json(name = "media_embed")
//	@Ignore
//	private Object mediaEmbed;

	public PostData() {}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public Preview getPreview() {
		return preview;
	}

	public void setPreview(Preview preview) {
		this.preview = preview;
	}

	public boolean isContestMode() {
		return contestMode;
	}

	public void setContestMode(boolean contestMode) {
		this.contestMode = contestMode;
	}

	public String getBannedBy() {
		return bannedBy;
	}

	public void setBannedBy(String bannedBy) {
		this.bannedBy = bannedBy;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}

	public String getSelfTextHtml() {
		return selfTextHtml;
	}

	public void setSelfTextHtml(String selfTextHtml) {
		this.selfTextHtml = selfTextHtml;
	}

	public String getSelfText() {
		return selfText;
	}

	public void setSelfText(String selfText) {
		this.selfText = selfText;
	}

	public String getLikes() {
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}

	public String getSuggestedSort() {
		return suggestedSort;
	}

	public void setSuggestedSort(String suggestedSort) {
		this.suggestedSort = suggestedSort;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getGilded() {
		return gilded;
	}

	public void setGilded(int gilded) {
		this.gilded = gilded;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public String getReportReasons() {
		return reportReasons;
	}

	public void setReportReasons(String reportReasons) {
		this.reportReasons = reportReasons;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public boolean isOver18() {
		return over18;
	}

	public void setOver18(boolean over18) {
		this.over18 = over18;
	}

	public String getRemovalReason() {
		return removalReason;
	}

	public void setRemovalReason(String removalReason) {
		this.removalReason = removalReason;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getSubredditID() {
		return subredditID;
	}

	public void setSubredditID(String subredditID) {
		this.subredditID = subredditID;
	}

	public String getLinkFlairCssClass() {
		return linkFlairCssClass;
	}

	public void setLinkFlairCssClass(String linkFlairCssClass) {
		this.linkFlairCssClass = linkFlairCssClass;
	}

	public String getAuthorFlairCssClass() {
		return authorFlairCssClass;
	}

	public void setAuthorFlairCssClass(String authorFlairCssClass) {
		this.authorFlairCssClass = authorFlairCssClass;
	}

	public int getDowns() {
		return downs;
	}

	public void setDowns(int downs) {
		this.downs = downs;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public String getPostHint() {
		return postHint;
	}

	public void setPostHint(String postHint) {
		this.postHint = postHint;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean self) {
		isSelf = self;
	}

	public boolean isHideScore() {
		return hideScore;
	}

	public void setHideScore(boolean hideScore) {
		this.hideScore = hideScore;
	}

	public boolean isSpoiler() {
		return spoiler;
	}

	public void setSpoiler(boolean spoiler) {
		this.spoiler = spoiler;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isStickied() {
		return stickied;
	}

	public void setStickied(boolean stickied) {
		this.stickied = stickied;
	}

	public double getCreated() {
		return created;
	}

	public void setCreated(double created) {
		this.created = created;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthorFlairText() {
		return authorFlairText;
	}

	public void setAuthorFlairText(String authorFlairText) {
		this.authorFlairText = authorFlairText;
	}

	public boolean isQuarantine() {
		return quarantine;
	}

	public void setQuarantine(boolean quarantine) {
		this.quarantine = quarantine;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getCreatedUTC() {
		return createdUTC;
	}

	public void setCreatedUTC(double createdUTC) {
		this.createdUTC = createdUTC;
	}

	public String getLinkFlairText() {
		return linkFlairText;
	}

	public void setLinkFlairText(String linkFlairText) {
		this.linkFlairText = linkFlairText;
	}

	public String getDistinguished() {
		return distinguished;
	}

	public void setDistinguished(String distinguished) {
		this.distinguished = distinguished;
	}

	public int getNumComments() {
		return numComments;
	}

	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public String getNumReports() {
		return numReports;
	}

	public void setNumReports(String numReports) {
		this.numReports = numReports;
	}

	public int getUps() {
		return ups;
	}

	public void setUps(int ups) {
		this.ups = ups;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PostData postData = (PostData) o;

		if (contestMode != postData.contestMode) return false;
		if (saved != postData.saved) return false;
		if (gilded != postData.gilded) return false;
		if (clicked != postData.clicked) return false;
		if (score != postData.score) return false;
		if (over18 != postData.over18) return false;
		if (hidden != postData.hidden) return false;
		if (downs != postData.downs) return false;
		if (archived != postData.archived) return false;
		if (isSelf != postData.isSelf) return false;
		if (hideScore != postData.hideScore) return false;
		if (spoiler != postData.spoiler) return false;
		if (locked != postData.locked) return false;
		if (stickied != postData.stickied) return false;
		if (Double.compare(postData.created, created) != 0) return false;
		if (quarantine != postData.quarantine) return false;
		if (Double.compare(postData.createdUTC, createdUTC) != 0) return false;
		if (numComments != postData.numComments) return false;
		if (visited != postData.visited) return false;
		if (ups != postData.ups) return false;
		if (media != null ? !media.equals(postData.media) : postData.media != null) return false;
		if (preview != null ? !preview.equals(postData.preview) : postData.preview != null)
			return false;
		if (bannedBy != null ? !bannedBy.equals(postData.bannedBy) : postData.bannedBy != null)
			return false;
		if (domain != null ? !domain.equals(postData.domain) : postData.domain != null)
			return false;
		if (subreddit != null ? !subreddit.equals(postData.subreddit) : postData.subreddit != null)
			return false;
		if (selfTextHtml != null ? !selfTextHtml.equals(postData.selfTextHtml) : postData.selfTextHtml != null)
			return false;
		if (selfText != null ? !selfText.equals(postData.selfText) : postData.selfText != null)
			return false;
		if (likes != null ? !likes.equals(postData.likes) : postData.likes != null) return false;
		if (suggestedSort != null ? !suggestedSort.equals(postData.suggestedSort) : postData.suggestedSort != null)
			return false;
		if (id != null ? !id.equals(postData.id) : postData.id != null) return false;
		if (reportReasons != null ? !reportReasons.equals(postData.reportReasons) : postData.reportReasons != null)
			return false;
		if (author != null ? !author.equals(postData.author) : postData.author != null)
			return false;
		if (name != null ? !name.equals(postData.name) : postData.name != null) return false;
		if (approvedBy != null ? !approvedBy.equals(postData.approvedBy) : postData.approvedBy != null)
			return false;
		if (removalReason != null ? !removalReason.equals(postData.removalReason) : postData.removalReason != null)
			return false;
		if (thumbnail != null ? !thumbnail.equals(postData.thumbnail) : postData.thumbnail != null)
			return false;
		if (subredditID != null ? !subredditID.equals(postData.subredditID) : postData.subredditID != null)
			return false;
		if (linkFlairCssClass != null ? !linkFlairCssClass.equals(postData.linkFlairCssClass) : postData.linkFlairCssClass != null)
			return false;
		if (authorFlairCssClass != null ? !authorFlairCssClass.equals(postData.authorFlairCssClass) : postData.authorFlairCssClass != null)
			return false;
		if (postHint != null ? !postHint.equals(postData.postHint) : postData.postHint != null)
			return false;
		if (permalink != null ? !permalink.equals(postData.permalink) : postData.permalink != null)
			return false;
		if (url != null ? !url.equals(postData.url) : postData.url != null) return false;
		if (authorFlairText != null ? !authorFlairText.equals(postData.authorFlairText) : postData.authorFlairText != null)
			return false;
		if (title != null ? !title.equals(postData.title) : postData.title != null) return false;
		if (linkFlairText != null ? !linkFlairText.equals(postData.linkFlairText) : postData.linkFlairText != null)
			return false;
		if (distinguished != null ? !distinguished.equals(postData.distinguished) : postData.distinguished != null)
			return false;
		return numReports != null ? numReports.equals(postData.numReports) : postData.numReports == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = media != null ? media.hashCode() : 0;
		result = 31 * result + (preview != null ? preview.hashCode() : 0);
		result = 31 * result + (contestMode ? 1 : 0);
		result = 31 * result + (bannedBy != null ? bannedBy.hashCode() : 0);
		result = 31 * result + (domain != null ? domain.hashCode() : 0);
		result = 31 * result + (subreddit != null ? subreddit.hashCode() : 0);
		result = 31 * result + (selfTextHtml != null ? selfTextHtml.hashCode() : 0);
		result = 31 * result + (selfText != null ? selfText.hashCode() : 0);
		result = 31 * result + (likes != null ? likes.hashCode() : 0);
		result = 31 * result + (suggestedSort != null ? suggestedSort.hashCode() : 0);
		result = 31 * result + (saved ? 1 : 0);
		result = 31 * result + (id != null ? id.hashCode() : 0);
		result = 31 * result + gilded;
		result = 31 * result + (clicked ? 1 : 0);
		result = 31 * result + (reportReasons != null ? reportReasons.hashCode() : 0);
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + score;
		result = 31 * result + (approvedBy != null ? approvedBy.hashCode() : 0);
		result = 31 * result + (over18 ? 1 : 0);
		result = 31 * result + (removalReason != null ? removalReason.hashCode() : 0);
		result = 31 * result + (hidden ? 1 : 0);
		result = 31 * result + (thumbnail != null ? thumbnail.hashCode() : 0);
		result = 31 * result + (subredditID != null ? subredditID.hashCode() : 0);
		result = 31 * result + (linkFlairCssClass != null ? linkFlairCssClass.hashCode() : 0);
		result = 31 * result + (authorFlairCssClass != null ? authorFlairCssClass.hashCode() : 0);
		result = 31 * result + downs;
		result = 31 * result + (archived ? 1 : 0);
		result = 31 * result + (postHint != null ? postHint.hashCode() : 0);
		result = 31 * result + (isSelf ? 1 : 0);
		result = 31 * result + (hideScore ? 1 : 0);
		result = 31 * result + (spoiler ? 1 : 0);
		result = 31 * result + (permalink != null ? permalink.hashCode() : 0);
		result = 31 * result + (locked ? 1 : 0);
		result = 31 * result + (stickied ? 1 : 0);
		temp = Double.doubleToLongBits(created);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (url != null ? url.hashCode() : 0);
		result = 31 * result + (authorFlairText != null ? authorFlairText.hashCode() : 0);
		result = 31 * result + (quarantine ? 1 : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		temp = Double.doubleToLongBits(createdUTC);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (linkFlairText != null ? linkFlairText.hashCode() : 0);
		result = 31 * result + (distinguished != null ? distinguished.hashCode() : 0);
		result = 31 * result + numComments;
		result = 31 * result + (visited ? 1 : 0);
		result = 31 * result + (numReports != null ? numReports.hashCode() : 0);
		result = 31 * result + ups;
		return result;
	}
}
