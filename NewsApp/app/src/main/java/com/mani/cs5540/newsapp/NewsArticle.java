package com.mani.cs5540.newsapp;

import android.graphics.Bitmap;

/**
 * Created by maeswara on 6/17/2017.
 */
public class NewsArticle {
    public static final String AUTHOR_NAME = "author";
    public static final String TITLE_NAME = "title";
    public static final String DESCRIPTION_NAME = "description";
    public static final String URL_NAME = "url";
    public static final String URL_TO_IMAGE_NAME = "urlToImage";
    public static final String PUBLISHED_AT_NAME = "publishedAt";

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private Bitmap imageBitMap;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Bitmap getImageBitMap() {
        return imageBitMap;
    }

    public void setImageBitMap(Bitmap imageBitMap) {
        this.imageBitMap = imageBitMap;
    }
}
