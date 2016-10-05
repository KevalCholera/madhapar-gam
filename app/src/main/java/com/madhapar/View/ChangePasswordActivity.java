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
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Fragment.EventFragment;

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
    @BindView(R.id.toolbarChangePassword)
    Toolbar toolbarChangePassword;
    private PresneterInt presenter;
    private ChangePasswordViewInt changePasswordViewInt=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        super.setSupportActionBar(toolbarChangePassword);
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
    @Override
    public void onBackPressed() {
        UtilClass.changeActivity(ChangePasswordActivity.this,MainActivity.class,true);    }
    @OnClick(R.id.btnSave)
    public void save(){
        presenter = new PresenterClass();
        presenter.changePasswordCredential(etChangeNewPassword.getText().toString(),etChangeConfirmNewPassword.getText().toString(),changePasswordViewInt);
    }
    @OnClick(R.id.btnChnagePasswordCancel)
    public void cancelChangePassword(){
        UtilClass.changeActivity(ChangePasswordActivity.this,EventFragment.class,true);
    }
    @Override
    public void changePasswordValidateResult(int check) {
        if(check == UtilClass.RequiredFieldError){
            UtilClass.displyMessage(getString(R.string.enterrequiredfiels),ChangePasswordActivity.this, Toast.LENGTH_SHORT);
        }
        else if(check == UtilClass.PasswordError){
            UtilClass.displyMessage(getString(R.string.enternewpassword),ChangePasswordActivity.this,Toast.LENGTH_SHORT);
        }
        else if(check == UtilClass.PasswordLengthError){
            UtilClass.displyMessage(getString(R.string.passwordlength),ChangePasswordActivity.this,Toast.LENGTH_SHORT);
        }
        else if(check == UtilClass.MatchPassword){
            UtilClass.displyMessage(getString(R.string.passwordmatch),ChangePasswordActivity.this,Toast.LENGTH_SHORT);
        }
        else if(check == UtilClass.ConfirmPassword){
            UtilClass.displyMessage(getString(R.string.enterConfirmPassword),ChangePasswordActivity.this,Toast.LENGTH_SHORT);
        }
        if(check == UtilClass.Success){
            UtilClass.displyMessage(getString(R.string.passwordupdate),ChangePasswordActivity.this,Toast.LENGTH_SHORT);
        }
    }
}
