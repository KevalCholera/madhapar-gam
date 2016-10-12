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
import com.madhapar.View.EventDetailCallback;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smartsense on 12/10/16.
 */

public class EventModel implements EventModelInt {


    @Override
    public void getEventDetail(String eventId, final onEventDetailListener mEventListener) {
        String tag = "eventDetail";
        StringRequest eventdetailRequest = new StringRequest(Request.Method.GET, UtilClass.getEventDetailUrl(eventId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject eventDetail = new JSONObject(response);
                        if (eventDetail.optInt("status") == Constants.ResponseCode.SuccessCode) {
                            mEventListener.onSuccessEventDetail(eventDetail);
                        } else {
                            mEventListener.onFailResponse(eventDetail.optString("message"));
                        }
                    } catch (JSONException q) {
                        q.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mEventListener.onFailRequest();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));

                return header;
            }
        };
        eventdetailRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(eventdetailRequest, tag);
    }

    @Override
    public void getEventList(final EventListListener mEventListener) {
        String tag = "getEventList";
        StringRequest eventListRequest = new StringRequest(Request.Method.GET, UtilClass.getEventListUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject eventObject = new JSONObject(response);
                    Log.e("event", "response" + response);
                    if (eventObject.optInt("status") == Constants.ResponseCode.SuccessCode) {
                        if (eventObject.has("response")) {
                            mEventListener.onSuccessEventList(eventObject.optJSONArray("response"));
                        }
                    } else {
                        mEventListener.onFailEventEventListResponse(eventObject.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mEventListener.onFailEventListRequest();

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

    @Override
    public void createEventStatus(final String eventId, final String eventStatusType, final String count, final EventStatusCreateListener mEventStatusListener) {
        String tag = "eventStatus";
        StringRequest eventStatusRequest = new StringRequest(Request.Method.POST, UtilClass.getEventStatudUpdate(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("eventStatusUpdate", response.toString());
                if (response != null) {
                    try {
                        JSONObject eventStautsObj = new JSONObject(response);
                        if (eventStautsObj != null) {
                            if (eventStautsObj.optInt("status") == Constants.ResponseCode.SuccessCode) {
                                mEventStatusListener.onSuccessStatusCreate(eventStautsObj.optJSONObject("response"));
                            } else {
                                mEventStatusListener.onFailStatusCreateResponse(eventStautsObj.optString("message"));
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
                mEventStatusListener.onFailStatusCreateRequest();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("eventId", eventId);
                params.put("eventStatusType", eventStatusType);
                params.put("eventUserCount", count);
                return params;
            }
        };
        eventStatusRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MadhaparGamApp.getAppInstance().addToRequestQueue(eventStatusRequest, tag);
    }

    @Override
    public void updateEventStatus(String eventId, String eventStatus, String count, EventStatusUpdateListener mEventUpdateListener) {

    }


    @Override
    public void getEventStatusList(String eventId, String eventStatus, final EventStautsListListener mEventStatusListListener) {
        String tag = "eventStatusList";
        StringRequest eventStatusListRequest = new StringRequest(Request.Method.GET, UtilClass.getEventStatusListUrl(eventId, eventStatus), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.e("eventStatusList", "response" + response);
                    try {
                        JSONObject statusObj = new JSONObject(response);
                        if (statusObj.optInt("status") == Constants.ResponseCode.SuccessCode) {
                            mEventStatusListListener.onSuccessEventStatusList(statusObj.optJSONArray("response"));
                        } else {
                            mEventStatusListListener.onFailStatusListResponse(statusObj.optString("message"));
                        }
                    } catch (JSONException w) {
                        w.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mEventStatusListListener.onFailStatusListRequest();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        eventStatusListRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(eventStatusListRequest, tag);

    }


}
