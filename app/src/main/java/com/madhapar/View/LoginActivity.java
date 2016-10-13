package com.madhapar.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.PresneterInt;
import com.madhapar.Util.UtilClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smartsense on 22/09/16.
 */

public class LoginActivity extends BaseActivity implements LoginInt {
    @BindView(R.id.etLoginId)
    EditText etLoginId;
    @BindView(R.id.etLoginPassword)
    EditText etLoginPassword;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.tvUserSignUp)
    TextView tvUserSignUp;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private PresneterInt presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void submit() {
        UtilClass.closeKeyboard(LoginActivity.this);
        if (UtilClass.isInternetAvailabel(LoginActivity.this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            presenter = new PresenterClass();
            presenter.validateCredentials(etLoginId.getText().toString(), etLoginPassword.getText().toString(), this, this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }

    }

    @OnClick(R.id.tvUserSignUp)
    public void signup() {
        UtilClass.closeKeyboard(LoginActivity.this);
        UtilClass.hideProgress();
        UtilClass.changeActivity(LoginActivity.this, SignUpActivity.class, false);
    }

    @OnClick(R.id.ivClose)
    public void close() {
        UtilClass.hideProgress();
        UtilClass.changeActivity(LoginActivity.this, FeedbackActivity.class, true);
    }

    @OnClick(R.id.tvForgetPassword)
    public void forgetpassword() {
        UtilClass.closeKeyboard(LoginActivity.this);
        UtilClass.hideProgress();
        UtilClass.changeActivity(LoginActivity.this, ForgetPasswordActivity.class, false);
    }

    @Override
    public void loginValidateResult(int check) {
        UtilClass.hideProgress();
        if (check == UtilClass.UserIdError) {
            UtilClass.displyMessage(getString(R.string.contactError), LoginActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.PasswordLengthError) {
            UtilClass.displyMessage(getString(R.string.passwordlength), LoginActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.PasswordError) {
            UtilClass.displyMessage(getString(R.string.enterpassword), LoginActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.UserIdLengthError) {
            UtilClass.displyMessage(getString(R.string.ErrorContactLength), LoginActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.Success) {
            UtilClass.changeActivity(LoginActivity.this, MainActivity.class, true);
        } else if (check == UtilClass.RequiredFieldError) {
            UtilClass.displyMessage(getString(R.string.enterrequiredfiels), LoginActivity.this, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onFailLogin(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, LoginActivity.this, Toast.LENGTH_SHORT);
    }

    @Override
    public void onRequestFail() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), LoginActivity.this, Toast.LENGTH_SHORT);
    }
}
