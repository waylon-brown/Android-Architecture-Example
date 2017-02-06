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

	// TODO: make this cleaner
	private static PostData.PostType classifyPost(PostData postData) {
		String postHint = postData.getPostHint();
		if (postHint != null
				&& postHint.equals("rich:video")
				&& postData.getMedia() != null
				&& postData.getMedia().getOembed() != null
				&& postData.getMedia().getOembed().getThumbnailUrl() != null) {
			return PostData.PostType.GFYCAT;
		}

		Preview preview = postData.getPreview();
		if (postHint != null
				&& postHint.equals("link")
				&& preview != null
				&& preview.getImages() != null
				&& preview.getImages().get(0) != null
				&& preview.getImages().get(0).getVariants() != null
				&& preview.getImages().get(0).getVariants().getGif() != null
				&& preview.getImages().get(0).getVariants().getGif().getSource() != null
				&& preview.getImages().get(0).getVariants().getGif().getSource().getUrl() != null) {
			return PostData.PostType.IMGUR_GIF;
		}

		if (preview != null
				&& preview != null
				&& preview.getImages().size() > 0
				&& preview.getImages().get(0).getSource() != null) {
			return PostData.PostType.IMAGE;
		}

		return PostData.PostType.TEXT;
	}
}
