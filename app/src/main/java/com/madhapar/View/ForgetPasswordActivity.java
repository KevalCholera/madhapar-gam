package com.madhapar.View;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.PresneterInt;
import com.madhapar.Util.UtilClass;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordViewInt {
    @BindView(R.id.etForgetMobileNumber)
    EditText etForgetMobileNumber;
    @BindView(R.id.btnRecover)
    Button btnRecover;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.toolbarForgotPassword)
    Toolbar toolbar;
    private PresneterInt presenter;
    private ForgetPasswordViewInt forgetPasswordViewInt = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        super.setSupportActionBar(toolbar);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnRecover)
    public void recover() {
        UtilClass.closeKeyboard(ForgetPasswordActivity.this);
        if (UtilClass.isInternetAvailabel(this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            presenter = new PresenterClass();
            presenter.forgetPasswordCredentials(etForgetMobileNumber.getText().toString(), forgetPasswordViewInt, 1);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }
    }

    @OnClick(R.id.btnCancel)
    public void cancel() {
        UtilClass.closeKeyboard(ForgetPasswordActivity.this);
        UtilClass.hideProgress();
        finish();
    }


    @Override
    public void forgetPasswordValidateResult(int check) {
        UtilClass.hideProgress();
        if (check == UtilClass.UserIdLengthError) {
            UtilClass.displyMessage(getString(R.string.ErrorContactLength), ForgetPasswordActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.UserIdError) {
            UtilClass.displyMessage(getString(R.string.contactError), ForgetPasswordActivity.this, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void forgotPasswordSuccess(JSONObject optResponse) {
        UtilClass.hideProgress();
        presenter.alert(ForgetPasswordActivity.this, optResponse, etForgetMobileNumber.getText().toString(), 1);
    }

    @Override
    public void forgotPasswrodFail(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, ForgetPasswordActivity.this, Toast.LENGTH_SHORT);
    }

    @Override
    public void forgotPasswordRequestError() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), ForgetPasswordActivity.this, Toast.LENGTH_SHORT);
    }

    @Override
    public void onBackPressed() {
        UtilClass.hideProgress();
        finish();
    }
}
