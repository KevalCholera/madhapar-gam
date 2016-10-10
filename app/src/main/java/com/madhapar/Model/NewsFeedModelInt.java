package com.madhapar.Model;

import com.madhapar.View.Adapter.NewsLikeCommentUpdateCallback;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Ronak on 10/7/2016.
 */
public interface NewsFeedModelInt {
    interface NewsListCallback {
        void onSuccessNewsList(List<NewsObject> newsArray);

        void onFailRequestNewsList();

        void onFailResponseNewsList(String message);
    }

    interface NewsLikeCommentUpdate {
        void onSuccessLikeComment(NewsObject newsObject);

        void onFailResponseNewsLikeComment(String message);

        void onFailRequestNewsLikeComment();
    }


    void getNewsData(NewsListCallback newsListCallback);

    void updateNewsLikeComment(String newsId, String newsStatus, String newsComment, NewsLikeCommentUpdate updateCallback);
}
