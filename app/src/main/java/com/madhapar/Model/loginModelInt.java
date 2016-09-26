package com.madhapar.Model;

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

        void onRequiredFieldError();
    }

    public void login(String contactNumber, String password, OnLoginFinishedListener listener);


}
