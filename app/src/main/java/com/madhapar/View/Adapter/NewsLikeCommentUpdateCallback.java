package com.madhapar.View.Adapter;

import com.madhapar.Model.NewsObject;

import org.json.JSONArray;

/**
 * Created by smartsense on 08/10/16.
 */

public interface NewsLikeCommentUpdateCallback {
    void successfulUpdateLike();

    void failUpdateResponse(String message);

    void failUpdateRequest();
}
