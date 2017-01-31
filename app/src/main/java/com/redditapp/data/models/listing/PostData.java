package com.redditapp.data.models.listing;

import com.squareup.moshi.Json;

import java.util.List;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.RealmClass;

@RealmClass
public class PostData implements RealmModel {

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

	public PostData() {}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public String getSubbreddit() {
		return subbreddit;
	}

	public void setSubbreddit(String subbreddit) {
		this.subbreddit = subbreddit;
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

	public List<Object> getUserReports() {
		return userReports;
	}

	public void setUserReports(List<Object> userReports) {
		this.userReports = userReports;
	}

	public Object getSecureMedia() {
		return secureMedia;
	}

	public void setSecureMedia(Object secureMedia) {
		this.secureMedia = secureMedia;
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

	public Object getSecureMediaEmbed() {
		return secureMediaEmbed;
	}

	public void setSecureMediaEmbed(Object secureMediaEmbed) {
		this.secureMediaEmbed = secureMediaEmbed;
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

	public Object getMedia() {
		return media;
	}

	public void setMedia(Object media) {
		this.media = media;
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

	public Object getPreview() {
		return preview;
	}

	public void setPreview(Object preview) {
		this.preview = preview;
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

	public double getEdited() {
		return edited;
	}

	public void setEdited(double edited) {
		this.edited = edited;
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

	public List<Object> getModReports() {
		return modReports;
	}

	public void setModReports(List<Object> modReports) {
		this.modReports = modReports;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public Object getMediaEmbed() {
		return mediaEmbed;
	}

	public void setMediaEmbed(Object mediaEmbed) {
		this.mediaEmbed = mediaEmbed;
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

	/**
	 * Used for DiffUtil in {@link com.redditapp.ui.ListingRecyclerViewAdapter}
	 */
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
		if (Double.compare(postData.edited, edited) != 0) return false;
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
		if (bannedBy != null ? !bannedBy.equals(postData.bannedBy) : postData.bannedBy != null)
			return false;
		if (domain != null ? !domain.equals(postData.domain) : postData.domain != null)
			return false;
		if (subbreddit != null ? !subbreddit.equals(postData.subbreddit) : postData.subbreddit != null)
			return false;
		if (selfTextHtml != null ? !selfTextHtml.equals(postData.selfTextHtml) : postData.selfTextHtml != null)
			return false;
		if (selfText != null ? !selfText.equals(postData.selfText) : postData.selfText != null)
			return false;
		if (likes != null ? !likes.equals(postData.likes) : postData.likes != null) return false;
		if (suggestedSort != null ? !suggestedSort.equals(postData.suggestedSort) : postData.suggestedSort != null)
			return false;
		if (userReports != null ? !userReports.equals(postData.userReports) : postData.userReports != null)
			return false;
		if (secureMedia != null ? !secureMedia.equals(postData.secureMedia) : postData.secureMedia != null)
			return false;
		if (id != null ? !id.equals(postData.id) : postData.id != null) return false;
		if (secureMediaEmbed != null ? !secureMediaEmbed.equals(postData.secureMediaEmbed) : postData.secureMediaEmbed != null)
			return false;
		if (reportReasons != null ? !reportReasons.equals(postData.reportReasons) : postData.reportReasons != null)
			return false;
		if (author != null ? !author.equals(postData.author) : postData.author != null)
			return false;
		if (media != null ? !media.equals(postData.media) : postData.media != null) return false;
		if (name != null ? !name.equals(postData.name) : postData.name != null) return false;
		if (approvedBy != null ? !approvedBy.equals(postData.approvedBy) : postData.approvedBy != null)
			return false;
		if (removalReason != null ? !removalReason.equals(postData.removalReason) : postData.removalReason != null)
			return false;
		if (preview != null ? !preview.equals(postData.preview) : postData.preview != null)
			return false;
		if (thumbnail != null ? !thumbnail.equals(postData.thumbnail) : postData.thumbnail != null)
			return false;
		if (subredditID != null ? !subredditID.equals(postData.subredditID) : postData.subredditID != null)
			return false;
		if (linkFlairCssClass != null ? !linkFlairCssClass.equals(postData.linkFlairCssClass) : postData.linkFlairCssClass != null)
			return false;
		if (authorFlairCssClass != null ? !authorFlairCssClass.equals(postData.authorFlairCssClass) : postData.authorFlairCssClass != null)
			return false;
		if (modReports != null ? !modReports.equals(postData.modReports) : postData.modReports != null)
			return false;
		if (mediaEmbed != null ? !mediaEmbed.equals(postData.mediaEmbed) : postData.mediaEmbed != null)
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
		result = (contestMode ? 1 : 0);
		result = 31 * result + (bannedBy != null ? bannedBy.hashCode() : 0);
		result = 31 * result + (domain != null ? domain.hashCode() : 0);
		result = 31 * result + (subbreddit != null ? subbreddit.hashCode() : 0);
		result = 31 * result + (selfTextHtml != null ? selfTextHtml.hashCode() : 0);
		result = 31 * result + (selfText != null ? selfText.hashCode() : 0);
		result = 31 * result + (likes != null ? likes.hashCode() : 0);
		result = 31 * result + (suggestedSort != null ? suggestedSort.hashCode() : 0);
		result = 31 * result + (userReports != null ? userReports.hashCode() : 0);
		result = 31 * result + (secureMedia != null ? secureMedia.hashCode() : 0);
		result = 31 * result + (saved ? 1 : 0);
		result = 31 * result + (id != null ? id.hashCode() : 0);
		result = 31 * result + gilded;
		result = 31 * result + (secureMediaEmbed != null ? secureMediaEmbed.hashCode() : 0);
		result = 31 * result + (clicked ? 1 : 0);
		result = 31 * result + (reportReasons != null ? reportReasons.hashCode() : 0);
		result = 31 * result + (author != null ? author.hashCode() : 0);
		result = 31 * result + (media != null ? media.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + score;
		result = 31 * result + (approvedBy != null ? approvedBy.hashCode() : 0);
		result = 31 * result + (over18 ? 1 : 0);
		result = 31 * result + (removalReason != null ? removalReason.hashCode() : 0);
		result = 31 * result + (hidden ? 1 : 0);
		result = 31 * result + (preview != null ? preview.hashCode() : 0);
		result = 31 * result + (thumbnail != null ? thumbnail.hashCode() : 0);
		result = 31 * result + (subredditID != null ? subredditID.hashCode() : 0);
		temp = Double.doubleToLongBits(edited);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (linkFlairCssClass != null ? linkFlairCssClass.hashCode() : 0);
		result = 31 * result + (authorFlairCssClass != null ? authorFlairCssClass.hashCode() : 0);
		result = 31 * result + downs;
		result = 31 * result + (modReports != null ? modReports.hashCode() : 0);
		result = 31 * result + (archived ? 1 : 0);
		result = 31 * result + (mediaEmbed != null ? mediaEmbed.hashCode() : 0);
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
