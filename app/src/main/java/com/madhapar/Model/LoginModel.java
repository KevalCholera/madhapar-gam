package com.madhapar.Model;

import android.support.v7.app.AppCompatActivity;
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
import com.madhapar.View.UserVerifyActivity;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smartsense on 22/09/16.
 */

public class LoginModel implements LoginModelInt {

    @Override
    public void login(final String loginId, final String password, final LoginModelInt.onLoginFinishListener listener, AppCompatActivity activity) {
        if (TextUtils.isEmpty(loginId) && TextUtils.isEmpty(password)) {
            listener.onLoginRequiredFieldError();
        } else if (TextUtils.isEmpty(loginId)) {
            listener.onLogincontactNumberError();
        } else if (!(loginId.length() > 7 && loginId.length() < 14)) {
            listener.onLoginContactLenghtError();
        } else if (TextUtils.isEmpty(password)) {
            listener.onLoginPasswordError();
        } else if (((password.length() < 6))) {
            listener.onLoginPasswordLengthError();
        } else {
            doLogin(listener, loginId, password, activity);
        }
    }

    private void doLogin(final LoginModelInt.onLoginFinishListener listener, final String login, final String password, final AppCompatActivity activity) {
        String tag = "login";
        StringRequest loginRequest = new StringRequest(Request.Method.POST, UtilClass.getLoginUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && !response.equalsIgnoreCase("")) {
                    try {
                        JSONObject logiObject = new JSONObject(response);
                        if (logiObject != null) {
                            if (logiObject.optInt("status") == Constants.ResponseCode.LoginSuccessCode) {
                                SharedPreferenceUtil.putValue(Constants.UserData.token, logiObject.optString("token"));
                                JSONObject userObject = logiObject.optJSONObject("user");
                                if (userObject != null) {
                                    try {
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserId, userObject.optString("userId"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserMobileNo, userObject.optString("userMobileNo"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserFirstName, userObject.optString("userFirstName"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserLastName, userObject.optString("userLastName"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserLocationId, userObject.optString("locationId"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserLocationName, userObject.optString("locationName"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserProfilePic, userObject.optString("userProfilePic"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserProfession, userObject.optString("userProfession"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserEmail, userObject.optString("email"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserDOB, userObject.optString("userDOB"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserBloodGroup, userObject.optString("userBloodGroup"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserFamilyMemberCount, userObject.optString("userFamilyMemberCount"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserRegistrationId, userObject.optString("userRegistrationId"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.isVerified, userObject.optBoolean("isVerified"));
                                        SharedPreferenceUtil.putValue(Constants.UserData.UserFBProfileName, userObject.optString("userFBProfileName"));
                                        SharedPreferenceUtil.save();
//                                        if (!userObject.optBoolean("isVerified")) {
//                                            UtilClass.changeActivity(activity, UserVerifyActivity.class, false);
//                                        } else {
                                        listener.onLoginSuccess();
                                        //   }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                SharedPreferenceUtil.save();
                            } else {
                                listener.onLoginFailError(logiObject.optString("message"));
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
                listener.onRequestError();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("password", password);
                params.put("userMobileNo", login);
                params.put("deviceToken", "12341234123412341234123412341234");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT" + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return params;
            }
        };

        loginRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().

                addToRequestQueue(loginRequest, tag);
    }

}

