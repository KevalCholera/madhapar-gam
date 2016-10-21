package com.madhapar.Presenter;

import com.madhapar.View.ProfileUpdateCallback;

import java.util.Map;

/**
 * Created by smartsense on 20/10/16.
 */

public interface ProfileUpdatePresenterInt {
    void updateUserFirstName(Map<String, String> params, String userId, ProfileUpdateCallback profileUpdateCallback);
}
