package com.madhapar.Presenter;

import com.madhapar.Model.EventCalenderModel;
import com.madhapar.Model.EventCalenderModelInt;
import com.madhapar.Model.NetworkModel;
import com.madhapar.Model.NetworkModelInt;
import com.madhapar.View.EventListInt;
import com.madhapar.View.NetworkViewInt;

import org.json.JSONArray;

/**
 * Created by smartsense on 06/10/16.
 */

public class RequestPresenter implements RequestPresenterInt, EventCalenderModelInt.EventListCallback, NetworkModelInt.NetworkListResponseCallback {
    EventListInt eventListInt;
    NetworkViewInt networkViewInt;


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


    //------------------Network List----------//
    @Override
    public void getNetworkList(NetworkViewInt networkViewInt) {
        new NetworkModel().getNetworkList(this);
        this.networkViewInt = networkViewInt;

    }


    @Override
    public void onSuccessResponse(JSONArray networkList) {
        networkViewInt.onSuccessNetworkList(networkList);
    }

    @Override
    public void onFailResponse(String message) {
        networkViewInt.onFailNetworkListResponse(message);
    }

    @Override
    public void onFailRequest() {
        networkViewInt.onFailNetworkListRequest();
    }
}
