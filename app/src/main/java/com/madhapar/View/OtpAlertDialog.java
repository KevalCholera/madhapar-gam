package com.madhapar.View;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Ronak on 9/28/2016.
 */
public class OtpAlertDialog extends AlertDialog.Builder implements OtpAlertDialogInt, ForgetPasswordViewInt {
    private Activity activity;
    private AlertDialog alert;
    private String contactNumber;
    private JSONObject otpObj;
    private PresenterClass presenter;
    private int type;
    @BindView(R.id.etCode1)
    EditText code1;
    @BindView(R.id.etCode2)
    EditText code2;
    @BindView(R.id.etCode3)
    EditText code3;
    @BindView(R.id.etCode4)
    EditText code4;

    @OnTextChanged(R.id.etCode1)
    public void code1() {
        if (code1.getText().toString().trim().length() == 1) {
            code2.requestFocus();
            code2.setCursorVisible(true);
        }
    }

    @OnTextChanged(R.id.etCode2)
    public void onText() {
        if (code2.getText().toString().trim().length() == 1) {
            code3.requestFocus();
            code3.setCursorVisible(true);
        }
    }

    @OnTextChanged(R.id.etCode3)
    public void code3() {
        if (code3.getText().toString().trim().length() == 1) {
            code4.requestFocus();
            code4.setCursorVisible(true);
        }
    }

    @OnTextChanged(R.id.etCode4)
    public void code4() {
        if (code4.getText().toString().trim().length() == 1) {

            String insertedOtp = code1.getText().toString().trim() + code2.getText().toString().trim() + code3.getText().toString().trim() + code4.getText().toString().trim();
            Log.e("otp", "inserted" + insertedOtp);
            Log.e("otp", this.otpObj.optString("otpValue"));
            if (insertedOtp.equals(this.otpObj.optString("otpValue"))) {
                if (UtilClass.isInternetAvailabel(activity)) {
                    UtilClass.showProgress(activity, activity.getString(R.string.msgPleaseWait));
                    if (this.type == 1)
                        new PresenterClass().verifyForgotPasswordOtp(this.contactNumber, insertedOtp, this);
                    else {
                        new PresenterClass().verifyUserOtp(this.contactNumber, insertedOtp, this);
                    }
                } else {
                    UtilClass.displyMessage(activity.getString(R.string.msgSomethigWentWrong), this.activity, 0);
                }
            } else {
                UtilClass.displyMessage(activity.getString(R.string.msgOtpNotMatch), this.activity, 0);
            }
        }
    }

    @OnClick(R.id.tvResendCode)
    public void onResondCode() {
        UtilClass.hideProgress();
        presenter = new PresenterClass();
        if (UtilClass.isInternetAvailabel(activity)) {
            UtilClass.showProgress(activity, activity.getString(R.string.msgPleaseWait));
            new PresenterClass().forgetPasswordCredentials(this.contactNumber, this, this.type);
        } else {
            UtilClass.displyMessage(activity.getString(R.string.msgSomethigWentWrong), this.activity, 0);
        }
    }

    @OnClick(R.id.tvCancel)
    public void onCancel() {
        UtilClass.hideProgress();
        alert.dismiss();
        activity.finish();
    }

    public OtpAlertDialog(@NonNull final AppCompatActivity context, JSONObject otpObj1, String contactNumber, int type) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_otp_code, null, false);
        this.setView(view);
        this.activity = context;
        this.contactNumber = contactNumber;
        SharedPreferenceUtil.putValue(Constants.UserData.UserMobileNo, contactNumber);
        SharedPreferenceUtil.save();
        this.otpObj = otpObj1;
        ButterKnife.bind(this, view);
        this.type = type;
        alert = create();
        alert.show();
        alert.setCancelable(false);
        code1.requestFocus();
        alert.getWindow().setBackgroundDrawableResource(R.drawable.alertdialogdesign);
    }
    @Override
    public void forgetPasswordValidateResult(int check) {
    }

    @Override
    public void forgotPasswordSuccess(JSONObject otpResponse) {
        UtilClass.hideProgress();
        code1.setText("");
        code2.setText("");
        code3.setText("");
        code4.setText("");
        code1.requestFocus();
        this.otpObj = otpResponse;
    }

    @Override
    public void forgotPasswrodFail(String message) {
        UtilClass.hideProgress();
        Log.e("resend success", "otpFail" + message);
    }

    @Override
    public void forgotPasswordRequestError() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(activity.getString(R.string.msgSomethigWentWrong), activity, 0);
    }

    @Override
    public void otpVerificationSuccessfull(JSONObject verifyObj) {
        if (verifyObj.has("otpToken") && this.type == 1) {
            UtilClass.displyMessage(verifyObj.optString("message"), activity, 0);
            Intent intent = new Intent(activity, ChangePasswordActivity.class);
            intent.putExtra("otpToken", verifyObj.optString("otpToken"));
            activity.startActivity(intent);
        } else {
            UtilClass.displyMessage(verifyObj.optString("message"), activity, 0);
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        }
        UtilClass.hideProgress();
        alert.dismiss();
        activity.finish();
    }

    @Override
    public void otpVerificationFail(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, activity, 0);
    }

    @Override
    public void otpVerifyRequestError() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(activity.getString(R.string.msgSomethigWentWrong), activity, 0);
    }
}
