package com.madhapar.Model;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Fragment.EventFragment;
import com.madhapar.View.Fragment.HomeFragment;
import com.madhapar.View.Fragment.MoreFragment;
import com.madhapar.View.Fragment.NetworkFragment;
import com.madhapar.View.Fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by smartsense on 21/09/16.
 */

public class MainModelClass implements MainModelInt {
    @Override
    public void validateUser(final String username, final String password, final Textvalidator textvalidator, final Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(username)) {
                    if (TextUtils.isEmpty(password)) {
                        textvalidator.fail();
                        UtilClass.displyMessage("Fail", activity, Toast.LENGTH_SHORT);
                    } else {
                        textvalidator.usernameError();
                        UtilClass.displyMessage("Username Error", activity, Toast.LENGTH_SHORT);
                    }
                } else if (TextUtils.isEmpty(password)) {
                    textvalidator.passwordError();
                    UtilClass.displyMessage("Password Error", activity, Toast.LENGTH_SHORT);
                } else {
                    textvalidator.success();
                    UtilClass.displyMessage("Successfull", activity, Toast.LENGTH_SHORT);
                }
            }
        }, 2000);

    }

    @Override
    public void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish) {
        curruntActivity.startActivity(new Intent(curruntActivity, nextActivity));
        if (finish) {
            curruntActivity.finish();
        }

    }

    @Override
    public List<Integer> getMainDrawerData() {
        List<Integer> drawerIconList = new ArrayList<>();
        drawerIconList.add(0, R.drawable.ic_home);
        drawerIconList.add(1, R.drawable.ic_event);
        drawerIconList.add(2, R.drawable.ic_user);
        drawerIconList.add(3, R.drawable.ic_network);
        drawerIconList.add(4, R.drawable.ic_more);
        return drawerIconList;


    }

    @Override
    public Fragment getFragment(int fragmentNo) {
        Fragment fragment = null;
        if (fragmentNo == 0) {
            fragment = new HomeFragment();
        } else if (fragmentNo == 1) {
            fragment = new EventFragment();
        } else if (fragmentNo == 2) {
            fragment = new UserFragment();
        } else if (fragmentNo == 3) {
            fragment = new MoreFragment();
        } else if (fragmentNo == 4) {
            fragment = new NetworkFragment();
        }
        return fragment;
    }


}
