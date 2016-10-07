package com.madhapar.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartsense.newproject.R;

/**
 * Created by smartsense on 21/09/16.
 */

public class UtilClass {
    public static final int PasswordError = 1;
    public static final int PasswordLengthError = 2;
    public static final int UserIdError = 3;
    public static final int UserIdLengthError = 4;
    public static final int Success = 5;
    public static final int SignUpRequestError = 100;
    public static final int LoginRequestError = 101;
    public static final int RequiredFieldError = 6;
    public static final int FirstNameError = 7;
    public static final int LastNameError = 8;
    public static final int FamilyMemberError = 9;
    public static final int MatchPassword = 10;
    public static final int ConfirmPassword = 11;
    public static final int FeedbackSubject = 12;
    public static final int Feeedback = 13;
    public static final int RetryTimeOut = 20000;
    private static ProgressDialog pDialog;

    public static void displyMessage(String msg, Context context, int toastLenght) {

        context.setTheme(R.style.NewTStyle);
        Toast.makeText(context, msg, toastLenght).show();
        context.setTheme(R.style.AppTheme);
        Log.e(context.getPackageName(), "Log:  " + msg + "");
    }

    public static void showProgress(Context activity, String message) {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog.cancel();
        }
        try {
            activity.setTheme(R.style.NewTStyle);
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(message);
            pDialog.setCancelable(false);
            pDialog.show();
            activity.setTheme(R.style.AppTheme);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish) {
        curruntActivity.startActivity(new Intent(curruntActivity, nextActivity));
        if (finish) {
            curruntActivity.finish();
        }
    }


    public static void hideProgress() {
        if (pDialog != null)
            try {
                pDialog.cancel();
                pDialog.dismiss();
                Log.e("progress", "dismiss");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static String getSignupUrl() {
        Uri signupUri = Uri.parse(Constants.RequestConstants.SignupUrl).buildUpon().build();
        return signupUri.toString();
    }

    public static String getLoginUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.LoginUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getEventListUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.EventListUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getUserVerifyUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.UserVerifyUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getUserListUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.UserListUrl).buildUpon().build();
        return builder.toString();
    }


    public static String getOtpUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.OtpUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getVerifyOtpUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.VerifyOtpUrl).buildUpon().build();
        return builder.toString();
    }

    public static String getChangePasswordUrl() {
        Uri builder = Uri.parse(Constants.RequestConstants.ChangePasswordUrl).buildUpon().build();
        return builder.toString();
    }


    public static boolean isInternetAvailabel(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    static public void closeKeyboard(Activity a) {
        try {
            if (isKeyboardVisible(a)) {
                InputMethodManager imm = (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isKeyboardVisible(Activity activity) {
        Rect r = new Rect();
        View contentView = activity.findViewById(android.R.id.content);
        contentView.getWindowVisibleDisplayFrame(r);
        int screenHeight = contentView.getRootView().getHeight();

        int keypadHeight = screenHeight - r.bottom;

        return
                (keypadHeight > screenHeight * 0.15);
    }

}
