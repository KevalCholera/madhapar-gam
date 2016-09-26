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

public class ForgetPassword extends AppCompatActivity implements ForgetPasswordViewInt {
    @BindView(R.id.etForgetMobileNumber)
    EditText etForgetMobileNumber;
    @BindView(R.id.btnRecover)
    Button btnRecover;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    private PresneterInt presenter;
    private ForgetPasswordViewInt forgetPasswordViewInt=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btnRecover)
    public void recover(){
        presenter = new PresenterClass(this);
        presenter.forgetPasswordCredentials(etForgetMobileNumber.getText().toString(),forgetPasswordViewInt);
    }
    @OnClick(R.id.btnCancel)
    public void cancel(){
        UtilClass.changeActivity(ForgetPassword.this,LoginActivity.class,true);
    }
    @OnClick(R.id.tvForgetPasswordBack)
    public void back(){
        UtilClass.changeActivity(ForgetPassword.this,LoginActivity.class,true);
    }
    @Override
    public void forgetPasswordValidateResult(int check) {
        if(check == UtilClass.UserIdLengthError){
            UtilClass.displyMessage("Enter Valid Contact Number",ForgetPassword.this, Toast.LENGTH_SHORT);
        }
        if(check == UtilClass.UserIdError){
            UtilClass.displyMessage("Contact Number must be Greater Than 7 letters",ForgetPassword.this,Toast.LENGTH_SHORT);
        }
        if(check == UtilClass.Success){
            UtilClass.displyMessage("Password Update",ForgetPassword.this,Toast.LENGTH_SHORT);
        }
    }
}
