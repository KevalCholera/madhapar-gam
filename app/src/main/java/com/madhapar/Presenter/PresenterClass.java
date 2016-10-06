package com.madhapar.Presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.madhapar.Model.ChangePasswordModel;
import com.madhapar.Model.EventCalenderModel;
import com.madhapar.Model.FeedbackModel;
import com.madhapar.Model.ForgetPasswordModel;
import com.madhapar.Model.LoginModel;
import com.madhapar.Model.LoginModelInt;
import com.madhapar.Model.MainModelClass;
import com.madhapar.Model.MyNetworkModel;
import com.madhapar.Model.SignUpModel;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.AlertDialogClass;
import com.madhapar.View.ChangePasswordViewInt;
import com.madhapar.View.FeedbackActivityInt;
import com.madhapar.View.ForgetPasswordViewInt;
import com.madhapar.View.LoginActivity;
import com.madhapar.View.LoginInt;
import com.madhapar.View.SignUpActivity;
import com.madhapar.View.SignUpViewInt;
import com.madhapar.View.ViewInt;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by smartsense on 21/09/16.
 */

public class PresenterClass implements PresneterInt, LoginModelInt.OnLoginFinishedListener, SignUpModel.OnSignUpFinishedListener, ForgetPasswordModel.OnLoginFinishedListener, ChangePasswordModel.OnLoginFinishedListener, FeedbackModel.OnLoginFinishedListener {

    private ViewInt viewInt;
    private MainModelClass modelClass;
    private LoginInt loginInt;
    private LoginModelInt loginModelInt;
    //private SignUpModelInt signUpModelInt;
    private SignUpModel signUpModel;
    private EventCalenderModel eventCalenderModel;
    private MyNetworkModel myNetworkModel;
    private SignUpViewInt signupInt;
    private ForgetPasswordViewInt forgetPasswordViewInt;
    private ForgetPasswordModel forgetPassModel;
    private ChangePasswordViewInt changePasswordViewInt;
    private FeedbackActivityInt feedbackActivityint;
    private FeedbackModel feedbackModel;
    private ChangePasswordModel changePasswordModel;
    LoginModel loginModel;

    public PresenterClass(SignUpActivity signUpModel) {
        this.signupInt = signUpModel;
    }

    public PresenterClass() {

    }

