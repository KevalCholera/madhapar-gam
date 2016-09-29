package com.madhapar.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.PresneterInt;
import com.madhapar.Util.UtilClass;

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
    private ChangePasswordViewInt changePasswordViewInt=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btnSave)
    public void save(){
        presenter = new PresenterClass();
        presenter.changePasswordCredential(etChangeNewPassword.getText().toString(),etChangeConfirmNewPassword.getText().toString(),changePasswordViewInt);
    }
    @OnClick(R.id.btnChnagePasswordCancel)
    public void cancelChangePassword(){
        UtilClass.changeActivity(ChangePasswordActivity.this,LoginActivity.class,true);
    }
    @Override
    public void changePasswordValidateResult(int check) {
        if(check == UtilClass.RequiredFieldError){
            UtilClass.displyMessage("Enter Required Field",ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        }
        else if(check == UtilClass.PasswordError){
            UtilClass.displyMessage("Enter New Password",ChangePasswordActivity.this,Toast.LENGTH_SHORT);
        }
        else if(check == UtilClass.PasswordLengthError){
            UtilClass.displyMessage("Password must be greater than 6 letters",ChangePasswordActivity.this,Toast.LENGTH_SHORT);
        }
        else if(check == UtilClass.MatchPassword){
            UtilClass.displyMessage("Password cant match Enter Valid Confirm Password",ChangePasswordActivity.this,Toast.LENGTH_SHORT);
        }
        else if(check == UtilClass.ConfirmPassword){
            UtilClass.displyMessage("Enter Confirm Password",ChangePasswordActivity.this,Toast.LENGTH_SHORT);
        }
        if(check == UtilClass.Success){
            UtilClass.displyMessage("Password Update",ChangePasswordActivity.this,Toast.LENGTH_SHORT);
        }
    }
}
