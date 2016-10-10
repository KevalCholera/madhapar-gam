package com.madhapar.Presenter;

import com.madhapar.Model.EventCalenderModel;
import com.madhapar.Model.EventCalenderModelInt;
import com.madhapar.Model.NetworkModel;
import com.madhapar.Model.NetworkModelInt;
import com.madhapar.Model.NewsFeedModel;
import com.madhapar.Model.NewsFeedModelInt;
import com.madhapar.Model.NewsObject;
import com.madhapar.View.Adapter.NewsLikeCommentUpdateCallback;
import com.madhapar.View.EventListInt;
import com.madhapar.View.HomeViewInt;
import com.madhapar.View.NetworkViewInt;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by smartsense on 06/10/16.
 */

public class RequestPresenter implements RequestPresenterInt, EventCalenderModelInt.EventListCallback, NetworkModelInt.NetworkListResponseCallback, NewsFeedModelInt.NewsListCallback, NewsFeedModelInt.NewsLikeCommentUpdate {
    EventListInt eventListInt;
    NetworkViewInt networkViewInt;
    HomeViewInt homeViewInt;
    NewsLikeCommentUpdateCallback likeUpdateCallback;


    @Override
    public void onSuccessEventList(JSONArray eventArray) {
        eventListInt.onSuccessEventList(eventArray);

    }

    @Override
    public void onFailEventList(String errorMessage) {
        eventListInt.onFailEventList(errorMessage);
    }


    @Override
    public void getEventList(EventListInt callback) {
        this.eventListInt = callback;
        new EventCalenderModel().getEventList(this);
    }


    //------------------Network List----------//
    @Override
    public void getNetworkList(NetworkViewInt networkViewInt) {
        new NetworkModel().getNetworkList(this);
        this.networkViewInt = networkViewInt;

    }


    @Override
    public void onSuccessResponse(JSONArray networkList) {
        networkViewInt.onSuccessNetworkList(networkList);
    }

    @Override
    public void onFailResponse(String message) {
        networkViewInt.onFailNetworkListResponse(message);
    }


    @Override
    public void onFailRequest() {
        networkViewInt.onFailNetworkListRequest();
    }


    //________NewsFeed________//


    @Override
    public void getNewsList(HomeViewInt homeViewInt) {
        this.homeViewInt = homeViewInt;
        NewsFeedModel newsFeedModel = new NewsFeedModel();
        newsFeedModel.getNewsData(this);

    }


    @Override
    public void onSuccessNewsList(List<NewsObject> newsList) {
        homeViewInt.onSuccessNewsList(newsList);
    }

    @Override
    public void onFailRequestNewsList() {
        homeViewInt.onFailRequest();
    }

    @Override
    public void onFailResponseNewsList(String message) {
        homeViewInt.onFailResponse(message);
    }


    //_______________Update Like Comment_________//
    @Override
    public void updateLikeComment(String newsId, String newsStatus, String newsComment, NewsLikeCommentUpdateCallback updateCallback) {
        this.likeUpdateCallback = updateCallback;
        NewsFeedModel newsFeedModel = new NewsFeedModel();
        newsFeedModel.updateNewsLikeComment(newsId, newsStatus, newsComment, this);

    }


    @Override
    public void onSuccessLikeComment(NewsObject newsObject) {
        likeUpdateCallback.successfulUpdateLike(newsObject);
    }

    @Override
    public void onFailResponseNewsLikeComment(String message) {
likeUpdateCallback.failUpdateResponse(message);
    }

    @Override
    public void onFailRequestNewsLikeComment() {
likeUpdateCallback.failUpdateRequest();
    }
}

