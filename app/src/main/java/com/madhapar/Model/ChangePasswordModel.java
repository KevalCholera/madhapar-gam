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
 * Created by Ronak on 9/27/2016.
 */
public class ChangePasswordModel implements ChangePasswordModelInt {
    @Override
    public void changePassword(final String newPassword, final String confirmNewPassword, String otpToken, String contactNumber, final onChangePasswordRequestFinishListener listener) {
        if (TextUtils.isEmpty(newPassword) && TextUtils.isEmpty(confirmNewPassword)) {
            listener.onChangePasswordRequiredFieldError();
        } else if (TextUtils.isEmpty(newPassword)) {
            listener.onNewPasswordError();
        } else if (!(newPassword.toString().length() > 6)) {
            listener.onNewPasswordLengthError();
        } else if (TextUtils.isEmpty(confirmNewPassword)) {
            listener.onConfirmPasswordError();
        } else if (!TextUtils.isEmpty(newPassword) && !TextUtils.isEmpty(confirmNewPassword) && !newPassword.equalsIgnoreCase(confirmNewPassword)) {
            listener.onPasswordMatchError();
        } else {
            changePasswordRequest(newPassword, otpToken, contactNumber, listener);
        }
    }

    private void changePasswordRequest(final String newPassword, final String otpToken, final String contactNumber, final onChangePasswordRequestFinishListener listener) {
        String tag = "changePassword";
        StringRequest changePasswordRequest = new StringRequest(Request.Method.PUT, UtilClass.getChangePasswordUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject changePasswordObj = new JSONObject(response);
                        if (changePasswordObj != null) {
                            if (changePasswordObj.optInt("status") == Constants.ResponseCode.LoginSuccessCode) {
                                listener.onChangePasswordSuccess(changePasswordObj);
                            } else {
                                listener.onFailToChangePassword(changePasswordObj.optString("message"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("changePassword", "request" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onChangePasswordRequestError();
                Log.e("changePassword", "request" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userMobileNo", contactNumber);
                params.put("otpToken", otpToken);
                params.put("password", newPassword);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "JWT" + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return headers;
            }
        };
        changePasswordRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(changePasswordRequest, tag);
    }


}
