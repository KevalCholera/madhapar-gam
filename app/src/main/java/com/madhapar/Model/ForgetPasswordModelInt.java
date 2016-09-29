package com.madhapar.Model;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ronak on 9/26/2016.
 */
public interface ForgetPasswordModelInt {


    interface OnLoginFinishedListener {

        void onForgetContactLenghtError();

        void onForgetContactNumberError();

        void onForgetSuccess();
    }
    public void login(String contactNumber, OnLoginFinishedListener listener);
}

