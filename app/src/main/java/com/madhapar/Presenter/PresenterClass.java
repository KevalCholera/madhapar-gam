package com.madhapar.Presenter;

import android.app.Activity;

import com.madhapar.Model.MainModelClass;
import com.madhapar.Model.MainModelInt;
import com.madhapar.View.ViewInt;

/**
 * Created by smartsense on 21/09/16.
 */

public class PresenterClass implements PresneterInt, MainModelInt.Textvalidator {

    private ViewInt viewInt;
    private MainModelClass modelClass;
    private MainModelInt.Textvalidator textValidator;


    @Override
    public void checkLogin(String username, String password, ViewInt mViewInt, Activity activity) {
        this.viewInt = mViewInt;
        modelClass = new MainModelClass();
        textValidator = this;
        modelClass.validateUser(username, password, textValidator, activity);

    }

    @Override
    public void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish) {
        if (modelClass == null) {
            modelClass = new MainModelClass();
        }
        modelClass.changeActivity(curruntActivity, nextActivity, finish);
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
}
