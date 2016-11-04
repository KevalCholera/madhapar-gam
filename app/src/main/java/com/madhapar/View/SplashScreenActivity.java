package com.madhapar.View;

import android.os.Bundle;

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

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
//
                    if (SharedPreferenceUtil.getString(Constants.UserData.token, Constants.RequestConstants.DefaultToken).equalsIgnoreCase(Constants.RequestConstants.DefaultToken)) {
                        UtilClass.changeActivity(SplashScreenActivity.this, LoginActivity.class, true);
                    } else {
                        UtilClass.changeActivity(SplashScreenActivity.this, MainActivity.class, true);
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
