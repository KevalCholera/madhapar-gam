package com.madhapar.Presenter;

import com.madhapar.View.Adapter.NewsLikeCommentUpdateCallback;
import com.madhapar.View.EventListInt;
import com.madhapar.View.HomeViewInt;
import com.madhapar.View.NetworkViewInt;

/**
 * Created by smartsense on 06/10/16.
 */

public interface RequestPresenterInt {
    void getEventList(EventListInt callback);

    void getNetworkList(NetworkViewInt networkViewInt);

    void getNewsList(HomeViewInt homeViewInt);

    void updateLikeComment(String NewsId, String newsStatus, String newsComment, NewsLikeCommentUpdateCallback updateCallback);
}