    @Override
    public void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish) {
        if (modelClass == null) {
            modelClass = new MainModelClass();
        }
        modelClass.changeActivity(curruntActivity, nextActivity, finish);
    }

    @Override
    public void validateCredentials(String contactNumber, String password, LoginActivity loginModel) {
        if (loginInt != null) {
        }
        this.loginInt = loginModel;
        loginModelInt = new LoginModel();
        loginModelInt.login(contactNumber, password, this);
    }

    @Override
    public void signUpValidationCredentials(String firstName, String lastName, String contactNumber, String password, String familyMember) {

        signUpModel = new SignUpModel();
        signUpModel.signup(firstName, lastName, contactNumber, password, familyMember, this);
    }

    @Override
    public void forgetPasswordCredentials(String contactNumber, ForgetPasswordViewInt forgetPasswordViewInt1) {
        forgetPasswordViewInt = forgetPasswordViewInt1;
        forgetPassModel = new ForgetPasswordModel();
        forgetPassModel.login(contactNumber, this);
    }

    @Override
    public void changeFragment(int containerId, int position, AppCompatActivity activity) {
        if (modelClass == null) {
            modelClass = new MainModelClass();
        }
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.
                replace(containerId, modelClass.getFragment(position));

        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void alert(Context context) {
        new AlertDialogClass(context);
    }

    @Override
    public void changePasswordCredential(String newPassword, String confirmNewPassword, ChangePasswordViewInt changePasswordViewInt1) {
        changePasswordViewInt = changePasswordViewInt1;
        changePasswordModel = new ChangePasswordModel();
        changePasswordModel.chnagePassword(newPassword, confirmNewPassword, this);
    }


    @Override
    public List<Integer> initMainDrawer() {
        if (modelClass == null) {
            modelClass = new MainModelClass();
        }
        return modelClass.getMainDrawerData();
    }

    @Override
    public void feedbackValidateCredentials(String name, String mobileNumber, String subject, String feedback, FeedbackActivityInt feedbackActivityInt) {
        feedbackActivityint = feedbackActivityInt;
        feedbackModel = new FeedbackModel();
        feedbackModel.feedback(name, mobileNumber, subject, feedback, this);
    }

    @Override
    public void onLogincontactNumberError() {
        loginInt.loginValidateResult(UtilClass.UserIdError);
    }

    @Override
    public void onLoginPasswordError() {
        loginInt.loginValidateResult(UtilClass.PasswordError);
    }

    @Override
    public void onLoginContactLenghtError() {
        loginInt.loginValidateResult(UtilClass.UserIdLengthError);
    }

    @Override
    public void onLoginPasswordLengthError() {
        loginInt.loginValidateResult(UtilClass.PasswordLengthError);
        Log.e("Password", "Match");
    }

    @Override
    public void onLoginSuccess() {
        loginInt.loginValidateResult(UtilClass.Success);
    }

    @Override
    public void onLoginFailError(JSONObject object) {
    }

    @Override
    public void onLoginRequestError() {

    }

    @Override
    public void onSignUpSuccess() {
        signupInt.signUpValidateResult(UtilClass.Success);
    }

    @Override
    public void onSignUpFailError(JSONObject error) {

    }

    @Override
    public void onSignUpFirstNameError() {
        signupInt.signUpValidateResult(UtilClass.FirstNameError);
    }

    @Override
    public void onSignUplastNameError() {
        signupInt.signUpValidateResult(UtilClass.LastNameError);
    }

    @Override
    public void onSignUpFamilyMemberError() {
        signupInt.signUpValidateResult(UtilClass.FamilyMemberError);
    }

    @Override
    public void onSignupPasswordLengthError() {
        signupInt.signUpValidateResult(UtilClass.PasswordLengthError);
        Log.e("password", "length");
    }

    @Override
    public void onsignUpPasswordError() {
        signupInt.signUpValidateResult(UtilClass.PasswordError);
    }

    @Override
    public void onSignUpContactLenghtError() {
        signupInt.signUpValidateResult(UtilClass.UserIdLengthError);
    }

    @Override
    public void onSignupContactNumberError() {
        signupInt.signUpValidateResult(UtilClass.UserIdError);
    }

    @Override
    public void onSignUpRequiredFieldError() {
        signupInt.signUpValidateResult(UtilClass.RequiredFieldError);
    }

    @Override
    public void onChangePasswordSuccess() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.Success);
    }

    @Override
    public void onLoginRequiredFieldError() {
        loginInt.loginValidateResult(UtilClass.RequiredFieldError);
    }

    @Override
    public void onNewPasswordError() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.PasswordError);
    }

    @Override
    public void onConfirmPasswordError() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.ConfirmPassword);
    }

    @Override
    public void onNewPasswordLengthError() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.PasswordLengthError);
    }

    @Override
    public void onPasswordMatchError() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.MatchPassword);
    }

    @Override
    public void onChangePasswordRequiredFieldError() {
        changePasswordViewInt.changePasswordValidateResult(UtilClass.RequiredFieldError);
    }

    @Override
    public void onForgetContactLenghtError() {
        forgetPasswordViewInt.forgetPasswordValidateResult(UtilClass.UserIdLengthError);
    }

    @Override
    public void onForgetContactNumberError() {
        forgetPasswordViewInt.forgetPasswordValidateResult(UtilClass.UserIdError);
    }

    @Override
    public void onForgetSuccess() {
        forgetPasswordViewInt.forgetPasswordValidateResult(UtilClass.Success);
    }

    @Override
    public void onFeddbackSubjectError() {
        feedbackActivityint.feedbackValidateResult(UtilClass.FeedbackSubject);
    }

    @Override
    public void onFeddbackDescriptionError() {
        feedbackActivityint.feedbackValidateResult(UtilClass.Feeedback);
    }

    @Override
    public void onFeddbackRequiredFieldError() {
        feedbackActivityint.feedbackValidateResult(UtilClass.RequiredFieldError);
    }

    @Override
    public void onFeedbackSuccess() {
        feedbackActivityint.feedbackValidateResult(UtilClass.Success);
    }


    @Override
    public JSONArray getProfile() {
        myNetworkModel = new MyNetworkModel();
        return myNetworkModel.getProfile();
    }
}
