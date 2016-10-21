package com.madhapar.Model;

import org.json.JSONArray;

/**
 * Created by Ronak on 10/19/2016.
 */
public interface FundRaisingModelInt {
    interface FundRaisingListResponseCallback {
        void onSuccessProjectList(JSONArray response);

        void onFailProjectListResponse(String message);

        void onFailProjectListRequest();
    }
    void getFundRaisingList(FundRaisingListResponseCallback callback);
}

