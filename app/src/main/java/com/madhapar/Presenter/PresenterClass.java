package com.madhapar.Presenter;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import com.madhapar.Model.LoginModel;
import com.madhapar.Model.LoginModelInt;
import com.madhapar.Model.MainModelClass;
import com.madhapar.Model.MainModelInt;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.LoginActivity;
import com.madhapar.View.LoginInt;
import com.madhapar.View.ViewInt;

/**
 * Created by smartsense on 21/09/16.
 */

public class PresenterClass implements PresneterInt, MainModelInt.Textvalidator,LoginModel.OnLoginFinishedListener {

    private ViewInt viewInt;
    private MainModelClass modelClass;
    private MainModelInt.Textvalidator textValidator;
    private LoginInt loginInt;
    private LoginModelInt loginModelInt;
    Handler handler;
    LoginModel loginModel;


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

    public PresenterClass(){}

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
    }

    @Override
    public void onSuccess() {
        loginInt.loginValidateResult(UtilClass.Success);
    }
}
