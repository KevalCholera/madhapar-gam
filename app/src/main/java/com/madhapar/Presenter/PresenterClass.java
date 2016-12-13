package com.madhapar.Presenter;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.madhapar.Model.ChangePasswordModel;
import com.madhapar.Model.CommentModel;
import com.madhapar.Model.CommentModelInt;
import com.madhapar.Model.FeedbackModel;
import com.madhapar.Model.ForgetPasswordModel;
import com.madhapar.Model.ForgetPasswordModelInt;
import com.madhapar.Model.FundRaisingModel;
import com.madhapar.Model.FundRaisingModelInt;
import com.madhapar.Model.LoginModel;
import com.madhapar.Model.LoginModelInt;
import com.madhapar.Model.MainModelClass;
import com.madhapar.Model.SignUpModel;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.ChangePasswordViewInt;
import com.madhapar.View.CommentViewInt;
import com.madhapar.View.FeedbackActivityInt;
import com.madhapar.View.ForgetPasswordViewInt;
import com.madhapar.View.FundRaisigListCallback;
import com.madhapar.View.LoginInt;
import com.madhapar.View.OtpAlertDialog;
import com.madhapar.View.OtpAlertDialogInt;
import com.madhapar.View.SignUpActivity;
import com.madhapar.View.SignUpViewInt;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by smartsense on 21/09/16.
 */

public class PresenterClass implements PresneterInt, CommentModelInt.onCommentRequestFinishListener, LoginModelInt.onLoginFinishListener, SignUpModel.OnSignUpFinishedListener, ForgetPasswordModel.onSendOtpListener, ChangePasswordModel.onChangePasswordRequestFinishListener, FeedbackModel.OnFeedbackPostListener, ForgetPasswordModelInt.onVerifyOtpListener, FundRaisingModelInt.FundRaisingListResponseCallback {

    private MainModelClass modelClass;
    private LoginInt loginInt;
    private SignUpModel signUpModel;
    private SignUpViewInt signupInt;
    private ForgetPasswordViewInt forgetPasswordViewInt;
    private ForgetPasswordModel forgetPassModel;
    private ChangePasswordViewInt changePasswordViewInt;
    private FeedbackActivityInt feedbackActivityint;
    private FeedbackModel feedbackModel;
    private ChangePasswordModel changePasswordModel;
    private OtpAlertDialogInt alertIntl;
    private FundRaisigListCallback fundRaisingCallback;
    private CommentModelInt commentModelInt;
    private CommentModel commentModel;
    private CommentViewInt commentViewInt1;

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
    public void signUpValidationCredentials(String firstName, String lastName, String contactNumber, String password, String middleName, AppCompatActivity activity) {

        signUpModel = new SignUpModel();

        signUpModel.signup(firstName, lastName, contactNumber, password, middleName, this, activity);
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


        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            Log.i("@@@@@@@@@@@@@@@@@@@@", "Found fragment: " + fragmentManager.getBackStackEntryAt(entry).getId());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void alert(AppCompatActivity context, JSONObject otpResponse, String contactNumber, int type) {
        new OtpAlertDialog(context, otpResponse, contactNumber, type);
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
    public void commentCredential(String comment, CommentViewInt commentViewInt) {
        commentViewInt1 = commentViewInt;
        commentModel = new CommentModel();
        commentModel.comment(comment, this);


    }

    @Override
    public void feedbackValidateCredentials(String subject, String feedback, FeedbackActivityInt feedbackActivityInt) {
        this.feedbackActivityint = feedbackActivityInt;
        feedbackModel = new FeedbackModel();
        feedbackModel.feedback(subject, feedback, this);
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
    public void onMiddleNameError() {
        signupInt.signUpValidateResult(UtilClass.MiddleNameError);
    }


    @Override
    public void onSignupPasswordLengthError() {
        signupInt.signUpValidateResult(UtilClass.PasswordLengthError);

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
    public void onFirstNameLengthError() {
        signupInt.signUpValidateResult(UtilClass.FirstNameLenght);
    }

    @Override
    public void onLasttNameLengthError() {
        signupInt.signUpValidateResult(UtilClass.LastNameLength);
    }

    @Override
    public void onFatherstNameLengthError() {
        signupInt.signUpValidateResult(UtilClass.MiddleNameLength);

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


    //__________OTP VERIFICATION_____///


    @Override
    public void forgetPasswordCredentials(String contactNumber, ForgetPasswordViewInt forgetPasswordViewInt1, int type) {
        forgetPasswordViewInt = forgetPasswordViewInt1;
        if (forgetPassModel == null)
            forgetPassModel = new ForgetPasswordModel();
        forgetPassModel.sendOtp(contactNumber, this, type);
    }

    @Override
    public void verifyForgotPasswordOtp(String contactNumber, String otpValue, OtpAlertDialogInt alertDialogInt) {
        this.alertIntl = alertDialogInt;
        if (forgetPassModel == null)
            forgetPassModel = new ForgetPasswordModel();
        forgetPassModel.verifyOtp(contactNumber, this, otpValue);

    }

    @Override
    public void verifyUserOtp(String contactNumber, String otp, OtpAlertDialogInt alertDialofClassInt) {
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


    //_______________FundRaising____________//
    @Override
    public void getFundRaisingList(FundRaisigListCallback fundCallback) {
        FundRaisingModel fundRaisingModel = new FundRaisingModel();
        fundRaisingModel.getFundRaisingList(this);
        this.fundRaisingCallback = fundCallback;
    }

    @Override
    public void onSuccessProjectList(JSONArray response) {
        fundRaisingCallback.onSuccessFundRaisingList(response);
    }

    @Override
    public void onFailProjectListResponse(String message) {
        fundRaisingCallback.onFailFundRaisingList(message);
    }

    @Override
    public void onFailProjectListRequest() {
        fundRaisingCallback.onFailRequestFundRaisingList();
    }


    //____________Feedback ___________//
    @Override
    public void onFeedbackFieldRequiredError() {
        feedbackActivityint.feedbackValidateResult(UtilClass.FeedbackFieldError);
    }

    @Override
    public void onFeedbackSubjectRequiredError() {
        feedbackActivityint.feedbackValidateResult(UtilClass.FeedbackSubjectRequiredError);
    }

    @Override
    public void onFeedbackSubjectValidError() {
        feedbackActivityint.feedbackValidateResult(UtilClass.FeedbackSubjectValidateError);
    }

    @Override
    public void onFeedbackRequiredError() {
        feedbackActivityint.feedbackValidateResult(UtilClass.FeedbackdRequiredError);
    }

    @Override
    public void onFeedbackValidError() {
        feedbackActivityint.feedbackValidateResult(UtilClass.FeedbackValidateError);
    }

    @Override
    public void onSuccessPostFeedback(String messsage) {
        feedbackActivityint.onFeedbackSuccess(messsage);
    }

    @Override
    public void onFailFeedbackResponse(String message) {
        feedbackActivityint.onFeedbackResponseError(message);
    }

    @Override
    public void onFailFeedbackRequest() {
        feedbackActivityint.onFeedbackRequestError();
    }


    @Override
    public void onCommentBlankError() {
        commentViewInt1.onCommentresult(UtilClass.CommentBlankError);
    }

    @Override
    public void onCommentLengthError() {
        commentViewInt1.onCommentresult(UtilClass.CommentLenghtError);
    }
}
