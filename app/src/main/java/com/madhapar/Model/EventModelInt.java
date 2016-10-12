package com.madhapar.Model;

import com.madhapar.Presenter.EventPresenter;
import com.madhapar.View.EventDetailCallback;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by smartsense on 12/10/16.
 */

public interface EventModelInt {


    interface onEventDetailListener {
        void onSuccessEventDetail(JSONObject successObj);

        void onFailRequest();

        void onFailResponse(String message);
    }

    interface EventStatusCreateListener {
        void onSuccessStatusCreate(JSONObject updateObj);

        void onFailStatusCreateRequest();

        void onFailStatusCreateResponse(String message);
    }

    interface EventListListener {
        void onSuccessEventList(JSONArray eventArray);

        void onFailEventListRequest();

        void onFailEventEventListResponse(String message);
    }

    interface EventStautsListListener {
        void onSuccessEventStatusList(JSONArray statusArray);

        void onFailStatusListRequest();

        void onFailStatusListResponse(String message);
    }

    interface EventStatusUpdateListener {
        void onSuccessEventUpdate(JSONObject updateObj);

        void onFailEventUpdateRequest();

        void onFailEventUpdateResponse(String message);
    }

    void getEventStatusList(String eventStatusId, String eventStatus, EventStautsListListener mEventStatusListListener);

    void getEventDetail(String eventId, onEventDetailListener mDetailListener);

    void getEventList(EventListListener mEventListener);

    void createEventStatus(String eventId, String eventStatus, String count, EventStatusCreateListener mEventStatusListener);

    void updateEventStatus(String eventId, String eventStatus, String count, EventStatusUpdateListener mEventUpdateListener);
}
