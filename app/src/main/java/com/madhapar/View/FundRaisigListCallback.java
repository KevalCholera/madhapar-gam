package com.madhapar.View;

import org.json.JSONArray;

/**
 * Created by smartsense on 20/10/16.
 */

public interface FundRaisigListCallback {
    void onSuccessFundRaisingList(JSONArray response);

    void onFailFundRaisingList(String message);

    void onFailRequestFundRaisingList();
}
