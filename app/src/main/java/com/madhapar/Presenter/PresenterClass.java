package com.madhapar.Presenter;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.madhapar.Model.ChangePasswordModel;
import com.madhapar.Model.EventCalenderModel;
import com.madhapar.Model.FeedbackModel;
import com.madhapar.Model.ForgetPasswordModel;
import com.madhapar.Model.ForgetPasswordModelInt;
import com.madhapar.Model.LoginModel;
import com.madhapar.Model.LoginModelInt;
import com.madhapar.Model.MainModelClass;
import com.madhapar.Model.NetworkModel;
import com.madhapar.Model.SignUpModel;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.AlertDialofClassInt;
import com.madhapar.View.AlertDialogClass;
import com.madhapar.View.ChangePasswordViewInt;
import com.madhapar.View.FeedbackActivityInt;
import com.madhapar.View.ForgetPasswordViewInt;
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

public class PresenterClass implements PresneterInt, LoginModelInt.onLoginFinishListener, SignUpModel.OnSignUpFinishedListener, ForgetPasswordModel.onSendOtpListener, ChangePasswordModel.onChangePasswordRequestFinishListener, FeedbackModel.OnLoginFinishedListener, ForgetPasswordModelInt.onVerifyOtpListener {

    private ViewInt viewInt;
    private MainModelClass modelClass;
    private LoginInt loginInt;
    private LoginModelInt loginModelInt;
    //private SignUpModelInt signUpModelInt;
    private SignUpModel signUpModel;
    private EventCalenderModel eventCalenderModel;
    private NetworkModel networkModelClass;
    private SignUpViewInt signupInt;
    private ForgetPasswordViewInt forgetPasswordViewInt;
    private ForgetPasswordModel forgetPassModel;
    private ChangePasswordViewInt changePasswordViewInt;
    private FeedbackActivityInt feedbackActivityint;
    private FeedbackModel feedbackModel;
    private ChangePasswordModel changePasswordModel;
    private AlertDialofClassInt alertIntl;
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
    public void validateCredentials(String contactNumber, String password, LoginInt loginInt, AppCompatActivity activity) {
        this.loginInt = loginInt;
        LoginModel loginModel = new LoginModel();
        loginModel.login(contactNumber, password, this, activity);
    }

    @Override
    public void signUpValidationCredentials(String firstName, String lastName, String contactNumber, String password, String familyMember, AppCompatActivity activity) {

        signUpModel = new SignUpModel();
        signUpModel.signup(firstName, lastName, contactNumber, password, familyMember, this, activity);
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
    public void alert(AppCompatActivity context, JSONObject otpResponse, String contactNumber, int type) {
        new AlertDialogClass(context, otpResponse, contactNumber, type);
    }

    @Override
    public void changePasswordCredential(String newPassword, String confirmNewPassword, String otpToken, String contactNumber, ChangePasswordViewInt changePasswordViewInt1) {
        changePasswordViewInt = changePasswordViewInt1;
        changePasswordModel = new ChangePasswordModel();
        changePasswordModel.changePassword(newPassword, confirmNewPassword, otpToken, contactNumber, this);
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


    //_________Login________//
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
    }

    @Override
    public void onLoginSuccess() {
        loginInt.loginValidateResult(UtilClass.Success);
    }

    @Override
    public void onLoginFailError(String failMessage) {
        loginInt.onFailLogin(failMessage);
    }

    @Override
    public void onRequestError() {
        loginInt.onRequestFail();
    }


    //________Signup_______//
    @Override
    public void onSignUpSuccess() {
        signupInt.signUpValidateResult(UtilClass.Success);
    }

    @Override
    public void onSignUpRequestError() {
        signupInt.signUpRequestError();
    }

    @Override
    public void onSignUpFailError(String errorMessage) {
        signupInt.signUpResponseError(errorMessage);

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


    //________Change Password_________//

    @Override
    public void onChangePasswordSuccess(JSONObject changePasswordObj) {
        changePasswordViewInt.changePasswordSuccessfull(changePasswordObj);
    }

    @Override
    public void onFailToChangePassword(String message) {
        changePasswordViewInt.changePasswordFail(message);
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
    public void onChangePasswordRequestError() {
        changePasswordViewInt.changePasswordRequestError();
    }


    //_________Forgot Password________//
    @Override
    public void onForgetContactLenghtError() {
        forgetPasswordViewInt.forgetPasswordValidateResult(UtilClass.UserIdLengthError);
    }

    @Override
    public void onForgetContactNumberError() {
        forgetPasswordViewInt.forgetPasswordValidateResult(UtilClass.UserIdError);
    }

    @Override
    public void onForgetSuccess(JSONObject optResponse) {
        forgetPasswordViewInt.forgotPasswordSuccess(optResponse);
    }

    @Override
    public void onForgotFail(String message) {
        forgetPasswordViewInt.forgotPasswrodFail(message);
    }

    @Override
    public void onOtpRequestError() {
        forgetPasswordViewInt.forgotPasswordRequestError();
    }


    //____________Feedback ___________//
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


    //__________OTP VERIFICATION_____///


    @Override
    public void forgetPasswordCredentials(String contactNumber, ForgetPasswordViewInt forgetPasswordViewInt1, int type) {
        forgetPasswordViewInt = forgetPasswordViewInt1;
        if (forgetPassModel == null)
            forgetPassModel = new ForgetPasswordModel();
        forgetPassModel.sendOtp(contactNumber, this, type);
    }

    @Override
    public void verifyForgotPasswordOtp(String contactNumber, String otpValue, AlertDialofClassInt alertDialogInt) {
        this.alertIntl = alertDialogInt;
        if (forgetPassModel == null)
            forgetPassModel = new ForgetPasswordModel();
        forgetPassModel.verifyOtp(contactNumber, this, otpValue);

    }

    @Override
    public void verifyUserOtp(String contactNumber, String otp, AlertDialofClassInt alertDialofClassInt) {
        this.alertIntl = alertDialofClassInt;
        if (forgetPassModel == null) {
            forgetPassModel = new ForgetPasswordModel();
        }
        forgetPassModel.verifyUserOtp(contactNumber, this, otp);

    }


    @Override
    public void onOtpVerify(JSONObject verifyResponse) {
        alertIntl.otpVerificationSuccessfull(verifyResponse);
    }

    @Override
    public void onOtpVerificationFail(String message) {
        alertIntl.otpVerificationFail(message);
    }

    @Override
    public void onVerifyOtpRequestError() {
        alertIntl.otpVerifyRequestError();
    }
}
