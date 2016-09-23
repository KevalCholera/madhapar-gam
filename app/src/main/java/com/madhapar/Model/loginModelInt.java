package com.madhapar.Model;

import android.app.Activity;

/**
 * Created by smartsense on 22/09/16.
 */

public interface LoginModelInt {

    interface OnLoginFinishedListener {

        void oncontactNumberError();

        void onPasswordError();

        void onContactLenghtError();

        void onPasswordLengthError();

        void onSuccess();
    }

    public void login(String contactNumber, String password, OnLoginFinishedListener listener);


}
