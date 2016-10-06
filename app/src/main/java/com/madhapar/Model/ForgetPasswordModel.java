package com.madhapar.Model;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smartsense.newproject.R;
import com.madhapar.Application.MadhaparGamApp;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ronak on 9/26/2016.
 */
public class ForgetPasswordModel implements ForgetPasswordModelInt {

    @Override
    public void sendOtp(final String contactNumber, final onSendOtpListener listener) {
        Log.e("request", "1");
        if (TextUtils.isEmpty(contactNumber)) {
            listener.onForgetContactNumberError();
        } else if (!(contactNumber.toString().length() > 7 && contactNumber.toString().length() < 14)) {
            listener.onForgetContactLenghtError();
        } else {
            sendForgotPasswordOtp(contactNumber, listener);
        }

    }


    private void sendForgotPasswordOtp(final String contactNubmer,
                                       final onSendOtpListener listener) {
        String tag = "sendOtp";
        StringRequest otpRequest = new StringRequest(Request.Method.POST, UtilClass.getOtpUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject otpObj = new JSONObject(response);
                        Log.e("Response:   ####", "otp" + response);
                        if (otpObj.optInt("status") == Constants.ResponseCode.SignUpSuccessCode) {
                            listener.onForgetSuccess(otpObj);
                        } else {
                            listener.onForgotFail(otpObj.optString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("otp", "request" + error);
                listener.onOtpRequestError();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userMobileNo", contactNubmer);
                params.put("otpType", "1");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        otpRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(otpRequest, tag);

    }


    @Override
    public void verifyOtp(final String contactNumber, final onVerifyOtpListener listener,
                          final String otpValue) {
        String tag = "verifyOtp";
        StringRequest verifyOtpRequest = new StringRequest(Request.Method.PUT, UtilClass.getVerifyOtpUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response:   ####", "otpVerify" + response);
                if (response != null) {
                    try {
                        JSONObject verifyObjet = new JSONObject(response);
                        if (verifyObjet != null) {
                            if (verifyObjet.optInt("status") == Constants.ResponseCode.OtpVerificationSuccess) {
                                listener.onOtpVerify(verifyObjet);
                            }
                        } else {
                            listener.onOtpVerificationFail(verifyObjet.optString("message"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("otpVerify", "request" + error);
                listener.onOtpVerificationFail(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userMobileNo", contactNumber);
                params.put("otpValue", otpValue);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", Constants.RequestConstants.HeaderPostfix + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return header;
            }
        };
        verifyOtpRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(verifyOtpRequest, tag);

    }


}
