package com.dicoding.newsapp.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.dicoding.newsapp.BuildConfig;
import com.dicoding.newsapp.data.local.entity.NewsEntity;
import com.dicoding.newsapp.data.local.room.NewsDao;
import com.dicoding.newsapp.data.remote.response.ArticlesItem;
import com.dicoding.newsapp.data.remote.response.NewsResponse;
import com.dicoding.newsapp.data.remote.retrofit.ApiService;
import com.dicoding.newsapp.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private volatile static NewsRepository INSTANCE = null;

    private final ApiService apiService;
    private final NewsDao newsDao;
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Result<List<NewsEntity>>> result = new MediatorLiveData<>();

    private NewsRepository(@NonNull ApiService apiService, @NonNull NewsDao newsDao, AppExecutors appExecutors) {
        this.apiService = apiService;
        this.newsDao = newsDao;
        this.appExecutors = appExecutors;
    }

    public static NewsRepository getInstance(ApiService apiService, NewsDao newsDao, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (NewsRepository.class) {
                INSTANCE = new NewsRepository(apiService, newsDao, appExecutors);
            }
        }
        return INSTANCE;
    }

    public LiveData<Result<List<NewsEntity>>> getHeadlineNews() {
        result.setValue(new Result.Loading<>());

        Call<NewsResponse> client = apiService.getNews(BuildConfig.API_KEY);
        client.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<ArticlesItem> articles = response.body().getArticles();

                        ArrayList<NewsEntity> newsList = new ArrayList<>();
                        appExecutors.diskIO().execute(() -> {
                            for (ArticlesItem article : articles) {
                                Boolean isBookmarked = newsDao.isNewsBookmarked(article.getTitle());
                                NewsEntity news = new NewsEntity(
                                        article.getTitle(),
                                        article.getPublishedAt(),
                                        article.getUrlToImage(),
                                        article.getUrl(),
                                        isBookmarked
                                );
                                newsList.add(news);
                            }
                            newsDao.deleteAll();
                            newsDao.insertNews(newsList);
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                result.setValue(new Result.Error<>(t.getLocalizedMessage()));
            }
        });

        LiveData<List<NewsEntity>> localData = newsDao.getNews();
        if (localData != null) {
            result.addSource(localData, newData -> result.setValue(new Result.Success<>(newData)));
        }

        return result;
    }

    public LiveData<List<NewsEntity>> getBookmarkedNews() {
        return newsDao.getBookmarkedNews();
    }

    public void setNewsBookmark(NewsEntity news, boolean bookmarkState) {
        appExecutors.diskIO().execute(() -> {
            news.setBookmarked(bookmarkState);
            newsDao.updateNews(news);
        });
    }

}

