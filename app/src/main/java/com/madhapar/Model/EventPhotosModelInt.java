package com.madhapar.Model;

import com.madhapar.Presenter.RequestPresenter;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Ronak on 10/11/2016.
 */
public interface EventPhotosModelInt {
    public interface NetworkListResponseCallback {
        void onSuccessResponse(JSONArray networkList);

        void onFailResponse(String message);

        void onFailRequest();
    }

    EventPhotosModel getEventPhotoList(RequestPresenter callback);
}
