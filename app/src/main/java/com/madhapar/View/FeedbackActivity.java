package com.madhapar.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.PresneterInt;
import com.madhapar.R;
import com.madhapar.Util.UtilClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends AppCompatActivity implements FeedbackActivityInt {
    @BindView(R.id.etFeedbackSubject)
    EditText etFeedbackSubject;
    @BindView(R.id.etFeedbackFeedback)
    EditText etFeedback;
    private PresneterInt presenter;
    private FeedbackActivityInt feedbackActivityInt = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        finish();
    }

    @OnClick(R.id.btnFeedbackSend)
    public void feedback() {
        if (UtilClass.isInternetAvailabel(this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            presenter = new PresenterClass();
            presenter.feedbackValidateCredentials(etFeedbackSubject.getText().toString(), etFeedback.getText().toString(), feedbackActivityInt);
        } else {
            UtilClass.hideProgress();
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), FeedbackActivity.this, Toast.LENGTH_SHORT);

        }

    }

    @Override
    public void feedbackValidateResult(int check) {
        UtilClass.hideProgress();
        if (check == UtilClass.FeedbackFieldError) {
            UtilClass.displyMessage(getString(R.string.feedbackFeildEmpty), FeedbackActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.FeedbackSubjectRequiredError) {
            UtilClass.displyMessage(getString(R.string.feedbackSubjetRequired), FeedbackActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.FeedbackSubjectValidateError) {
            UtilClass.displyMessage(getString(R.string.feedbackSubjectValid), FeedbackActivity.this, Toast.LENGTH_SHORT);
        } else if (check == UtilClass.FeedbackdRequiredError) {
            UtilClass.displyMessage(getString(R.string.feedbackRequired), FeedbackActivity.this, Toast.LENGTH_SHORT);
        } else
            UtilClass.displyMessage(getString(R.string.feedbackValid), FeedbackActivity.this, Toast.LENGTH_SHORT);
    }

    @Override
    public void onFeedbackResponseError(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, FeedbackActivity.this, Toast.LENGTH_SHORT);

    }

    @Override
    public void onFeedbackSuccess(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, FeedbackActivity.this, Toast.LENGTH_SHORT);
        finish();

    }

    @Override
    public void onFeedbackRequestError() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), FeedbackActivity.this, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        UtilClass.closeKeyboard(this);
        super.onDestroy();
    }


}
