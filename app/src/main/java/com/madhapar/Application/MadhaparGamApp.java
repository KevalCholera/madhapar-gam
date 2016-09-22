package com.madhapar.Application;

import android.app.Application;

import com.mpt.storage.SharedPreferenceUtil;
import com.onesignal.OneSignal;

/**
 * Created by smartsense on 22/09/16.
 */

public class MadhaparGamApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).init();
        SharedPreferenceUtil.init(this);
    }
}
