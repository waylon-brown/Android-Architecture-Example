package com.redditapp.data.models.listing;

/**
 * Use https://www.reddit.com/hot.json as an example listing response.
 */
public class Listing {

	private final String kind;
	private final ListingData data;

	public Listing(String kind, ListingData listingData) {
		this.kind = kind;
		this.data = listingData;
	}

	public static Listing copy(Listing other) {
		if (other == null) {
			return null;
		}
		return new Listing(other.getKind(), ListingData.copy(other.getData()));
	}


	public String getKind() {
		return kind;
	}

	public ListingData getData() {
		return data;
	}

	/**
	 * Classify post type for {@link com.redditapp.ui.ListingAdapter} based on data from API.
     */
	public static void classifyPosts(Listing listing) {
		if (listing.getData().getPosts() != null) {
			for(Post post : listing.getData().getPosts()) {
				post.getData().setPostType(classifyPost(post.getData()));
			}
		}
	}

	/**
	 * Only using try/catches so that I don't have 30 lines of null checks at each level when accessing the data.
	 */
	private static PostData.PostType classifyPost(PostData postData) {
		String postHint = postData.getPostHint();
		try {
			if (postHint.equals("rich:video")) {
				Oembed oembed = postData.getMedia().getOembed();
				postData.setImageUrl(oembed.getThumbnailUrl());
				postData.setImageWidth(oembed.getWidth());
				postData.setImageHeight(oembed.getHeight());
				return PostData.PostType.GIF;
			}
		} catch (NullPointerException e) {}

		Preview preview = postData.getPreview();
		try {
			if (postHint.equals("link") || postHint.equals("image")) {
				Source source = postData.getPreview().getImages().get(0).getVariants().getGif().getSource();
				postData.setImageUrl(source.getUrl());
				postData.setImageWidth(source.getWidth());
				postData.setImageHeight(source.getHeight());
				return PostData.PostType.GIF;
			}
		} catch (NullPointerException e) {}


		if (preview != null
				&& preview.getImages().size() > 0
				&& preview.getImages().get(0).getSource() != null) {
			Source source = postData.getPreview().getImages().get(0).getSource();
			postData.setImageUrl(source.getUrl());
			postData.setImageWidth(source.getWidth());
			postData.setImageHeight(source.getHeight());
			return PostData.PostType.IMAGE;
		}

		return PostData.PostType.TEXT;
	}
}
