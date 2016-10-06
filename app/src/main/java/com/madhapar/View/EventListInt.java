package com.madhapar.View;

import org.json.JSONArray;

/**
 * Created by smartsense on 06/10/16.
 */

public interface EventListInt {
    void onSuccessEventList(JSONArray eventArray);

    void onFailEventList(String errorMessage);
}
