package com.madhapar.Model;

import org.json.JSONArray;

/**
 * Created by Ronak on 10/19/2016.
 */
public interface FundRaisingModelInt {
    public interface FundRaisingListResponseCallback {

    }
    public void getFundRaisingList(FundRaisingListResponseCallback callback);
}
