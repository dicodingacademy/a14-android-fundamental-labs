package com.dicoding.restaurantreview.model;

import com.google.gson.annotations.SerializedName;

public class CustomerReviewsItem {

	@SerializedName("date")
	private String date;

	@SerializedName("review")
	private String review;

	@SerializedName("name")
	private String name;

	public String getDate(){
		return date;
	}

	public String getReview(){
		return review;
	}

	public String getName(){
		return name;
	}
}