package com.madhapar.Model;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

/**
 * Created by Ronak on 9/26/2016.
 */
public interface SignUpModelInt {

    void signup(String firstName, String lastName, String contactNumber, String password, String familyMember, SignUpModelInt.OnSignUpFinishedListener listener, AppCompatActivity activity);

    interface OnSignUpFinishedListener {

        void onSignUpSuccess();

        void onSignUpRequestError();

        void onSignUpFailError(String signUpError);

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
