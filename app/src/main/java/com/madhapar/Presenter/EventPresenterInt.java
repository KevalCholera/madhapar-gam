package com.madhapar.Presenter;

import android.support.v7.app.AppCompatActivity;

import com.madhapar.View.EventDetailCallback;

import org.json.JSONObject;

/**
 * Created by smartsense on 12/10/16.
 */

public interface EventPresenterInt {
    void getEventDetail(String eventId, EventDetailCallback mEventDetailCallback);

    void getEventList(EventDetailCallback.EventListCallback callback);

    void getEventStatusList(String eventId, String eventStatus, EventDetailCallback.EventStatusListCallback mEventStatusListCallback);

    void createEventStatus(String eventId, String eventStatus, String count, EventDetailCallback.EventStatusCreateCallback mEventStatusCreateCallback);

    void updateEventStatus(String eventId, String eventStatus, String count, EventDetailCallback.EventStatusUpdateCallback mEventStatusUpdateCallback);

    void openEventAlert(AppCompatActivity activity, JSONObject eventObj, String statusType, EventDetailCallback.EventStatusCreateCallback createCallback, EventDetailCallback.EventStatusUpdateCallback updateCallback);
}
