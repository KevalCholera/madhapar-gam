package com.madhapar.Presenter;

import android.util.Log;

import com.madhapar.Model.EventPhotosModel;
import com.madhapar.Model.EventPhotosModelInt;
import com.madhapar.Model.NetworkModel;
import com.madhapar.Model.NetworkModelInt;
import com.madhapar.Model.NewsFeedModel;
import com.madhapar.Model.NewsFeedModelInt;
import com.madhapar.Model.NewsObject;
import com.madhapar.Model.ProfileDataModel;
import com.madhapar.Model.ProfileDataModelInt;
import com.madhapar.View.Adapter.NewsLikeCommentUpdateCallback;
import com.madhapar.View.CatagoryCallback;
import com.madhapar.View.CommentListCallback;
import com.madhapar.View.EventPhotosInt;
import com.madhapar.View.HomeViewInt;
import com.madhapar.View.NetworkViewInt;
import com.madhapar.View.NewsDetailViewInt;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by smartsense on 06/10/16.
 */

public class RequestPresenter implements RequestPresenterInt, NetworkModelInt.NetworkListResponseCallback, NewsFeedModelInt.NewsListCallback, NewsFeedModelInt.NewsLikeCommentUpdate, NewsFeedModel.NewsDetailCallback, NewsFeedModelInt.CommentListCallback, EventPhotosModelInt.NetworkListResponseCallback, ProfileDataModelInt.ProfileDataResponseCallback, NewsFeedModelInt.CatagoryListListener {
    NetworkViewInt networkViewInt;
    HomeViewInt homeViewInt;
    EventPhotosInt eventPhotosInt;
    ProfileDataModelInt profileDataModelInt;
    NewsLikeCommentUpdateCallback likeUpdateCallback;
    private NewsFeedModel newsModel;
    private NewsDetailViewInt newsDetailCallback;
    private CommentListCallback commentListCallback;
    private EventPhotosModel eventPhotosModel;
    private ProfileDataModel profileDataModel;
    private CatagoryCallback catagoryCallback;


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
    public void getEventPhoto(EventPhotosInt eventPhotosInt) {
        new EventPhotosModel().getEventPhotoList(this);
        this.eventPhotosInt = eventPhotosInt;
    }

    @Override
    public void getrofileData(ProfileDataModelInt profileDataModelInt) {
        new ProfileDataModel().getProfileDataList(this);
        this.profileDataModelInt = profileDataModelInt;
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
        newsModel = new NewsFeedModel();
        newsModel.updateNewsLikeComment(newsId, newsStatus, newsComment, this);

    }

    @Override
    public void removeLike(String newsStatusId, NewsLikeCommentUpdateCallback updateCallback) {
        this.likeUpdateCallback = updateCallback;
        if (newsModel == null)
            newsModel = new NewsFeedModel();
        newsModel.removeNewsLike(newsStatusId, this);
    }


    @Override
    public void onSuccessLikeComment(JSONObject updateObj) {
        likeUpdateCallback.successfulUpdateLike(updateObj);
    }

    @Override
    public void onFailResponseNewsLikeComment(String message) {
        likeUpdateCallback.failUpdateResponse(message);
    }

    @Override
    public void onFailRequestNewsLikeComment() {
        likeUpdateCallback.failUpdateRequest();
    }


    //______________NewsDetail__________//


    @Override
    public void getNewsDetail(String newsId, NewsDetailViewInt newsDetailCallback) {
        this.newsDetailCallback = newsDetailCallback;
        if (newsModel == null) {
            newsModel = new NewsFeedModel();
        }
        newsModel.getNewsDetail(newsId, this);
    }


    @Override
    public void onSuccessNewsDetail(NewsObject newsObject) {

        newsDetailCallback.onSuccessNewsDetail(newsObject);
    }

    @Override
    public void onFailNewsDetailRequest() {
        newsDetailCallback.onFailRequest();
    }

    @Override
    public void onFailNewsDetailResponse(String message) {
        newsDetailCallback.onFailResponse(message);
    }


    //_____________Comment List___________//
    @Override
    public void getNewsCommentList(String newsId, String newsStatusId, CommentListCallback commentCallback) {
        this.commentListCallback = commentCallback;
        if (newsModel == null) {
            newsModel = new NewsFeedModel();
        }
        newsModel.getCommentList(newsId, newsStatusId, this);
    }


    @Override
    public void onSuccessCommentList(JSONArray commentList) {
        commentListCallback.onSuccessCommentList(commentList);
    }

    @Override
    public void onFailCommentListRequest() {
        commentListCallback.onFailRequest();
    }

    @Override
    public void onFailCommentResponse(String message) {
        commentListCallback.onFailResponse(message);
    }


    //_______Update News Comment______//

    @Override
    public void updateComment(String newsId, String newsStatusId, String newsComment, String newsStatus, NewsLikeCommentUpdateCallback callback) {
        likeUpdateCallback = callback;
        if (newsModel == null) {
            newsModel = new NewsFeedModel();
        }
        newsModel.updateComment(newsId, newsStatus, newsStatusId, newsComment, this);
    }


    public void onSuccessEventResponse(JSONArray userList) {
        eventPhotosInt.onSuccessEventPhotoList(userList);

    }


    @Override
    public void getCatagoryList(CatagoryCallback catagoryCallback) {
        this.catagoryCallback = catagoryCallback;
        if (newsModel == null) {
            newsModel = new NewsFeedModel();
        }
        newsModel.getCatagories(this);
    }

    @Override
    public void onSuccessCatagoryList(JSONArray catagoryList) {
        catagoryCallback.onSuccessCatagoryList(catagoryList);
    }

    @Override
    public void onFailCatagoryListRequest() {
        catagoryCallback.onFailCatagoryListRequest();
    }

    @Override
    public void onFailCatagoryListResponse(String message) {
        catagoryCallback.onFailCatagoryResponse(message);
    }
}

