package com.madhapar.Model;

import org.json.JSONObject;

/**
 * Created by smartsense on 22/09/16.
 */

public interface LoginModelInt {

    interface OnLoginFinishedListener {

        void onLogincontactNumberError();

        void onLoginPasswordError();

        void onLoginContactLenghtError();

        void onLoginPasswordLengthError();

        void onLoginSuccess();

        void onLoginRequiredFieldError();
        void onLoginFailError(JSONObject errorObject);
      void  onLoginRequestError();
    }

    public void login(String contactNumber, String password, OnLoginFinishedListener listener);


}
