package com.madhapar.View;

import org.json.JSONArray;

/**
 * Created by smartsense on 22/10/16.
 */

public interface ProfileUpdateCallback {
    void onSuccessUpdateUserData(String name);

    void onFailUpdateUesrDate(String message);

    void onFailUpdateRequest();

    void onSuccessLocationList(JSONArray jsonArray);


}
