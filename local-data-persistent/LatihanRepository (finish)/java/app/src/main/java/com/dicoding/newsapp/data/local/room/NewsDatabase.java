package com.dicoding.newsapp.data.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dicoding.newsapp.data.local.entity.NewsEntity;

@Database(entities = {NewsEntity.class},
        version = 1,
        exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();

    private static volatile NewsDatabase INSTANCE;

    public static NewsDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (NewsDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        NewsDatabase.class, "News.db")
                        .build();
            }
        }
        return INSTANCE;
    }
}