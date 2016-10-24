package com.madhapar.View;

import org.json.JSONArray;

/**
 * Created by smartsense on 24/10/16.
 */

public interface CatagoryCallback {
    void onSuccessCatagoryList(JSONArray catagories);

    void onFailCatagoryListRequest();

    void onFailCatagoryResponse(String message);
}
