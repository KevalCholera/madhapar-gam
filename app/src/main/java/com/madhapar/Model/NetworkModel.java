package com.madhapar.Model;

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
 * Created by Ronak on 10/5/2016.
 */
public class NetworkModel implements NetworkModelInt {


    @Override
    public void getNetworkList(final NetworkListResponseCallback callback) {
        String tag = "userList";
        StringRequest userListRequest = new StringRequest(Request.Method.GET, UtilClass.getUserUrl("", true), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject userObject = new JSONObject(response);

                        if (userObject != null) {
                            if (userObject.optInt("status") == Constants.ResponseCode.SuccessCode) {
                                JSONArray userList = userObject.optJSONArray("response");
                                if (userList != null) {
                                    callback.onSuccessResponse(userList);
                                }
                            } else {
                                callback.onFailResponse(userObject.optString("message"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailRequest();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        userListRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(userListRequest, tag);

    }
}
