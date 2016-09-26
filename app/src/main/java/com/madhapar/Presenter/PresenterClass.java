package com.madhapar.Presenter;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.madhapar.Model.ForgetPasswordModel;
import com.madhapar.Model.LoginModel;
import com.madhapar.Model.LoginModelInt;
import com.madhapar.Model.MainModelClass;
import com.madhapar.Model.MainModelInt;
import com.madhapar.Model.SignUpModel;
import com.madhapar.Model.SignUpModelInt;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.ForgetPassword;
import com.madhapar.View.ForgetPasswordViewInt;
import com.madhapar.View.LoginActivity;
import com.madhapar.View.LoginInt;
import com.madhapar.View.SignUpActivity;
import com.madhapar.View.SignUpViewInt;
import com.madhapar.View.ViewInt;

/**
 * Created by smartsense on 21/09/16.
 */

public class PresenterClass implements PresneterInt, MainModelInt.Textvalidator,LoginModel.OnLoginFinishedListener,SignUpModel.OnLoginFinishedListener,ForgetPasswordModel.OnLoginFinishedListener {

    private ViewInt viewInt;
    private MainModelClass modelClass;
    private MainModelInt.Textvalidator textValidator;
    private LoginInt loginInt;
    private LoginModelInt loginModelInt;
    //private SignUpModelInt signUpModelInt;
    private SignUpModel signUpModel;
    private SignUpViewInt signupInt;
    private ForgetPasswordViewInt forgetPasswordViewInt;
    private ForgetPasswordModel forgetPassModel;
    Handler handler;
    LoginModel loginModel;

    public PresenterClass() {

    }

    public PresenterClass(ForgetPassword forgetPassword) {
        this.forgetPasswordViewInt = (ForgetPasswordViewInt) forgetPasswordViewInt;
    }


    @Override
    public void checkLogin(String username, String password, ViewInt mViewInt, Activity activity) {
        this.viewInt = mViewInt;
        modelClass = new MainModelClass();
        textValidator = this;
        modelClass.validateUser(username, password, textValidator, activity);
    }

    public PresenterClass(LoginActivity loginModel){

        this.loginInt = loginModel;
    }

    public PresenterClass(SignUpActivity signUpModel)
    {
        this.signupInt = signUpModel;
    }

    @Override
    public void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish) {
        if (modelClass == null) {
            modelClass = new MainModelClass();
        }
        modelClass.changeActivity(curruntActivity, nextActivity, finish);
    }

    @Override
    public void validateCredentials(String contactNumber, String password) {
        if(loginInt != null){
        }
        loginModelInt = new LoginModel();
        loginModelInt.login(contactNumber,password,this);
    }

    @Override
    public void signUpValidationCredentials(String firstName, String lastName, String contactNumber, String password, String familyMember) {

        signUpModel = new SignUpModel();
        signUpModel.signup(firstName,lastName,contactNumber,password,familyMember,this);
    }

    @Override
    public void forgetPasswordCredentials(String contactNumber,ForgetPasswordViewInt forgetPasswordViewInt1) {
        forgetPasswordViewInt = forgetPasswordViewInt1;
        forgetPassModel = new ForgetPasswordModel();
        forgetPassModel.login(contactNumber,this);
    }

    @Override
    public void passwordError() {
        viewInt.validationResult(0);
    }

    @Override
    public void usernameError() {
        viewInt.validationResult(1);
    }

    @Override
    public void success() {
        viewInt.validationResult(2);
    }

    @Override
    public void fail() {
        viewInt.validationResult(3);
    }

    @Override
    public void oncontactNumberError() {
        loginInt.loginValidateResult(UtilClass.UserIdError);
    }

    @Override
    public void onPasswordError() {
        loginInt.loginValidateResult(UtilClass.PasswordError);
    }

    @Override
    public void onContactLenghtError() {
        loginInt.loginValidateResult(UtilClass.UserIdLengthError);
    }

    @Override
    public void onPasswordLengthError() {
        loginInt.loginValidateResult(UtilClass.PasswordLengthError);
        Log.e("Password","Match");
    }
    @Override
    public void onSuccess() {
        loginInt.loginValidateResult(UtilClass.Success);
    }

    @Override
    public void onSignUpSuccess() {
        signupInt.signUpValidateResult(UtilClass.Success);
    }

    @Override
    public void onFirstNameError() {
        signupInt.signUpValidateResult(UtilClass.FirstNameError);
    }

    @Override
    public void onlastNameError() {
        signupInt.signUpValidateResult(UtilClass.LastNameError);
    }

    @Override
    public void onFamilyMemberError() {
        signupInt.signUpValidateResult(UtilClass.FamilyMemberError);
    }

    @Override
    public void onSignupPasswordLengthError() {
        signupInt.signUpValidateResult(UtilClass.PasswordLengthError);
        Log.e("password","length");
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
    public void onRequiredFieldError() {
        loginInt.loginValidateResult(UtilClass.RequiredFieldError);
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
}
