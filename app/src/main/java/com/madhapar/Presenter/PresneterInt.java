package com.madhapar.Presenter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.madhapar.View.FundRaisigListCallback;
import com.madhapar.View.OtpAlertDialogInt;
import com.madhapar.View.ChangePasswordViewInt;
import com.madhapar.View.FeedbackActivityInt;
import com.madhapar.View.ForgetPasswordViewInt;
import com.madhapar.View.LoginInt;

import org.json.JSONObject;

import java.util.List;


/**
 * Created by smartsense on 21/09/16.
 */

public interface PresneterInt {

    void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish);

    void validateCredentials(String contactNumber, String password, LoginInt loginInt, AppCompatActivity activity);

    List<Integer> initMainDrawer();

    void feedbackValidateCredentials(String subject, String feedback, FeedbackActivityInt feedbackActivityInt);

    void signUpValidationCredentials(String firstName, String lastName, String contactNumber, String password, String familyMember, AppCompatActivity activity);

    void forgetPasswordCredentials(String contactNumber, ForgetPasswordViewInt forgetPasswordViewInt, int type);

    void verifyForgotPasswordOtp(String contactNumber, String otpValue, OtpAlertDialogInt alerClasstInt);

    void verifyUserOtp(String contactNumber, String otp, OtpAlertDialogInt alertDialofClassInt);

    void changeFragment(int containerId, int position, AppCompatActivity activity);

    void alert(AppCompatActivity context, JSONObject otpResponse, String contactNumber, int type);

    void changePasswordCredential(String newPassword, String confirmNewPassword, String otpToken, String contactNumber, ChangePasswordViewInt changePasswordViewInt1);

    void getFundRaisingList(FundRaisigListCallback fundCallback);
}
