package com.madhapar.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronak on 10/5/2016.
 */
public class InterestedListModel implements InterestedListModelInt {
    @Override
    public JSONArray getInterestedList() {
        JSONArray interestList = new JSONArray();
        try {
            JSONObject obj1 = new JSONObject();
            obj1.put("InterestedPersonName","Tony Clark");
            JSONObject obj2 = new JSONObject();
            obj2.put("InterestedPersonName","Allan Bred");
            JSONObject obj3 = new JSONObject();
            obj3.put("InterestedPersonName","Ben Peter");
            JSONObject obj4 = new JSONObject();
            obj4.put("InterestedPersonName","Suart Mini");
            interestList.put(obj1);
            interestList.put(obj2);
            interestList.put(obj3);
            interestList.put(obj4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return interestList;
    }
}
