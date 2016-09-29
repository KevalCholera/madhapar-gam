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
        presenter = new PresenterClass(this);
        presenter.validateCredentials(etLoginId.getText().toString(), etLoginPassword.getText().toString());
    }

    @OnClick(R.id.tvUserSignUp)
    public void signup() {
        UtilClass.changeActivity(LoginActivity.this, SignUpActivity.class, false);
    }

    @OnClick(R.id.ivClose)
    public void close() {
        UtilClass.changeActivity(LoginActivity.this, FeedbackActivity.class, true);
    }

    @OnClick(R.id.tvForgetPassword)
    public void forgetpassword() {
        UtilClass.changeActivity(LoginActivity.this, ForgetPassword.class, true);
    }

    @Override
    public void loginValidateResult(int check) {
        if (check == UtilClass.UserIdError) {
            UtilClass.displyMessage("Enter Contact Number", LoginActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.PasswordLengthError) {
            UtilClass.displyMessage("Enter Password Greater than 6", LoginActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.PasswordError) {
            UtilClass.displyMessage("Enter valid Password", LoginActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.UserIdLengthError) {
            UtilClass.displyMessage("Contact no Length is required 7 to 10 digit", LoginActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.Success) {
            UtilClass.displyMessage("Login Success", LoginActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.RequiredFieldError) {
            UtilClass.displyMessage("Enter Required Field", LoginActivity.this, Toast.LENGTH_SHORT);
        }

    }
}
