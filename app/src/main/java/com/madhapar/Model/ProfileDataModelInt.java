package com.madhapar.Model;

import android.content.SharedPreferences;

import com.madhapar.Presenter.RequestPresenter;

import org.json.JSONArray;

/**
 * Created by Ronak on 10/7/2016.
 */
public interface ProfileDataModelInt {
    public interface ProfileDataResponseCallback {
        void onSuccessResponse(JSONArray networkList);

        void onFailResponse(String message);

        void onFailRequest();
    }

    EventPhotosModel getProfileDataList(RequestPresenter callback);
}
