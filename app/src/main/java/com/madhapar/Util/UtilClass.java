package com.madhapar.Util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by smartsense on 21/09/16.
 */

public class UtilClass {

    public static void displyMessage(String msg, Context context, int toastLenght) {
        Toast.makeText(context, msg, toastLenght).show();
        Log.e(context.getPackageName(), "Log:  " + msg);
    }

    public static void showProgress(ProgressBar progress) {
        progress.setVisibility(View.VISIBLE);

    }

    public static void hideProgress(ProgressBar progress) {
        progress.setVisibility(View.GONE);
    }


}
