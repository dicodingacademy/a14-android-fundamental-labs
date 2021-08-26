package com.dicoding.restaurantreview.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantResponse{

	@SerializedName("restaurant")
	private Restaurant restaurant;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public Restaurant getRestaurant(){
		return restaurant;
	}

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}