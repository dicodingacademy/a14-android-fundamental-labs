package com.dicoding.newsapp.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news")
public class NewsEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "title")
    private final String title;

    @ColumnInfo(name = "publishedAt")
    private final String publishedAt;

    @ColumnInfo(name = "urlToImage")
    private final String urlToImage;

    @ColumnInfo(name = "url")
    private final String url;

    @ColumnInfo(name = "bookmarked")
    private boolean bookmarked;

    public NewsEntity(@NonNull String title, String publishedAt, String urlToImage, String url, Boolean bookmarked) {
        this.title = title;
        this.publishedAt = publishedAt;
        this.urlToImage = urlToImage;
        this.url = url;
        this.bookmarked = bookmarked;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getUrl() {
        return url;
    }


    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }
}
