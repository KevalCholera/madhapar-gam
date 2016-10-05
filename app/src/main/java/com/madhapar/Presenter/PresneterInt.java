package com.madhapar.Presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.madhapar.View.ChangePasswordViewInt;
import com.madhapar.View.FeedbackActivityInt;
import com.madhapar.View.ForgetPasswordViewInt;
import com.madhapar.View.LoginActivity;
import com.madhapar.View.ViewInt;

import org.json.JSONArray;

import java.util.List;


/**
 * Created by smartsense on 21/09/16.
 */

public interface PresneterInt {

    void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish);

    void validateCredentials(String contactNumber, String password,LoginActivity loginModel);

    List<Integer> initMainDrawer();

    void feedbackValidateCredentials(String name, String mobileNumber, String subject, String feedback, FeedbackActivityInt feedbackActivityInt);

    JSONArray getEventList();

    JSONArray getProfile();

    void signUpValidationCredentials(String firstName, String lastName, String contactNumber, String password, String familyMember);

    void forgetPasswordCredentials(String contactNumber, ForgetPasswordViewInt forgetPasswordViewInt1);

    void changeFragment(int containerId, int position, AppCompatActivity activity);

    void alert(Context context);

    void changePasswordCredential(String newPassword, String confirmNewPassword, ChangePasswordViewInt changePasswordViewInt1);
}
