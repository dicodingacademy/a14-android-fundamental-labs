package com.dicoding.restaurantreview;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.restaurantreview.model.CustomerReviewsItem;
import com.dicoding.restaurantreview.model.PostReviewResponse;
import com.dicoding.restaurantreview.model.Restaurant;
import com.dicoding.restaurantreview.model.RestaurantResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Restaurant> _restaurant = new MutableLiveData<>();
    public LiveData<Restaurant> getRestaurant() {
        return _restaurant;
    }

    private final MutableLiveData<List<CustomerReviewsItem>> _listReview = new MutableLiveData<>();
    public LiveData<List<CustomerReviewsItem>> getListReview() {
        return _listReview;
    }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

//    private final MutableLiveData<String> _snackbarText = new MutableLiveData<>();
//    public LiveData<String> snackbarText() {
//        return _snackbarText;
//    }

    private final MutableLiveData<Event<String>> _snackbarText = new MutableLiveData<>();
    public LiveData<Event<String>> snackbarText() {
        return _snackbarText;
    }

    private static final String TAG = "MainViewModel";
    private static final String RESTAURANT_ID = "uewq1zg2zlskfw1e867";

    public MainViewModel() {
        _isLoading.setValue(true);
        Call<RestaurantResponse> client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID);
        client.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(@NotNull Call<RestaurantResponse> call, @NotNull Response<RestaurantResponse> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        _restaurant.setValue(response.body().getRestaurant());
                        _listReview.setValue(response.body().getRestaurant().getCustomerReviews());
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<RestaurantResponse> call, @NotNull Throwable t) {
                _isLoading.setValue(false);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public final void postReview(@NotNull String review) {
        _isLoading.setValue(true);
        Call<PostReviewResponse> client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Dicoding", review);
        client.enqueue(new Callback<PostReviewResponse>() {
            @Override
            public void onResponse(@NotNull Call<PostReviewResponse> call, @NotNull Response<PostReviewResponse> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        _listReview.setValue(response.body().getCustomerReviews());
//                        _snackbarText.setValue(response.body().getMessage());
                        _snackbarText.setValue(new Event<>(response.body().getMessage()));
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PostReviewResponse> call, @NotNull Throwable t) {
                _isLoading.setValue(false);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
