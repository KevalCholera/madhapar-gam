package com.madhapar.Presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.madhapar.View.AlertDialofClassInt;
import com.madhapar.View.ChangePasswordViewInt;
import com.madhapar.View.FeedbackActivityInt;
import com.madhapar.View.ForgetPasswordViewInt;
import com.madhapar.View.LoginActivity;
import com.madhapar.View.LoginInt;
import com.madhapar.View.ViewInt;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by smartsense on 21/09/16.
 */

public interface PresneterInt {

    void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish);

    void validateCredentials(String contactNumber, String password, LoginInt loginInt,AppCompatActivity activity);

    List<Integer> initMainDrawer();

    void feedbackValidateCredentials(String name, String mobileNumber, String subject, String feedback, FeedbackActivityInt feedbackActivityInt);


    JSONArray getProfile();

    void signUpValidationCredentials(String firstName, String lastName, String contactNumber, String password, String familyMember);

    void forgetPasswordCredentials(String contactNumber, ForgetPasswordViewInt forgetPasswordViewInt);

    void verifyForgotPasswordOtp(String contactNumber, String otpValue, AlertDialofClassInt alerClasstInt);

    void changeFragment(int containerId, int position, AppCompatActivity activity);

    void alert(AppCompatActivity context, JSONObject otpResponse, String contactNumber);

    void changePasswordCredential(String newPassword, String confirmNewPassword, String otpToken, String contactNumber, ChangePasswordViewInt changePasswordViewInt1);
}
