package com.madhapar.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronak on 10/5/2016.
 */
public class GoinListModel implements GoingListModelInt {
    @Override
    public JSONArray getGoingList() {
        JSONArray goinList = new JSONArray();
        try {
            JSONObject obj1 = new JSONObject();
            obj1.put("GoingPersonName","Tony Clark");
            JSONObject obj2 = new JSONObject();
            obj2.put("GoingPersonName","Allan Bred");
            JSONObject obj3 = new JSONObject();
            obj3.put("GoingPersonName","Ben Peter");
            JSONObject obj4 = new JSONObject();
            obj4.put("GoingPersonName","Suart Mini");
            goinList.put(obj1);
            goinList.put(obj2);
            goinList.put(obj3);
            goinList.put(obj4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return goinList;
    }
}
