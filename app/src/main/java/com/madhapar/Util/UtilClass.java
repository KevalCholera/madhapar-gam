package com.madhapar.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by smartsense on 21/09/16.
 */

public class UtilClass {
    public static final int PasswordError=1;
    public static final int PasswordLengthError=2;
    public static final int UserIdError=3;
    public static final int UserIdLengthError=4;
    public static final int Success=5;
    public static final int RequiredFieldError=6;
    public static final int FirstNameError = 7;
    public static final int LastNameError = 8;
    public static final int FamilyMemberError = 9;
    public static final int MatchPassword = 10;
    public static final int ConfirmPassword = 11;

    public static void displyMessage(String msg, Context context, int toastLenght) {
        Toast.makeText(context, msg, toastLenght).show();
        Log.e(context.getPackageName(), "Log:  " + msg + "");
    }

    public static void showProgress(ProgressBar progress) {
        progress.setVisibility(View.VISIBLE);

    }
    public static void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish) {
        curruntActivity.startActivity(new Intent(curruntActivity, nextActivity));
        if (finish) {
            curruntActivity.finish();
        }

    }

    public static void hideProgress(ProgressBar progress) {
        progress.setVisibility(View.GONE);
    }

}
