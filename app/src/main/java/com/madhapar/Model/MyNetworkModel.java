package com.madhapar.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronak on 10/5/2016.
 */
public class MyNetworkModel implements MyNetworkModelInt {
    @Override
    public JSONArray getProfile() {
        JSONArray profileList = new JSONArray();
        try {
            JSONObject objname = new JSONObject();
            objname.put("personName","Tony Clark");
            objname.put("qualification","Software Engineer");
            objname.put("personCity","Ahmedabad");
            JSONObject objq = new JSONObject();
            objq.put("personName","Mark Larry");
            objq.put("qualification","Software Engineer");
            objq.put("personCity","Banglore");
            JSONObject obj3 = new JSONObject();
            obj3.put("personName","Van Stain");
            obj3.put("qualification","Software Engineer");
            obj3.put("personCity","Ahmedabad");
            profileList.put(objname);
            profileList.put(objq);
            profileList.put(obj3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profileList;
    }
}
