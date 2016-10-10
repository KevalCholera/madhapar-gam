package com.madhapar.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronak on 10/10/2016.
 */
public class EventInfoModel implements EventInfoModelInt {
    @Override
    public JSONObject getEventIndo() {
        JSONObject obj1 = new JSONObject();
        try {
            obj1.put("EventInfoName","Diwali");
            obj1.put("EventInfodateTime","16 july at 6:00");
            obj1.put("EventInfoAddress","ABC hall near Titanium City center");
            obj1.put("OrganizedBy","Smart Sense Company");
            obj1.put("EventDescription","This Event is for all Gujarati. This event is for Diwali Function.Diwali is festival that everyone smiling faces,make rangoli, make sweets.");
            obj1.put("EventIngoGoing","12");
            obj1.put("EventIngoInterested","33");
            obj1.put("EventIngocantgo","9");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj1;
    }
}
