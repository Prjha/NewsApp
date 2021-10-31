package com.card.apiapplicaton;

public class Data {

    String title, description, url,publishedAt,sourceOfNews;

    public Data() {
    }

    public Data(String title, String description, String url, String publishedAt, String sourceOfNews) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
        this.sourceOfNews = sourceOfNews;
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

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSourceOfNews() {
        return sourceOfNews;
    }

    public void setSourceOfNews(String sourceOfNews) {
        this.sourceOfNews = sourceOfNews;
    }
}
