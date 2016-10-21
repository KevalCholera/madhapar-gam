package com.madhapar.View;

/**
 * Created by smartsense on 20/10/16.
 */

public interface ProfileUpdateCallback {
    void onSuccessUpdateUserData(String name);

    void onFailUpdateUesrDate(String message);

    void onFailUpdateRequest();
}
