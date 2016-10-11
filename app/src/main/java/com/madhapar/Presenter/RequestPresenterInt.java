package com.madhapar.Presenter;

import com.madhapar.View.Adapter.NewsLikeCommentUpdateCallback;
import com.madhapar.View.CommentListCallback;
import com.madhapar.View.EventListInt;
import com.madhapar.View.HomeViewInt;
import com.madhapar.View.NetworkViewInt;
import com.madhapar.View.NewsDetailViewInt;

/**
 * Created by smartsense on 06/10/16.
 */

public interface RequestPresenterInt {
    void getEventList(EventListInt callback);

    void getNetworkList(NetworkViewInt networkViewInt);

    void getNewsList(HomeViewInt homeViewInt);

    void updateLikeComment(String NewsId, String newsStatus, String newsComment, NewsLikeCommentUpdateCallback updateCallback);

    void removeLike(String newsStatusId, NewsLikeCommentUpdateCallback updateCallback);

    void getNewsDetail(String newsId, NewsDetailViewInt newsDetailCallback);

    void getNewsCommentList(String newsId, String newsStatusId, CommentListCallback commentCallback);

    void updateComment(String newsId, String newsStatus, String newsComment, String newsStatusId, NewsLikeCommentUpdateCallback callback);


}
