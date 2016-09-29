package com.madhapar.View;

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
    public void signup(){
        presenter = new PresenterClass(this);
        presenter.signUpValidationCredentials(etFirstName.getText().toString(),etLastName.getText().toString(),etMobileNumber.getText().toString(),etSignUpPassword.getText().toString(),etFamilyMember.getText().toString());
    }
    @OnClick(R.id.imgBack)
    public void back(){
        finish();
    }

    @Override
    public void signUpValidateResult(int check) {
        switch (check){
            case UtilClass.RequiredFieldError:
            {
                UtilClass.displyMessage("Enter Required Field",SignUpActivity.this,Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.FamilyMemberError:
            {
                UtilClass.displyMessage("Enter Family Member",SignUpActivity.this,Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.FirstNameError:
            {
                UtilClass.displyMessage("Enter First Name",SignUpActivity.this,Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.LastNameError:
            {
                UtilClass.displyMessage("Enter Valid Last Name",SignUpActivity.this,Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.PasswordError:
            {
                UtilClass.displyMessage("Enter Password",SignUpActivity.this,Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.PasswordLengthError:
            {
                UtilClass.displyMessage("Password must be greater than 6 letters",SignUpActivity.this,Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.Success:
            {
                UtilClass.changeActivity(SignUpActivity.this,MainActivity.class,true);
                break;
            }
            case UtilClass.UserIdError:
            {
                UtilClass.displyMessage("Enter Contact Number",SignUpActivity.this,Toast.LENGTH_SHORT);
                break;
            }
            case UtilClass.UserIdLengthError:
            {
                UtilClass.displyMessage("Contact Number must be 7 to 14 digit",SignUpActivity.this,Toast.LENGTH_SHORT);
                break;
            }
            default:
            {
                setContentView(R.layout.activity_sign_up);
                break;
            }
        }


    }
}
