package com.madhapar.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.madhapar.Util.UtilClass;


/**
 * Created by smartsense on 21/09/16.
 */

public class MainModelClass implements MainModelInt {
    @Override
    public void validateUser(final String username, final String password, final Textvalidator textvalidator, final Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(username)) {
                    if (TextUtils.isEmpty(password)) {
                        textvalidator.fail();
                        UtilClass.displyMessage("Fail", activity, Toast.LENGTH_SHORT);
                    } else {
                        textvalidator.usernameError();
                        UtilClass.displyMessage("Username Error", activity, Toast.LENGTH_SHORT);
                    }
                } else if (TextUtils.isEmpty(password)) {
                    textvalidator.passwordError();
                    UtilClass.displyMessage("Password Error", activity, Toast.LENGTH_SHORT);
                } else {
                    textvalidator.success();
                    UtilClass.displyMessage("Successfull", activity, Toast.LENGTH_SHORT);
                }
            }
        }, 2000);

    }

    @Override
    public void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish) {
        curruntActivity.startActivity(new Intent(curruntActivity, nextActivity));
        if (finish) {
            curruntActivity.finish();
        }

    }


}
