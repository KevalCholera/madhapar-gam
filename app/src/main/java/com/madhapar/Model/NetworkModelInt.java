package com.madhapar.Model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Ronak on 10/5/2016.
 */
public interface NetworkModelInt {
    public interface NetworkListResponseCallback {
        void onSuccessResponse(JSONArray networkList);

        void onFailResponse(String message);

        void onFailRequest();
    }

    void getNetworkList(NetworkListResponseCallback callback);
}
