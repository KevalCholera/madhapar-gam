package com.madhapar.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by smartsense on 22/09/16.
 */

public class LoginModel implements LoginModelInt {

    @Override
    public void login(final String contactNumber, final String password, final LoginModelInt.OnLoginFinishedListener listener) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                if(TextUtils.isEmpty(contactNumber) && TextUtils.isEmpty(password))
                {
                    listener.onRequiredFieldError();
                    error = true;
                }
                else if (TextUtils.isEmpty(contactNumber)) {
                    listener.oncontactNumberError();
                    error = true;
                }
                else if(!(contactNumber.toString().length() >7 && contactNumber.toString().length() < 14)){
                    listener.onContactLenghtError();
                    error = true;
                }
                else if (TextUtils.isEmpty(password)) {
                    listener.onPasswordError();
                    error = true;
                }
                else if((!(password.toString().length() > 6))){
                    listener.onPasswordLengthError();
                    error = true;
                }
                if (!error) {
                    listener.onSuccess();
                }
            }
        }, 2000);
    }
}

