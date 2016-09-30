package com.madhapar.Presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.madhapar.View.AlertDialogClass;
import com.madhapar.View.ChangePasswordViewInt;
import com.madhapar.View.FeedbackActivityInt;
import com.madhapar.View.ForgetPasswordViewInt;
import com.madhapar.View.ViewInt;

import java.util.List;


/**
 * Created by smartsense on 21/09/16.
 */

public interface PresneterInt {
    public void checkLogin(String text, String password, ViewInt mViewInt, Activity activity);

    public void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish);

    public void validateCredentials(String contactNumber, String password);

    public List<Integer> initMainDrawer();

    public void feedbackValidateCredentials(String name, String mobileNumber, String subject, String feedback, FeedbackActivityInt feedbackActivityInt);


    public void signUpValidationCredentials(String firstName, String lastName, String contactNumber, String password, String familyMember);

    public void forgetPasswordCredentials(String contactNumber, ForgetPasswordViewInt forgetPasswordViewInt1);

    public void changeFragment(int containerId, int position, AppCompatActivity activity);
    public void alert(Context context);

    public void changePasswordCredential(String newPassword, String confirmNewPassword, ChangePasswordViewInt changePasswordViewInt1);
}
