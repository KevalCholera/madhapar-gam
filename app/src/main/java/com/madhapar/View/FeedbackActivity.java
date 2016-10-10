package com.madhapar.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class FeedbackActivity extends AppCompatActivity implements FeedbackActivityInt {
    @BindView(R.id.etFeedbackName)
    EditText etFeedbackName;
    @BindView(R.id.etFeedbackMobile)
    EditText etFeedbackMobile;
    @BindView(R.id.etFeedbackSubject)
    EditText etFeedbackSubject;
    @BindView(R.id.etFeedbackFeedback)
    EditText etFeedback;
    @BindView(R.id.toolbarFeedback)
    Toolbar toolbarFeedback;
    private PresneterInt presenter;
    private FeedbackActivityInt feedbackActivityInt=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        super.setSupportActionBar(toolbarFeedback);
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
        UtilClass.changeActivity(FeedbackActivity.this,ProfileActivity.class,true);    }

    @OnClick(R.id.btnFeedbackSend)
    public void feedback(){
        presenter = new PresenterClass();
        presenter.feedbackValidateCredentials(etFeedbackName.getText().toString(),etFeedbackMobile.getText().toString(),etFeedbackSubject.getText().toString(),etFeedback.getText().toString(),feedbackActivityInt);
    }

    @Override
    public void feedbackValidateResult(int check) {
        if(check == UtilClass.RequiredFieldError){
            UtilClass.displyMessage(getString(R.string.enterrequiredfiels),FeedbackActivity.this, Toast.LENGTH_SHORT);
        }
        if(check == UtilClass.FeedbackSubject){
            UtilClass.displyMessage(getString(R.string.subjectrequired),FeedbackActivity.this,Toast.LENGTH_SHORT);
        }
        if(check == UtilClass.Feeedback){
            UtilClass.displyMessage(getString(R.string.enterFeedback),FeedbackActivity.this,Toast.LENGTH_SHORT);
        }
        if(check == UtilClass.Success) {
            UtilClass.displyMessage(getString(R.string.thanku), FeedbackActivity.this, Toast.LENGTH_SHORT);
        }

    }
}
