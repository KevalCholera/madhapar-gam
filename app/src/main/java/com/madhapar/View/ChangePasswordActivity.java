package com.madhapar.View;

import android.support.v7.app.AppCompatActivity;
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
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Fragment.EventFragment;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordViewInt {
    @BindView(R.id.etChangeNewPassword)
    EditText etChangeNewPassword;
    @BindView(R.id.etChangeConfirmNewPassword)
    EditText etChangeConfirmNewPassword;
    @BindView(R.id.btnSave)
    Button btnSave;
    private PresneterInt presenter;
    private String otpToken;
    private ChangePasswordViewInt changePasswordViewInt = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent() != null) {
            otpToken = getIntent().getStringExtra("otpToken");
        }
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

    @Override
    public void onBackPressed() {
        UtilClass.hideProgress();
        UtilClass.changeActivity(ChangePasswordActivity.this, MainActivity.class, true);
    }

    @OnClick(R.id.btnSave)
    public void save() {
        UtilClass.closeKeyboard(ChangePasswordActivity.this);
        if (UtilClass.isInternetAvailabel(this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            presenter = new PresenterClass();
            presenter.changePasswordCredential(etChangeNewPassword.getText().toString().trim(), etChangeConfirmNewPassword.getText().toString().trim(), otpToken, SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo, ""), changePasswordViewInt);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }
    }

    @OnClick(R.id.btnChnagePasswordCancel)
    public void cancelChangePassword() {
        UtilClass.closeKeyboard(ChangePasswordActivity.this);
        UtilClass.hideProgress();
        finish();
    }

    @Override
    public void changePasswordValidateResult(int check) {
        UtilClass.hideProgress();
        if (check == UtilClass.RequiredFieldError) {
            UtilClass.displyMessage(getString(R.string.enterrequiredfiels), ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.PasswordError) {
            UtilClass.displyMessage(getString(R.string.enternewpassword), ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.PasswordLengthError) {
            UtilClass.displyMessage(getString(R.string.passwordlength), ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.MatchPassword) {
            UtilClass.displyMessage(getString(R.string.passwordmatch), ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.ConfirmPassword) {
            UtilClass.displyMessage(getString(R.string.enterConfirmPassword), ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void changePasswordSuccessfull(JSONObject response) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(response.optString("message"), this, 0);
        UtilClass.changeActivity(this, LoginActivity.class, true);
    }

    @Override
    public void changePasswordFail(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, this, 0);
    }

    @Override
    public void changePasswordRequestError() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }
}
