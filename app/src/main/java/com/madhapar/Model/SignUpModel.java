package com.madhapar.Model;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

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
 * Created by Ronak on 9/26/2016.
 */
public class SignUpModel implements SignUpModelInt {

    @Override
    public void signup(final String firstName, final String lastName, final String contactNumber, final String password, final String familyMember, final OnSignUpFinishedListener listener, AppCompatActivity activity) {
        Log.e("Password", password);
        if (TextUtils.isEmpty(contactNumber) && TextUtils.isEmpty(password) && TextUtils.isEmpty(firstName) && TextUtils.isEmpty(familyMember) && TextUtils.isEmpty(lastName)) {
            listener.onSignUpRequiredFieldError();
        } else if (TextUtils.isEmpty(firstName)) {
            listener.onSignUpFirstNameError();
        } else if (TextUtils.isEmpty(lastName)) {
            listener.onSignUplastNameError();
        } else if (TextUtils.isEmpty(contactNumber)) {
            listener.onSignupContactNumberError();
        } else if (!(contactNumber.toString().length() > 7 && contactNumber.toString().length() < 14)) {
            listener.onSignUpContactLenghtError();
        } else if (TextUtils.isEmpty(password)) {
            listener.onsignUpPasswordError();
        } else if ((!(password.toString().length() > 6))) {
            listener.onSignupPasswordLengthError();
        } else if (!isValidFamilyMember(familyMember)) {
            listener.onSignUpFamilyMemberError();
        } else {
            doSignup(firstName, lastName, contactNumber, password, familyMember, listener, activity);
        }
    }

    private void doSignup(final String userFirstName, final String userLastName, final String userMobileNo, final String password, final String familyMemberCount, final SignUpModelInt.OnSignUpFinishedListener listener, final AppCompatActivity activity) {
        String tag = "signUp";
        StringRequest signUpRequest = new StringRequest(Request.Method.POST, UtilClass.getSignupUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject signUpObject = new JSONObject(response);
                        if (signUpObject != null) {
                            if (signUpObject.optInt("status") == Constants.ResponseCode.SignUpSuccessCode) {
                                JSONObject responseObj = signUpObject.optJSONObject("response");
                                if (responseObj != null) {
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserId, responseObj.optString("userId"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserMobileNo, responseObj.optString("userMobileNo"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserLocationName, responseObj.optString("userLocation"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserFirstName, responseObj.optString("userFirstName"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserLastName, responseObj.optString("userLastName"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserProfession, responseObj.optString("userProfession"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserEmail, responseObj.optString("email"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserDOB, responseObj.optString("userDOB"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserBloodGroup, responseObj.optString("userBloodGroup"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserFamilyMemberCount, responseObj.optString("userFamilyMemberCount"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserRegistrationId, responseObj.optString("userRegistrationId"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.isVerified, responseObj.optString("isVerified"));
                                    SharedPreferenceUtil.putValue(Constants.UserData.UserFBProfileName, responseObj.optString("userFBProfileName"));
                                    SharedPreferenceUtil.save();
                                    if (!responseObj.optBoolean("isVerified")) {
                                        listener.onSignUpSuccess();
                                        //UtilClass.changeActivity(activity, UserVerifyActivity.class, false);
                                    } else {
                                        listener.onSignUpSuccess();
                                    }
                                    UtilClass.displyMessage(signUpObject.optString("message"), MadhaparGamApp.getAppInstance(), Toast.LENGTH_SHORT);
                                }
                            } else {
                                listener.onSignUpFailError(signUpObject.optString("message"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("signup", "response" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onSignUpRequestError();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userFirstName", userFirstName);
                params.put("userLastName", userLastName);
                params.put("userMobileNo", userMobileNo);
                params.put("password", password);
                params.put("userFamilyMemberCount", familyMemberCount);
                Log.i("signup", "params" + params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT" + SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken));
                return params;
            }
        };
        signUpRequest.setRetryPolicy(new DefaultRetryPolicy(UtilClass.RetryTimeOut,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MadhaparGamApp.getAppInstance().addToRequestQueue(signUpRequest, tag);
    }

    private boolean isValidFamilyMember(String familyMember) {
        if (TextUtils.isEmpty(familyMember)) {
            return false;
        } else {
            try {
                Long value = Long.valueOf(familyMember);
                if (value < 100) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    }

}
