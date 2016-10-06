package com.madhapar.Model;

import org.json.JSONObject;

/**
 * Created by Ronak on 9/26/2016.
 */
public interface SignUpModelInt {

    void signup(String firstName, String lastName, String contactNumber, String password, String familyMember, SignUpModelInt.OnSignUpFinishedListener listener);

    interface OnSignUpFinishedListener {

        void onSignUpSuccess();

        void onSignUpFailError(JSONObject error);

        void onSignUpFirstNameError();

        void onSignUplastNameError();

        void onSignUpFamilyMemberError();

        void onSignupPasswordLengthError();

        void onsignUpPasswordError();

        void onSignUpContactLenghtError();

        void onSignupContactNumberError();

        void onSignUpRequiredFieldError();
    }
}
