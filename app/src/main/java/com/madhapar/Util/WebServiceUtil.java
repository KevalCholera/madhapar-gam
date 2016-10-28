package com.madhapar.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.madhapar.View.UploadInterface;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smartsense on 25/10/16.
 */

public class WebServiceUtil extends AsyncTask<String, Integer, String> {

    HashMap<String, String> formValues;
    HashMap<String, String> fileValues;
    private UploadInterface uploadInterface;

    Boolean uploadFile;
    String reqUrl;
    String result;
    Context context;


    public WebServiceUtil(Context context, String reqUrl,
                          HashMap<String, String> formValues, Boolean uploadFile,
                          HashMap<String, String> fileValues, UploadInterface uploadInterface) {

        this.context = context;
        this.reqUrl = reqUrl;
        this.formValues = formValues;
        this.uploadInterface = uploadInterface;

        this.uploadFile = uploadFile;
        if (uploadFile) {

            this.fileValues = fileValues;
        }
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

    }

    //

    @Override
    protected String doInBackground(String... URLs) {

        List<String> response = null;
        try {
            MultipartUtility mu = new MultipartUtility(context, reqUrl, "UTF-8");

            for (Map.Entry<String, String> entry : formValues.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                mu.addFormField(key, value);
            }

            if (uploadFile) {

                for (Map.Entry<String, String> entry : fileValues.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    File file = new File(value);
                    mu.addFilePart(key, file);

                }

            }

            response = mu.finish();


        } catch (IOException e) {

            e.printStackTrace();
        }
        if (response != null) {
            return response.toString();
        } else {
            return null;
        }

    }

    protected void onPostExecute(String result) {
        if (result != null) {
            try {
                JSONArray picArray = new JSONArray(result);
                JSONObject picObj = picArray.optJSONObject(0);
                if (picObj.optInt("status") == Constants.ResponseCode.SuccessCode) {
                    uploadInterface.onSuccessUploadImage(picObj);
                } else {
                    uploadInterface.onFailUpload(picObj.optString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.e("resonse", "result" + result);

    }

}
