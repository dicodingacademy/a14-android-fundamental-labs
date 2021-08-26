package com.dicoding.restaurantreview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostReviewResponse {
    @SerializedName("customerReviews")
    private List<CustomerReviewsItem> customerReviews;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public List<CustomerReviewsItem> getCustomerReviews(){
        return customerReviews;
    }

    public boolean isError(){
        return error;
    }

    public String getMessage(){
        return message;
    }
}
