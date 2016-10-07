package com.madhapar.Model;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

/**
 * Created by Ronak on 9/26/2016.
 */
public interface ForgetPasswordModelInt {


    interface onSendOtpListener {

        void onForgetContactLenghtError();

        void onForgetContactNumberError();

        void onForgetSuccess(JSONObject optResponse);

        void onForgotFail(String message);

        void onOtpRequestError();
    }

    interface onVerifyOtpListener {
        void onOtpVerify(JSONObject verifyResponse);

        void onOtpVerificationFail(String message);

        void onVerifyOtpRequestError();
    }


    void sendOtp(String contactNumber, onSendOtpListener listener, int type);

    void verifyOtp(String contactNumber, onVerifyOtpListener listener, String otp);

    void verifyUserOtp(String contactNumber, onVerifyOtpListener listener, String otp);
}

