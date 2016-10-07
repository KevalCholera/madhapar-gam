package com.madhapar.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ronak on 10/7/2016.
 */
public class ProfileDataModel implements ProfileDataModelInt {
    @Override
    public JSONArray getProfileData() {
        JSONArray profileData = new JSONArray();
        try {
            JSONObject objname = new JSONObject();
            objname.put("name","Mark Stain");
            objname.put("mobileNo","+ 98887787777");
            objname.put("location","Ahmedabad");
            objname.put("dob","10 july 1990");
            objname.put("membershipNo","2221");
            objname.put("bloodGroup","B+");
            objname.put("email","bcdbcbd@gmail.com");
            objname.put("facebook","cbdjbcejdncjdennkjnfacebook.com");
            profileData.put(objname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profileData;
    }
}
