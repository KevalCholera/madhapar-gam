package com.madhapar.View;

import org.json.JSONObject;

/**
 * Created by Ronak on 9/26/2016.
 */
public interface ForgetPasswordViewInt {
    public void forgetPasswordValidateResult(int check);

    void forgotPasswordSuccess(JSONObject otpResponse);

    void forgotPasswrodFail(String message);

    void forgotPasswordRequestError();
}
