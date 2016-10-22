package com.madhapar.Presenter;

import android.support.v7.app.AppCompatActivity;

import com.madhapar.Model.ProfileUpdateModel;
import com.madhapar.View.LocationAlertDialog;
import com.madhapar.View.ProfileUpdateCallback;

import org.json.JSONArray;

import java.util.Map;

/**
 * Created by smartsense on 20/10/16.
 */

public class ProfileUpdatePresenter implements ProfileUpdatePresenterInt, ProfileUpdateModel.ProfileUpdateListener {
    private ProfileUpdateCallback mProfileUpdateCallback;
    private ProfileUpdateModel mProfileModel;

    @Override
    public void updateUserDetail(Map<String, String> params, String userId, ProfileUpdateCallback profileUpdateCallback) {
        this.mProfileUpdateCallback = profileUpdateCallback;
        if (mProfileModel == null) {
            mProfileModel = new ProfileUpdateModel();
        }
        mProfileModel.updateProfileUserName(params, userId, this);

    }


    @Override
    public void onSuccessUpdateProfile(String message) {
        mProfileUpdateCallback.onSuccessUpdateUserData("");

    }

    @Override
    public void onFailProfileUpdateRequest(String message) {
        mProfileUpdateCallback.onFailUpdateUesrDate(message);
    }

    @Override
    public void onFailProfileUpdateRequest() {
        mProfileUpdateCallback.onFailUpdateRequest();
    }

    @Override
    public void onSuccessLocationList(JSONArray locatioList) {
        mProfileUpdateCallback.onSuccessLocationList(locatioList);
    }

    @Override
    public void getLocationList(ProfileUpdateCallback mProfileUpdateCallback) {
        if (mProfileModel == null) {
            mProfileModel = new ProfileUpdateModel();
        }
        this.mProfileUpdateCallback = mProfileUpdateCallback;
        mProfileModel.getLocationList(this);

    }

    @Override
    public void addNewLocationAlert(AppCompatActivity activity) {
        LocationAlertDialog locationAlertDialog = new LocationAlertDialog(activity);
        locationAlertDialog.openLocationDialog(activity);
    }

    @Override
    public void createNewLocation(String locationName, ProfileUpdateCallback mProfileUpdateCallback) {
        this.mProfileUpdateCallback = mProfileUpdateCallback;
        if (mProfileModel == null) {
            mProfileModel = new ProfileUpdateModel();
        }
        mProfileModel.createNewLocation(locationName, this);

    }


}
