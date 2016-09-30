package com.madhapar.Model;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by Ronak on 9/27/2016.
 */
public class ChangePasswordModel implements ChangePasswordModelInt {


    @Override
    public void chnagePassword(final String newPassword, final String confirmNewPassword, final OnLoginFinishedListener listener) {

                boolean error = false;
                if(TextUtils.isEmpty(newPassword) && TextUtils.isEmpty(confirmNewPassword)){
                    listener.onChangePasswordRequiredFieldError();
                    error = true;
                }
                else if(TextUtils.isEmpty(newPassword)){
                    listener.onNewPasswordError();
                    error = true;
                }
                else if(!(newPassword.toString().length() >6)){
                    listener.onNewPasswordLengthError();
                    error = true;
                }
                else if(TextUtils.isEmpty(confirmNewPassword)){
                    listener.onConfirmPasswordError();
                    error = true;
                }
                else if(!TextUtils.isEmpty(newPassword)&&!TextUtils.isEmpty(confirmNewPassword)&&!newPassword.equalsIgnoreCase(confirmNewPassword)){
                    listener.onPasswordMatchError();
                    error = true;
                }
                if (!error) {
                    listener.onChangePasswordSuccess();
                }
            }
}
