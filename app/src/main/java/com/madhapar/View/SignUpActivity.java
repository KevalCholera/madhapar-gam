package com.madhapar.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.PresneterInt;
import com.madhapar.Util.UtilClass;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements SignUpViewInt {
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLasttName)
    EditText etLastName;
    @BindView(R.id.etFamilyMember)
    EditText etFamilyMember;
    @BindView(R.id.etSignUpPassord)
    EditText etSignUpPassword;
    @BindView(R.id.etMobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    Context c = SignUpActivity.this;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    private PresneterInt presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnSignUp)
    public void signup() {
        presenter = new PresenterClass(this);
        presenter.signUpValidationCredentials(etFirstName.getText().toString(), etLastName.getText().toString(), etMobileNumber.getText().toString(), etSignUpPassword.getText().toString(), etFamilyMember.getText().toString());
    }

    @OnClick(R.id.imgBack)
    public void back() {
        //    finish();
        UtilClass.changeActivity(SignUpActivity.this, ForgetPassword.class, true);
    }

    @Override
    public void signUpValidateResult(int check) {
        switch (check) {
            case UtilClass.RequiredFieldError: {
                UtilClass.displyMessage(getString(R.string.enterrequiredfiels), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.FamilyMemberError: {
                UtilClass.displyMessage(getString(R.string.enterfamilymember), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.FirstNameError: {
                UtilClass.displyMessage(getString(R.string.enterfirstnm), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.LastNameError: {
                UtilClass.displyMessage(getString(R.string.enterlastnm), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.PasswordError: {
                UtilClass.displyMessage(getString(R.string.enterpassword), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.PasswordLengthError: {
                UtilClass.displyMessage(getString(R.string.passwordlength), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.Success: {
                UtilClass.changeActivity(SignUpActivity.this, MainActivity.class, true);
                break;
            }
            case UtilClass.UserIdError: {
                UtilClass.displyMessage(getString(R.string.contact), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.UserIdLengthError: {
                UtilClass.displyMessage(getString(R.string.contactlength), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            default: {
                setContentView(R.layout.activity_sign_up);
                break;
            }
        }


    }

    @Override
    public void signUpResponseError(JSONObject error) {
        if (error != null) {
            if (error.has("message"))
                UtilClass.displyMessage(error.optString("message"), SignUpActivity.this, 0);
        }
    }
}
