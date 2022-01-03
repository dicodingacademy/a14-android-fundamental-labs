package com.dicoding.newsapp.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dicoding.newsapp.data.NewsRepository;
import com.dicoding.newsapp.di.Injection;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final NewsRepository newsRepository;

    private ViewModelFactory(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public static ViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(Injection.provideRepository(context));
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewsViewModel.class)) {
            return (T) new NewsViewModel(newsRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
