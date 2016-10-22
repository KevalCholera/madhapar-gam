package com.madhapar.Model;

import org.json.JSONArray;

import java.util.Map;

/**
 * Created by smartsense on 20/10/16.
 */

public interface ProfileUpdateModelInt {
    void updateProfileUserName(Map<String, String> params, String userId, ProfileUpdateListener mProfileUpdateListener);

    void getLocationList(ProfileUpdateListener mProfileUpdateListener);

    void createNewLocation(String locationName, ProfileUpdateListener mProfileUpdateListener);

    interface ProfileUpdateListener {
        void onSuccessUpdateProfile(String message);

        void onFailProfileUpdateRequest(String message);

        void onFailProfileUpdateRequest();

        void onSuccessLocationList(JSONArray locatioList);
    }
}
