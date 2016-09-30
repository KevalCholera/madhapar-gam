package com.madhapar.Model;

/**
 * Created by Ronak on 9/26/2016.
 */
public interface SignUpModelInt {

     void signup(String firstName,String lastName,String contactNumber, String password,String familyMember , SignUpModelInt.OnLoginFinishedListener listener);

     interface OnLoginFinishedListener {

        void onSignUpSuccess();

        void onFirstNameError();

        void onlastNameError();

        void onFamilyMemberError();

        void onSignupPasswordLengthError();

        void onsignUpPasswordError();

        void onSignUpContactLenghtError();

        void onSignupContactNumberError();

        void onSignUpRequiredFieldError();
    }
}
