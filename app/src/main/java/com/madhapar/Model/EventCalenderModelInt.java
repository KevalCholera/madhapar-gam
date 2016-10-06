package com.madhapar.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronak on 9/30/2016.
 */
public interface EventCalenderModelInt {


    interface EventListCallback {
        void onSuccessEventList(JSONArray eventArray);
        void onFailEventList(String errorMessage);
    }

    void getEventList(EventListCallback eventListCallback);


}
