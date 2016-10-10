package com.madhapar.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronak on 10/10/2016.
 */
public class CommentModel implements CommentModelInt {
    @Override
    public JSONArray GetComments() {
        JSONArray commentList = new JSONArray();
        try {
            JSONObject obj1 = new JSONObject();
            obj1.put("CommentPersonName","Tony Clark3");
            obj1.put("Comment","#Design#Awesome#Fun#Memory#");
            obj1.put("CommentTime","5 hours ago");
            JSONObject obj2 = new JSONObject();
            obj2.put("CommentPersonName","Tony Clark3");
            obj2.put("Comment","#Design#Awesome#Fun#Memory#");
            obj2.put("CommentTime","5 hours ago");
            JSONObject obj3 = new JSONObject();
            obj3.put("CommentPersonName","Tony Clark3");
            obj3.put("Comment","#Design#Awesome#Fun#Memory#");
            obj3.put("CommentTime","5 hours ago");
            commentList.put(obj1);
            commentList.put(obj2);
            commentList.put(obj3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentList;
    }
}
