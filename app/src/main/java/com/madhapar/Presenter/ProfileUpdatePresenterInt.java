package com.madhapar.Presenter;

import android.support.v7.app.AppCompatActivity;

import com.madhapar.View.ProfileUpdateCallback;

import java.util.Map;

/**
 * Created by smartsense on 20/10/16.
 */

public interface ProfileUpdatePresenterInt {
    void updateUserDetail(Map<String, String> params, String userId, ProfileUpdateCallback profileUpdateCallback);

    void getLocationList(ProfileUpdateCallback mProfileUpdateCallback);

    void addNewLocationAlert(AppCompatActivity activity);

    void createNewLocation(String locationName, ProfileUpdateCallback mProfileUpdateCallback);
}
