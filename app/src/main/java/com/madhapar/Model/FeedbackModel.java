package com.madhapar.Model;

import android.os.Handler;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ronak on 9/29/2016.
 */
public class FeedbackModel implements FeedbackModelInt {
    @Override
    public void feedback(final String subject, final String feedback, final OnFeedbackPostListener listener) {

        if (TextUtils.isEmpty(subject) && TextUtils.isEmpty(feedback)) {
            listener.onFeedbackFieldRequiredError();
        } else if (TextUtils.isEmpty(subject)) {
            listener.onFeedbackSubjectRequiredError();
        } else if (subject.length() < 5) {
            listener.onFeedbackSubjectValidError();
        } else if (TextUtils.isEmpty(feedback)) {
            listener.onFeedbackRequiredError();
        } else if (feedback.length() < 10) {
            listener.onFeedbackValidError();
        } else {
            submitFeedback(subject, feedback, listener);
        }

    }

    private void submitFeedback(final String subject, final String feedback, final OnFeedbackPostListener listener) {
        String tag = "feedback";
        StringRequest feedbackRequest = new StringRequest(Request.Method.POST, UtilClass.getFeedbackUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject feedbackObj = new JSONObject(response);
                        if (feedbackObj.optInt("status") == Constants.ResponseCode.SignUpSuccessCode) {
                            listener.onSuccessPostFeedback(feedbackObj.optString("message"));
                        } else {
                            listener.onFailFeedbackResponse(feedbackObj.optString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                listener.onFailFeedbackRequest();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("feedbackSubject", subject);
                params.put("feedbackDescription", feedback);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return params;
            }
        };
        feedbackRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().
                addToRequestQueue(feedbackRequest, tag);
    }

}
