package com.madhapar.View.Adapter;

import com.madhapar.Model.NewsObject;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by smartsense on 08/10/16.
 */

public interface NewsLikeCommentUpdateCallback {
    void successfulUpdateLike(JSONObject updateObj);

    void failUpdateResponse(String message);

    void failUpdateRequest();
}
