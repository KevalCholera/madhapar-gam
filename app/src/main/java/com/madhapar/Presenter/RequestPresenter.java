package com.madhapar.Presenter;

import com.madhapar.Model.EventCalenderModel;
import com.madhapar.Model.EventCalenderModelInt;
import com.madhapar.View.EventListInt;

import org.json.JSONArray;

/**
 * Created by smartsense on 06/10/16.
 */

public class RequestPresenter implements RequestPresenterInt, EventCalenderModelInt.EventListCallback {
    EventListInt eventListInt;

    @Override
    public void onSuccessEventList(JSONArray eventArray) {
        eventListInt.onSuccessEventList(eventArray);

    }

    @Override
    public void onFailEventList(String errorMessage) {
        eventListInt.onFailEventList(errorMessage);
    }


    @Override
    public void getEventList(EventListInt callback) {
        this.eventListInt = callback;
        new EventCalenderModel().getEventList(this);
    }
}
