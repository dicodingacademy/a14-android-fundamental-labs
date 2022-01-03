package com.dicoding.newsapp.di;

import android.content.Context;

import com.dicoding.newsapp.data.NewsRepository;
import com.dicoding.newsapp.data.local.room.NewsDao;
import com.dicoding.newsapp.data.local.room.NewsDatabase;
import com.dicoding.newsapp.data.remote.retrofit.ApiConfig;
import com.dicoding.newsapp.data.remote.retrofit.ApiService;
import com.dicoding.newsapp.utils.AppExecutors;

public class Injection {
    public static NewsRepository provideRepository(Context context) {
        ApiService apiService = ApiConfig.getApiService();
        NewsDatabase database = NewsDatabase.getInstance(context);
        NewsDao dao = database.newsDao();
        AppExecutors appExecutors = new AppExecutors();
        return NewsRepository.getInstance(apiService, dao, appExecutors);
    }
}
