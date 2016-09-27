package com.madhapar.Model;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by smartsense on 21/09/16.
 */

public interface MainModelInt {
    interface Textvalidator {
        public void passwordError();

        public void usernameError();

        public void success();

        public void fail();
    }

    public void validateUser(String username, String password, Textvalidator textvalidator, Activity activity);

    public void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish);

    public List<Integer> getMainDrawerData();

    public Fragment getFragment(int fragmentNo);
}
