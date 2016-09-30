package com.madhapar.Model;

/**
 * Created by Ronak on 9/27/2016.
 */
public interface ChangePasswordModelInt {
    interface OnLoginFinishedListener{

        void onChangePasswordSuccess();

        void onNewPasswordError();

        void onConfirmPasswordError();

        void onNewPasswordLengthError();

        void onPasswordMatchError();

        void onChangePasswordRequiredFieldError();
    }
    public void chnagePassword(String newPassword,String confirmNewPassword, OnLoginFinishedListener listener);

}
