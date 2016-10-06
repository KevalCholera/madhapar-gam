package com.madhapar.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONObject;

/**
 * Created by smartsense on 06/10/16.
 */

public class UserVerifyActivity extends BaseActivity implements ForgetPasswordViewInt {
    private PresenterClass presenterClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenterClass = new PresenterClass();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verify);

        if (UtilClass.isInternetAvailabel(this)) {
            presenterClass.forgetPasswordCredentials(SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo, ""), this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }
    }

    @Override
    public void forgetPasswordValidateResult(int check) {
        Log.e("verify", "check0" + check);
    }

    @Override
    public void forgotPasswordSuccess(JSONObject otpResponse) {
        if (UtilClass.isInternetAvailabel(this)) {
            if (presenterClass == null) {
                presenterClass = new PresenterClass();
            }
            presenterClass.alert(this, otpResponse, SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo, ""));
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }
    }

    @Override
    public void forgotPasswrodFail(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, this, 0);
    }

    @Override
    public void forgotPasswordRequestError() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }
}
