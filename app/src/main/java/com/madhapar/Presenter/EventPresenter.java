package com.madhapar.Presenter;

import android.support.v7.app.AppCompatActivity;

import com.madhapar.Model.EventModel;
import com.madhapar.Model.EventModelInt;
import com.madhapar.View.EventDetailCallback;
import com.madhapar.View.EventStatusAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by smartsense on 12/10/16.
 */

public class EventPresenter implements EventPresenterInt, EventModelInt.onEventDetailListener, EventModelInt.EventStatusCreateListener, EventModelInt.EventListListener, EventModelInt.EventStautsListListener, EventModelInt.EventStatusUpdateListener {
    private EventDetailCallback mEventDetailCallback;
    private EventDetailCallback.EventListCallback mEventListCallback;
    private EventModel eventModel;
    private EventDetailCallback.EventStatusListCallback mEventStatusListCallback;
    private EventDetailCallback
            .EventStatusUpdateCallback mEventStatusUpdateCallback;
    private EventDetailCallback.EventStatusCreateCallback EventStatusCreateCallback;

    @Override
    public void getEventDetail(String eventId, EventDetailCallback mEventDetailCallback) {
        this.mEventDetailCallback = mEventDetailCallback;
        if (eventModel == null) {
            eventModel = new EventModel();
        }
        eventModel.getEventDetail(eventId, this);
    }

    @Override
    public void getEventList(EventDetailCallback.EventListCallback mEventListCallback) {
        this.mEventListCallback = mEventListCallback;
        if (eventModel == null) {
            eventModel = new EventModel();
        }
        eventModel.getEventList(this);
    }


    @Override
    public void createEventStatus(String eventId, String eventStatus, String count, EventDetailCallback.EventStatusCreateCallback mEventDetailCallback) {
        this.EventStatusCreateCallback = mEventDetailCallback;
        if (eventModel == null) {
            eventModel = new EventModel();
        }
        eventModel.createEventStatus(eventId, eventStatus, count, this);
    }


    @Override
    public void onSuccessEventDetail(JSONObject successObj) {
        mEventDetailCallback.onSuccessEventDetail(successObj);
    }

    @Override
    public void onFailRequest() {
        mEventDetailCallback.onFailEventDetailRequest();
    }

    @Override
    public void onFailResponse(String message) {
        mEventDetailCallback.onFailEventDetailResponse(message);
    }

    @Override
    public void onSuccessStatusCreate(JSONObject updateObj) {
        EventStatusCreateCallback.onSuccessStautsCreate(updateObj);
    }

    @Override
    public void onFailStatusCreateRequest() {
        EventStatusCreateCallback.onFailStautsCreateRequest();
    }

    @Override
    public void onFailStatusCreateResponse(String message) {
        EventStatusCreateCallback.onFailStautsCreateRequest(message);
    }

    @Override
    public void onSuccessEventList(JSONArray eventArray) {
        mEventListCallback.onSuccessEventList(eventArray);
    }

    @Override
    public void onFailEventListRequest() {
        mEventListCallback.onFailEventListRequest();
    }

    @Override
    public void onFailEventEventListResponse(String message) {
        mEventListCallback.onFailEventListResponse(message);
    }

    @Override
    public void getEventStatusList(String eventId, String eventStatus, EventDetailCallback.EventStatusListCallback mEventStatusListCallback) {
        this.mEventStatusListCallback = mEventStatusListCallback;
        if (eventModel == null) {
            eventModel = new EventModel();
        }
        eventModel.getEventStatusList(eventId, eventStatus, this);
    }


    @Override
    public void onSuccessEventStatusList(JSONArray statusArray) {
        mEventStatusListCallback.onSuccessEventStatusList(statusArray);
    }

    @Override
    public void onFailStatusListRequest() {
        mEventStatusListCallback.onFailEventStatusListRequest();
    }

    @Override
    public void onFailStatusListResponse(String message) {
        mEventStatusListCallback.onFailEventStatusResponse(message);
    }


    @Override
    public void updateEventStatus(String eventStatusId, String eventStatus, String count, EventDetailCallback.EventStatusUpdateCallback mEventStatusUpdateCallback) {
        this.mEventStatusUpdateCallback = mEventStatusUpdateCallback;
        if (eventModel == null) {
            eventModel = new EventModel();
        }
        eventModel.updateEventStatus(eventStatusId, eventStatus, count, this);
    }

    @Override
    public void openEventAlert(AppCompatActivity activity, JSONObject eventObj, String statusType, EventDetailCallback.EventStatusCreateCallback createCallback, EventDetailCallback.EventStatusUpdateCallback updateCallback) {
        EventStatusAlertDialog alertDialog = new EventStatusAlertDialog(activity,eventObj,statusType,createCallback,updateCallback);
    }

    @Override
    public void onSuccessEventUpdate(JSONObject updateObj) {
        mEventStatusUpdateCallback.onSuccessStautsUpdate(updateObj);
    }

    @Override
    public void onFailEventUpdateRequest() {
        mEventStatusUpdateCallback.onFailStautsUpdateRequest();
    }

    @Override
    public void onFailEventUpdateResponse(String message) {
        mEventStatusUpdateCallback.onFailStautsUpdateRequest(message);
    }
}

