package com.madhapar.Presenter;

import com.madhapar.Model.ProfileUpdateModel;
import com.madhapar.View.ProfileUpdateCallback;

import java.util.Map;

/**
 * Created by smartsense on 20/10/16.
 */

public class ProfileUpdatePresenter implements ProfileUpdatePresenterInt, ProfileUpdateModel.ProfileUpdateListener {
    ProfileUpdateCallback mProfileUpdateCallback;
    ProfileUpdateModel mProfileModel;

    @Override
    public void updateUserFirstName(Map<String, String> params, String userId, ProfileUpdateCallback profileUpdateCallback) {
        this.mProfileUpdateCallback = profileUpdateCallback;
        if (mProfileModel == null) {
            mProfileModel = new ProfileUpdateModel();
        }
        mProfileModel.updateProfileUserName(params,userId, this);

    }

    @Override
    public void onSuccessUpdateProfile(String message) {

    }

    @Override
    public void onFailProfileUpdateRequest(String message) {

    }

    @Override
    public void onFailProfileUpdateRequest() {

    }
}
