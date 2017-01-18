package com.redditapp.models.listing;

import java.util.List;

public class ListingData {

	public final String modhash;
	public final List<Children> children;
	public final String after;
	public final String before;

	public ListingData(String modhash, List<Children> children, String after, String before) {
		this.modhash = modhash;
		this.children = children;
		this.after = after;
		this.before = before;
	}
}
