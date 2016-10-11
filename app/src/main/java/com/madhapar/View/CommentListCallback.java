package com.madhapar.View;

import org.json.JSONArray;

/**
 * Created by smartsense on 10/10/16.
 */

public interface CommentListCallback {
    void onSuccessCommentList(JSONArray commentList);

    void onFailRequest();

    void onFailResponse(String mesaage);

}
