package com.madhapar.Model;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smartsense.newproject.R;
import com.madhapar.Util.UtilClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ronak on 9/26/2016.
 */
public class ForgetPasswordModel implements ForgetPasswordModelInt {

    @Override
    public void login(final String contactNumber, final OnLoginFinishedListener listener) {
                boolean error = false;
                if (TextUtils.isEmpty(contactNumber)) {
                    listener.onForgetContactNumberError();
                    error = true;
                }
                else if(!(contactNumber.toString().length() >7 && contactNumber.toString().length() < 14)){
                    listener.onForgetContactLenghtError();
                    error = true;
                }
                if (!error) {
                    listener.onForgetSuccess();
                }
    }

}
