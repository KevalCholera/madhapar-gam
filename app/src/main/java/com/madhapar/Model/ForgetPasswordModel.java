package com.madhapar.Model;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by Ronak on 9/26/2016.
 */
public class ForgetPasswordModel implements ForgetPasswordModelInt {

    @Override
    public void login(final String contactNumber, final OnLoginFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                if (TextUtils.isEmpty(contactNumber)) {
                    listener.onForgetContactNumberError();
                    error = true;
                }
                else if(!(contactNumber.toString().length() >7 && contactNumber.toString().length() < 14)){
                    listener.onForgetContactLenghtError();
                    error = true;
                }
                if (!error) {
                    listener.onForgetSuccess();
                }
            }
        }, 2000);
    }
}
