package com.madhapar.Model;

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
import com.madhapar.View.ProfileUpdateCallback;
import com.mpt.storage.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smartsense on 20/10/16.
 */

public class ProfileUpdateModel implements ProfileUpdateModelInt {
    @Override
    public void updateProfileUserName(final Map<String, String> params, String userId, ProfileUpdateListener mProfileUpdateListener) {
        String tag = "updateProfile";
        StringRequest profileRequest = new StringRequest(Request.Method.PUT, UtilClass.getProfileUpdateUrl(userId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", "profileUpdate" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        profileRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().
                addToRequestQueue(profileRequest, tag);
    }

}
