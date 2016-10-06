package com.madhapar.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronak on 10/5/2016.
 */
public class NotGoinListModel implements NotGoingModelInt {
    @Override
    public JSONArray getNotGoingList() {
        JSONArray notgoingList = new JSONArray();
        try {
            JSONObject obj1 = new JSONObject();
            obj1.put("NotGoingPersonName","Tony Clark2");
            JSONObject obj2 = new JSONObject();
            obj2.put("NotGoingPersonName","Allan Bred2");
            JSONObject obj3 = new JSONObject();
            obj3.put("NotGoingPersonName","Ben Peter2");
            JSONObject obj4 = new JSONObject();
            obj4.put("NotGoingPersonName","Suart Mini2");
            notgoingList.put(obj1);
            notgoingList.put(obj2);
            notgoingList.put(obj3);
            notgoingList.put(obj4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notgoingList;
    }
}
