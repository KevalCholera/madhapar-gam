package com.madhapar.Presenter;

import android.app.Activity;

import com.madhapar.View.ViewInt;


/**
 * Created by smartsense on 21/09/16.
 */

public interface PresneterInt {
    public void checkLogin(String text, String password, ViewInt mViewInt, Activity activity);

    public void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish);

    public void validateCredentials(String contactNumber, String password);

}
