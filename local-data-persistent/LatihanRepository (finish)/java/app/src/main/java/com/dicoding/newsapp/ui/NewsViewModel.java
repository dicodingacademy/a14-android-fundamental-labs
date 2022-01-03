package com.dicoding.newsapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.newsapp.data.NewsRepository;
import com.dicoding.newsapp.data.Result;
import com.dicoding.newsapp.data.local.entity.NewsEntity;

import java.util.List;


public class NewsViewModel extends ViewModel {
    private final NewsRepository newsRepository;

    public NewsViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public LiveData<Result<List<NewsEntity>>> getHeadlineNews() {
        return newsRepository.getHeadlineNews();
    }

    public LiveData<List<NewsEntity>> getBookmarkedNews() {
        return newsRepository.getBookmarkedNews();
    }

    public void saveNews(NewsEntity news) {
        newsRepository.setNewsBookmark(news, true);
    }

    public void deleteNews(NewsEntity news) {
        newsRepository.setNewsBookmark(news, false);
    }

}


