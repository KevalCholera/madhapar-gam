package com.madhapar.Model;

import java.util.Map;

/**
 * Created by smartsense on 20/10/16.
 */

public interface ProfileUpdateModelInt {
    void updateProfileUserName(Map<String, String> params, String userId, ProfileUpdateListener mProfileUpdateListener);

    interface ProfileUpdateListener {
        void onSuccessUpdateProfile(String message);

        void onFailProfileUpdateRequest(String message);

        void onFailProfileUpdateRequest();
    }
}
