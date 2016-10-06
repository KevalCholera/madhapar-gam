package com.madhapar.Model;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by smartsense on 06/10/16.
 */

public interface LoginModelInt {

    public interface onLoginFinishListener {
        void onLoginRequiredFieldError();

        void onLogincontactNumberError();

        void onLoginPasswordError();

        void onLoginContactLenghtError();

        void onLoginPasswordLengthError();

        void onLoginSuccess();

        void onLoginFailError(String failMessage);

        void onRequestError();

    }

    void login(String contactNumber, String password, LoginModelInt.onLoginFinishListener listener, AppCompatActivity activity);
}
