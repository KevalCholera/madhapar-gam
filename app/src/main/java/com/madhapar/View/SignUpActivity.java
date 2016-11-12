package com.madhapar.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.PresneterInt;
import com.madhapar.R;
import com.madhapar.Util.UtilClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class SignUpActivity extends BaseActivity implements SignUpViewInt {
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLasttName)
    EditText etLastName;
    @BindView(R.id.etMiddleName)
    EditText etMiddleName;
    @BindView(R.id.etSignUpPassord)
    EditText etSignUpPassword;
    @BindView(R.id.etMobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.llSignupBack)
    LinearLayout llSignupBack;

    @OnFocusChange(R.id.etMobileNumber)
    void changeHint() {
        if (etMobileNumber.hasFocus()) {
            etMobileNumber.setHint(getString(R.string.mobileNumberLoginWithCountryCode));
        } else {
            etMobileNumber.setHint("");
        }
    }


    @OnClick(R.id.llSignupBack)
    public void back() {
        UtilClass.closeKeyboard(this);
        UtilClass.hideProgress();
        finish();

    }

    private PresneterInt presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSignUp)
    public void signup() {
        UtilClass.closeKeyboard(SignUpActivity.this);
        if (UtilClass.isInternetAvailabel(SignUpActivity.this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            presenter = new PresenterClass(this);
            presenter.signUpValidationCredentials(etFirstName.getText().toString(), etLastName.getText().toString(), etMobileNumber.getText().toString(), etSignUpPassword.getText().toString(), etMiddleName.getText().toString(), this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }
    }


    @Override
    public void signUpValidateResult(int check) {
        UtilClass.hideProgress();
        switch (check) {
            case UtilClass.RequiredFieldError: {
                UtilClass.displyMessage(getString(R.string.enterrequiredfiels), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.MiddleNameError: {
                UtilClass.displyMessage(getString(R.string.enterfamilymember), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.FirstNameError: {
                UtilClass.displyMessage(getString(R.string.enterfirstnm), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.FirstNameLenght: {
                UtilClass.displyMessage(getString(R.string.firsrNameCharacter50), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.LastNameLength: {
                UtilClass.displyMessage(getString(R.string.lastNameCharacter50), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.MiddleNameLength: {
                UtilClass.displyMessage(getString(R.string.middleNameCharacter50), SignUpActivity.this, Toast.LENGTH_SHORT);
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

                UtilClass.changeActivity(SignUpActivity.this, LoginActivity.class, true);
                break;
            }
            case UtilClass.UserIdError: {
                UtilClass.displyMessage(getString(R.string.contactError), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.UserIdLengthError: {
                UtilClass.displyMessage(getString(R.string.ErrorContactLength), SignUpActivity.this, Toast.LENGTH_SHORT);
                break;
            }
            default: {
                setContentView(R.layout.activity_sign_up);
                break;
            }
        }
    }

    @Override
    public void signUpResponseError(String error) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(error, SignUpActivity.this, 0);

    }

    @Override
    public void signUpRequestError() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), SignUpActivity.this, 0);
    }
}
