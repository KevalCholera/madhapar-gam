package com.madhapar.Model;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Ronak on 9/26/2016.
 */
public class SignUpModel implements SignUpModelInt {

    @Override
    public void signup(final String firstName, final String lastName, final String contactNumber, final String password, final String familyMember, final OnLoginFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                Log.e("Password",password);
                if(TextUtils.isEmpty(contactNumber) && TextUtils.isEmpty(password) && TextUtils.isEmpty(firstName) && TextUtils.isEmpty(familyMember) && TextUtils.isEmpty(lastName))
                {
                    listener.onSignUpRequiredFieldError();
                    error = true;
                }
                else if(TextUtils.isEmpty(firstName)){
                    listener.onFirstNameError();
                    error = true;
                }
                else if(TextUtils.isEmpty(lastName)){
                    listener.onlastNameError();
                    error = true;
                }
                else if (TextUtils.isEmpty(contactNumber)) {
                    listener.onSignupContactNumberError();
                    error = true;
                }
                else if(!(contactNumber.toString().length() > 7 && contactNumber.toString().length() < 14)){
                    listener.onSignUpContactLenghtError();
                    error = true;
                }
                else if (TextUtils.isEmpty(password)) {
                    listener.onsignUpPasswordError();
                    error = true;
                }
                else if((!(password.toString().length() > 6))){
                    listener.onSignupPasswordLengthError();
                    error = true;
                }
                else if(TextUtils.isEmpty(familyMember)){
                    listener.onFamilyMemberError();
                    error = true;
                }
                if (!error) {
                    listener.onSignUpSuccess();
                }
            }
        }, 2000);

    }
}
