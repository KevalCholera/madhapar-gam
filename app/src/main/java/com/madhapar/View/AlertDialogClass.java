package com.madhapar.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import butterknife.OnTextChanged;

/**
 * Created by Ronak on 9/28/2016.
 */
public class AlertDialogClass extends AlertDialog.Builder implements AlertDialofClassInt{
    private Activity mactivi;
    private AlertDialog alert;
    private TextView tvResendCode1;
    private TextView tvCancel;
    @BindView(R.id.etCode1)
    EditText code1;
    @BindView(R.id.etCode2)
    EditText code2;
    @BindView(R.id.etCode3)
    EditText code3;
    @BindView(R.id.etCode4)
    EditText code4;
    @OnTextChanged(R.id.etCode1)
    public void code1(){
        if(code1.getText().length()!=1){
            code1.requestFocus();
            code1.setCursorVisible(true);
        }
        else {
            code2.requestFocus();
            code2.setCursorVisible(true);
        }
    }
    @OnTextChanged(R.id.etCode2)
    public void onText(){
        if(code2.getText().length()!=1){
            code2.requestFocus();
            code2.setCursorVisible(true);
        }
        else {
            code3.requestFocus();
            code3.setCursorVisible(true);
        }
    }
    @OnTextChanged(R.id.etCode3)
    public void code3(){
        if(code3.length()!=1){
            code3.requestFocus();
            code3.setCursorVisible(true);
        }
        else {
            code4.requestFocus();
            code4.setCursorVisible(true);
        }
    }
    @OnClick(R.id.tvResendCode)
    public void onresondCode(){
        UtilClass.displyMessage("Resend OTP Successfully",getContext(),Toast.LENGTH_SHORT);
    }
    @OnClick(R.id.tvCancel)
    public void onCancel(){
        alert.dismiss();
    }
    public AlertDialogClass(@NonNull final Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_otp_code,null,false);
        this.setView(view);
        ButterKnife.bind(this,view);
        alert=create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(R.drawable.alertdialogdesign);
    }
}
