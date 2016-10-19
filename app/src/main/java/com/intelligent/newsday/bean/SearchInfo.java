package com.intelligent.newsday.bean;

/**
 * Created by ljh on 2016/9/21.
 */
public class SearchInfo {
    private String url,title;

    public SearchInfo(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SearchInfo{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
