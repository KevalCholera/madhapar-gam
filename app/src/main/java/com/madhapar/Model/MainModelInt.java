package com.madhapar.Model;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by smartsense on 21/09/16.
 */

public interface MainModelInt {


    void changeActivity(Activity curruntActivity, Class nextActivity, Boolean finish);

    List<Integer> getMainDrawerData();

    Fragment getFragment(int fragmentNo);
}
