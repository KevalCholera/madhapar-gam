package com.madhapar.View;

import org.json.JSONObject;

/**
 * Created by Ronak on 9/26/2016.
 */
public interface SignUpViewInt {
    public void signUpValidateResult(int check);

     void signUpResponseError(JSONObject error);
}
