package com.madhapar.View;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by smartsense on 12/10/16.
 */

public interface EventDetailCallback {
    void onSuccessEventDetail(JSONObject eventObj);

    void onFailEventDetailRequest();

    void onFailEventDetailResponse(String message);

    interface EventStatusCreateCallback {
        void onSuccessStautsCreate(JSONObject updateObj);

        void onFailStautsCreateRequest();

        void onFailStautsCreateRequest(String message);
    }

    interface EventListCallback {
        void onSuccessEventList(JSONArray eventArray);

        void onFailEventListRequest();

        void onFailEventListResponse(String message);
    }

    interface EventStatusListCallback {
        void onSuccessEventStatusList(JSONArray statusArray);

        void onFailEventStatusListRequest();

        void onFailEventStatusResponse(String message);
    }

    interface EventStatusUpdateCallback {
        void onSuccessStautsUpdate(JSONObject updateObj);

        void onFailStautsUpdateRequest();

        void onFailStautsUpdateRequest(String message);
    }


}
