package com.madhapar.View;

import org.json.JSONObject;

/**
 * Created by smartsense on 25/10/16.
 */

public interface UploadInterface {

    void onSuccessUploadImage(JSONObject response);

    void onFailUpload(String message);
}
