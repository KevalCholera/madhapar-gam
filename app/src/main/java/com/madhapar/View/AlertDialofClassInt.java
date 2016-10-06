package com.madhapar.View;

import org.json.JSONObject;

/**
 * Created by Ronak on 9/28/2016.
 */
public interface AlertDialofClassInt {
    void otpVerificationSuccessfull(JSONObject verifyObj);

    void otpVerificationFail(String message);

    void otpVerifyRequestError();

}
