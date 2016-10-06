package com.madhapar.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;

/**
 * Created by smartsense on 30/09/16.
 */

public class SplashScreenActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (SharedPreferenceUtil.contains(Constants.UserData.UserFirstName) && SharedPreferenceUtil.contains(Constants.UserData.UserId)) {
            if (!SharedPreferenceUtil.getString(Constants.UserData.UserFirstName, "").equalsIgnoreCase("") && !SharedPreferenceUtil.getString(Constants.UserData.UserId, "").equalsIgnoreCase("")) {
                UtilClass.changeActivity(SplashScreenActivity.this, MainActivity.class, true);
            } else {
                UtilClass.changeActivity(SplashScreenActivity.this, LoginActivity.class, true);
            }
        } else {
            UtilClass.changeActivity(SplashScreenActivity.this, LoginActivity.class, true);
        }
    }
}
