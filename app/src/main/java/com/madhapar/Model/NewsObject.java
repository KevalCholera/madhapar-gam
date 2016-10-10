package com.madhapar.Model;

import com.madhapar.View.Adapter.NewsListAdapter;

import org.json.JSONArray;

/**
 * Created by smartsense on 08/10/16.
 */

public class NewsObject {
    public String getNewsStatusId() {
        return newsStatusId;
    }

    public void setNewsStatusId(String newsStatusId) {
        this.newsStatusId = newsStatusId;
    }

    public String newsStatusId;

    public boolean isCommented() {
        return isCommented;
    }

    public void setCommented(boolean commented) {
        isCommented = commented;
    }

    public boolean isCommented;

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getNewsCity() {
        return newsCity;
    }

    public void setNewsCity(String newsCity) {
        this.newsCity = newsCity;
    }

    public String getNewsLikeCount() {
        return newsLikeCount;
    }

    public void setNewsLikeCount(String newsLikeCount) {
        this.newsLikeCount = newsLikeCount;
    }

    public String getNewsCommentCount() {
        return newsCommentCount;
    }

    public void setNewsCommentCount(String newsCommentCount) {
        this.newsCommentCount = newsCommentCount;
    }

    public String newsTitle;
    public String newsDescription;
    public String newsCity;
    public String newsLikeCount;
    public String newsCommentCount;


    public String newsDataAndTime;

    public JSONArray getNewsImageArray() {
        return newsImageArray;
    }

    public void setNewsImageArray(JSONArray newsImageArray) {
        this.newsImageArray = newsImageArray;
    }

    public JSONArray newsImageArray;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsDataAndTime() {
        return newsDataAndTime;
    }

    public void setNewsDataAndTime(String newsDataAndTime) {
        this.newsDataAndTime = newsDataAndTime;
    }

    public String newsId;


    public static class ComparatorClass {
        public String newsId;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof NewsObject) {
                NewsObject that = (NewsObject) obj;
                return (that.getNewsId().equals(newsId));
            }
            return super.equals(obj);
        }
    }

}