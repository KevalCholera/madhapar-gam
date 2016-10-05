package com.madhapar.View;

import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class ForgetPassword extends BaseActivity  implements ForgetPasswordViewInt,AlertDialofClassInt {
    @BindView(R.id.etForgetMobileNumber)
    EditText etForgetMobileNumber;
    @BindView(R.id.btnRecover)
    Button btnRecover;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.toolbarForgotPassword)
    Toolbar toolbar;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alBuilder;
    private PresneterInt presenter;
    private ForgetPasswordViewInt forgetPasswordViewInt=this;
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
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnRecover)
    public void recover(){
        presenter = new PresenterClass();
        presenter.forgetPasswordCredentials(etForgetMobileNumber.getText().toString(),forgetPasswordViewInt);
    }
    @OnClick(R.id.btnCancel)
    public void cancel(){
        UtilClass.changeActivity(ForgetPassword.this,FeedbackActivity.class,true);
    }
   // @OnClick(R.id.tvForgetPasswordBack)
    public void back(){

    }
    @Override
    public void forgetPasswordValidateResult(int check) {
        if(check == UtilClass.UserIdLengthError){
            UtilClass.displyMessage(getString(R.string.contactlength),ForgetPassword.this, Toast.LENGTH_SHORT);
        }
        if(check == UtilClass.UserIdError){
            UtilClass.displyMessage(getString(R.string.contact),ForgetPassword.this,Toast.LENGTH_SHORT);
        }
        if(check == UtilClass.Success){
            UtilClass.displyMessage(getString(R.string.passwordUpdate),ForgetPassword.this,Toast.LENGTH_SHORT);
            presenter.alert(ForgetPassword.this);
        }
    }

    @Override
    public void onBackPressed() {
        UtilClass.changeActivity(ForgetPassword.this,LoginActivity.class,true);    }
}
