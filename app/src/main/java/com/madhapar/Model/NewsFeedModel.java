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
import com.madhapar.View.Adapter.NewsLikeCommentUpdateCallback;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ronak on 10/7/2016.
 */
public class NewsFeedModel implements NewsFeedModelInt {

    @Override
    public void getNewsData(final NewsListCallback newsListCallback) {
        String tag = "newsFeed";
        StringRequest newsRequest = new StringRequest(Request.Method.GET, UtilClass.getNewsFeedUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject newsObj = new JSONObject(response);
                        if (newsObj != null) {
                            Log.i("*****", "response" + response);
                            if (newsObj.optInt("status") == Constants.ResponseCode.SuccessCode) {
                                sendNewsList(newsObj.optJSONArray("response"), newsListCallback);
                            } else {
                                newsListCallback.onFailResponseNewsList(newsObj.optString("message"));
                            }
                        } else {
                            newsListCallback.onFailRequestNewsList();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                newsListCallback.onFailRequestNewsList();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        newsRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MadhaparGamApp.getAppInstance().addToRequestQueue(newsRequest, tag);
    }


    private void sendNewsList(final JSONArray newsArray, final NewsListCallback newsListCallback) {
        List<NewsObject> newsList = new ArrayList<>();
        if (newsArray != null) {
            for (int i = 0; i < newsArray.length(); i++) {
                NewsObject newsObject = new NewsObject();
                newsObject.setNewsTitle(newsArray.optJSONObject(i).optString("newsTitle"));
                JSONObject location = newsArray.optJSONObject(i).optJSONObject("newsLocation");
                if (location != null)
                    newsObject.setNewsCity(location.optString("locationName"));
                newsObject.setNewsDescription(newsArray.optJSONObject(i).optString("newsDescription"));
                newsObject.setNewsCommentCount(newsArray.optJSONObject(i).optString("newsComments"));
                newsObject.setNewsImageArray(newsArray.optJSONObject(i).optJSONArray("newsImages").toString());
                newsObject.setNewsLikeCount(newsArray.optJSONObject(i).optString("newsLikes"));
                newsObject.setNewsId(newsArray.optJSONObject(i).optString("newsId"));
                newsObject.setNewsDataAndTime(newsArray.optJSONObject(i).optString("newsCreatedDate"));
                newsObject.setCommented(newsArray.optJSONObject(i).optBoolean("isCommented"));
                newsObject.setCommented(newsArray.optJSONObject(i).optBoolean("isCommented"));
                newsObject.setNewsStatusId(newsArray.optJSONObject(i).optString("newsStatusId"));
                newsList.add(newsObject);
            }
        }
        newsListCallback.onSuccessNewsList(newsList);
    }


    @Override
    public void updateNewsLikeComment(final String newsId, final String newsStatus, final String newsComment, final NewsFeedModelInt.NewsLikeCommentUpdate newsLikeCommentUpdateCallback) {
        String tag = "updateLikes";
        StringRequest likeRequest = new StringRequest(Request.Method.POST, UtilClass.getLikeUpdateUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject likeObj = new JSONObject(response);
                        if (likeObj != null) {
                            if (likeObj.optInt("status") == Constants.ResponseCode.SignUpSuccessCode) {
                                NewsObject newsObject = new NewsObject();

                                newsLikeCommentUpdateCallback.onSuccessLikeComment();
                            } else {
                                newsLikeCommentUpdateCallback.onFailResponseNewsLikeComment(likeObj.optString("message"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("update", "resonse" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                newsLikeCommentUpdateCallback.onFailRequestNewsLikeComment();
                Log.e("update", "resonse" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("newsId", newsId);
                params.put("newsStatus", newsStatus);
                params.put("newsComment", newsComment);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        likeRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(likeRequest, tag);


    }

    @Override
    public void removeNewsLike(String newsStatusId, final NewsLikeCommentUpdate updateCallback) {
        String tag = "removeLike";
        StringRequest removeLikeRequest = new StringRequest(Request.Method.DELETE, UtilClass.getRemoveLikeUrl(newsStatusId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("remove", "response" + response);
                    JSONObject removeObj = new JSONObject(response);
                    if (removeObj != null) {
                        if (removeObj.optInt("status") == Constants.ResponseCode.RemoveLikeSuccess) {
                            updateCallback.onSuccessLikeComment();
                        } else {
                            updateCallback.onFailResponseNewsLikeComment(removeObj.optString("message"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateCallback.onFailRequestNewsLikeComment();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        removeLikeRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MadhaparGamApp.getAppInstance().addToRequestQueue(removeLikeRequest, tag);
    }

    @Override
    public void getNewsDetail(String newsId, final NewsDetailCallback newsDetailCallback) {
        String tag = "newsDetail";
        StringRequest newsDetailRequest = new StringRequest(Request.Method.GET, UtilClass.getNewsDetailUrl(newsId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject newsDataObj = new JSONObject(response);
                        if (newsDataObj != null) {
                            Log.e("newsDetail", "response" + response);
                            if (newsDataObj.optInt("status") == Constants.ResponseCode.SuccessCode) {
                                JSONObject newsResponseObj = newsDataObj.optJSONObject("response");
                                sendNewsData(newsResponseObj, newsDetailCallback);
                            } else {
                                newsDetailCallback.onFailNewsDetailResponse(newsDataObj.optString("message"));
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
                newsDetailCallback.onFailNewsDetailRequest();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        newsDetailRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(newsDetailRequest, tag);
    }


    private void sendNewsData(JSONObject newsJsonObj, NewsDetailCallback newsDetailCallback) {
        NewsObject newsObject = new NewsObject();
        newsObject.setNewsId(newsJsonObj.optString("newsId"));
        newsObject.setNewsTitle(newsJsonObj.optString("newsTitle"));
        newsObject.setNewsDescription(newsJsonObj.optString("newsDescription"));
        newsObject.setNewsDataAndTime(newsJsonObj.optString("newsCreatedDate"));
        newsObject.setNewsImageArray(newsJsonObj.optJSONArray("newsImages").toString());
        newsObject.setNewsStatusId(newsJsonObj.optString("newsStatusId"));
        JSONObject locationObj = newsJsonObj.optJSONObject("newsLocation");
        if (locationObj != null)
            newsObject.setNewsCity(locationObj.optString("locationName"));
        newsObject.setNewsLikeCount(newsJsonObj.optString("newsLikes"));
        newsObject.setNewsCommentCount(newsJsonObj.optString("newsComments"));
        newsObject.setCommented(newsJsonObj.optBoolean("isCommented"));
        newsDetailCallback.onSuccessNewsDetail(newsObject);
    }


    @Override
    public void getCommentList(String newsId, String newsStausId, final CommentListCallback commentListCallback) {
        String tag = "commentList";
        StringRequest commentRequest = new StringRequest(Request.Method.GET, UtilClass.getCommentListUrk(newsId, newsStausId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject commentObj = new JSONObject(response);
                        if (commentObj != null) {
                            Log.e("******", "commentResponse" + response);
                            if (commentObj.optInt("status") == Constants.ResponseCode.SuccessCode) {
                                commentListCallback.onSuccessCommentList(commentObj.optJSONArray("response"));
                            } else {
                                commentListCallback.onFailCommentResponse(commentObj.optString("message"));
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
                commentListCallback.onFailCommentListRequest();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        commentRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(commentRequest, tag);
    }


}
