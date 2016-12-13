package com.madhapar.Model;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.madhapar.Application.MadhaparGamApp;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by smartsense on 12/10/16.
 */

public class EventModel implements EventModelInt {

    @Override
    public void getEventDetail(String eventId, final onEventDetailListener mEventListener) {
        String tag = "eventDetail";
        StringRequest eventdetailRequest = new StringRequest(Request.Method.GET, UtilClass.getEventstUrl(eventId, false), new Response.Listener<String>() {
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
        StringRequest eventListRequest = new StringRequest(Request.Method.GET, UtilClass.getEventstUrl("", true), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject eventObject = new JSONObject(response);

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
        String tag = "eventStatusCreate";
        StringRequest eventStatusRequest = new StringRequest(Request.Method.POST, UtilClass.getEventStatustUrl("", "", 1), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("eventStatus", "create" + response);
                    if (response != null) {
                        JSONObject eventStautsObj = new JSONObject(response);
                        if (eventStautsObj != null) {
                            if (eventStautsObj.optInt("status") == Constants.ResponseCode.SignUpSuccessCode) {
                                mEventStatusListener.onSuccessStatusCreate(eventStautsObj);
                            } else {
                                mEventStatusListener.onFailStatusCreateResponse(eventStautsObj.optString("message"));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
    public void getEventStatusList(String eventId, String eventStatus, final EventStautsListListener mEventStatusListListener) {
        String tag = "eventStatusList";
        StringRequest eventStatusListRequest = new StringRequest(Request.Method.GET, UtilClass.getEventStatustUrl(eventId, eventStatus, 0), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        Log.d("eventStatus", "list" + response);
                        JSONObject statusObj = new JSONObject(response);

                        if (statusObj.optInt("status") == Constants.ResponseCode.SuccessCode) {
                            mEventStatusListListener.onSuccessEventStatusList(statusObj.optJSONArray("response"));
                        } else {
                            mEventStatusListListener.onFailStatusListResponse(statusObj.optString("message"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
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

    @Override
    public void updateEventStatus(String eventStatusId, final String eventStatus, final String count, final EventStatusUpdateListener mEventUpdateListener) {
        String tag = "eventStatusUpdate";
        StringRequest statusUpdateRequest = new StringRequest(Request.Method.PUT, UtilClass.getEventStatustUrl("", eventStatusId, 2), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject updateObj = new JSONObject(response);
                        if (updateObj != null) {
                            if (updateObj.optInt("status") == Constants.ResponseCode.SuccessCode) {
                                mEventUpdateListener.onSuccessEventUpdate(updateObj);
                            } else {
                                mEventUpdateListener.onFailEventUpdateResponse(updateObj.optString("message"));
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
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        if (obj != null) {
                            mEventUpdateListener.onFailEventUpdateResponse(obj.optString("message"));
                        } else {
                            mEventUpdateListener.onFailEventUpdateRequest();
                        }
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }

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
                params.put("eventUserCount", count);
                params.put("eventStatusType", eventStatus);

                return params;
            }
        };
        statusUpdateRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(statusUpdateRequest, tag);
    }


}
