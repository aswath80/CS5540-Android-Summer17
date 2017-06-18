package com.mani.cs5540.newsapp;

/**
 * Created by maeswara on 6/17/2017.
 */

public class NewsResponse {
    public static final String STATUS_NAME = "status";
    public static final String SOURCE_NAME = "source";
    public static final String SORT_BY_NAME = "sortBy";
    public static final String ARTICLES_NAME = "articles";

    private String status;
    private String source;
    private String sortBy;
    private NewsArticle[] articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public NewsArticle[] getArticles() {
        return articles;
    }

    public void setArticles(NewsArticle[] articles) {
        this.articles = articles;
    }
}
