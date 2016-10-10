package com.madhapar.PushUtil;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.madhapar.Application.MadhaparGamApp;
import com.madhapar.Util.Constants;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by smartsense on 08/10/16.
 */

public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        try {
            if (intent.getExtras() != null) {
                JSONObject pushData = new JSONObject(extras.get("custom").toString());
                int pushType = 0;
                if (pushData != null) {
                    JSONObject aObj = pushData.optJSONObject("a");
                    if (aObj != null) {
                        pushType = aObj.optInt("pushType");
                        Log.e("pushType", "push" + pushType);
                    }
                    String i = pushData.optString("i");
                }
                String alert = extras.get("alert").toString();
                Intent broadcastIntent;
                if (pushType == Constants.PushConstant.NewsFeedPush) {
                    broadcastIntent = new Intent(Constants.PushConstant.PushActionNews);
                } else {
                    broadcastIntent = new Intent(Constants.PushConstant.PushActionEvent);
                }
                MadhaparGamApp.getAppInstance().sendBroadcast(broadcastIntent);
                GcmBroadcastReceiver.completeWakefulIntent(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
