package com.madhapar.View;

import org.json.JSONObject;

/**
 * Created by Ronak on 9/27/2016.
 */
public interface ChangePasswordViewInt {
    void changePasswordValidateResult(int check);

    void changePasswordSuccessfull(JSONObject response);

    void changePasswordFail(String message);

    void changePasswordRequestError();
}
