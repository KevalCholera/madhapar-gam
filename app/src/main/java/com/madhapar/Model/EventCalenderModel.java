package com.madhapar.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronak on 9/30/2016.
 */
public class EventCalenderModel implements EventCalenderModelInt{

    @Override
    public JSONArray getEventList() {
        Log.e("presenter","eventList");

        JSONArray eventList = new JSONArray();
        try {
            JSONObject obj1 = new JSONObject();
            obj1.put("eventTitle","Navratri");
            obj1.put("eventFromDate ","2016-09-30T21:50:00Z");
            obj1.put("eventAddress","KICC Main hall");
            obj1.put("going ","12");
            obj1.put("interested ","12");
            obj1.put("cantGo ","12");
            JSONObject obj2 = new JSONObject();
            obj2.put("eventTitle","Diwali");
            obj2.put("eventFromDate ","2016-09-30T21:50:00Z");
            obj2.put("eventAddress","Titanium City Center");
            obj2.put("going ","12");
            obj2.put("interested ","12");
            obj2.put("cantGo ","12");
           eventList.put(obj1);
            eventList.put(obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eventList;
    }

}
