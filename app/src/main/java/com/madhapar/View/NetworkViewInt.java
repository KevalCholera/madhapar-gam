package com.madhapar.View;

import org.json.JSONArray;

/**
 * Created by smartsense on 07/10/16.
 */

public interface NetworkViewInt {
    void onSuccessNetworkList(JSONArray userList);

    void onFailNetworkListResponse(String message);

    void onFailNetworkListRequest();


}
