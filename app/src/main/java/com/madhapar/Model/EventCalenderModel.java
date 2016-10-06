package com.madhapar.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.madhapar.Application.MadhaparGamApp;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ronak on 9/30/2016.
 */
public class EventCalenderModel implements EventCalenderModelInt {


    @Override
    public void getEventList(final EventListCallback eventListCallback) {
        String tag = "getEventList";
        StringRequest eventListRequest = new StringRequest(Request.Method.GET, UtilClass.getEventListUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject eventObject = new JSONObject(response);
                    if (eventObject.optInt("status") == Constants.ResponseCode.SuccessCode) {
                        if (eventObject.has("response")) {
                            eventListCallback.onSuccessEventList(eventObject.getJSONArray("response"));
                        }
                    } else {
                        eventListCallback.onFailEventList(eventObject.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                eventListCallback.onFailEventList(error.getMessage());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        eventListRequest.setRetryPolicy(new

                DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        );
        MadhaparGamApp.getAppInstance().

                addToRequestQueue(eventListRequest, tag);

    }
}
