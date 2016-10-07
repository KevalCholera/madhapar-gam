package com.madhapar.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronak on 10/7/2016.
 */
public class NewsFeedModel implements NewsFeedModelInt {
    @Override
    public JSONArray getNewsData() {
        JSONArray newsData = new JSONArray();
        try {
            JSONObject objname = new JSONObject();
            objname.put("newsTitle","News Title");
            objname.put("dateTime","12 july at 02:59 PM");
            objname.put("newsCity","Ahmedabad");
            objname.put("newDescription","this news is about ahmedabad it is highly alert in ahmdabad. All people are very scared.");
            objname.put("likeCount","221");
            objname.put("commentCount","37");
            JSONObject objq = new JSONObject();
            objq.put("newsTitle","News Title");
            objq.put("dateTime","12 july at 02:59 PM");
            objq.put("newsCity","Ahmedabad");
            objq.put("newDescription","this news is about ahmedabad it is highly alert in ahmdabad. All people are very scared.");
            objq.put("likeCount","221");
            objq.put("commentCount","37");
            JSONObject obj3 = new JSONObject();
            obj3.put("newsTitle","News Title");
            obj3.put("dateTime","12 july at 02:59 PM");
            obj3.put("newsCity","Ahmedabad");
            obj3.put("newDescription","this news is about ahmedabad it is highly alert in ahmdabad. All people are very scared.");
            obj3.put("likeCount","221");
            obj3.put("commentCount","37");
            newsData.put(objname);
            newsData.put(objq);
            newsData.put(obj3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsData;
    }
}
