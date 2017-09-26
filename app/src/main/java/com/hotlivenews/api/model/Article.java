package com.hotlivenews.api.model;

public class Article {

    private String author; // The author of the article.
    private String title; // The headline or title of the article.
    private String description; //A description or preface for the article.
    private String url; //The direct URL to the content page of the article.
    private String urlToImage; //The URL to a relevant image for the article.
    private String publishedAt; //The best attempt at finding a date for the article, in UTC (+0).

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
}
