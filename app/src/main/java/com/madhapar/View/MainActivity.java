package com.madhapar.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.example.smartsense.newproject.R;
import com.madhapar.Util.UtilClass;
import com.madhapar.Presenter.PresenterClass;

import butterknife.internal.Utils;


public class MainActivity extends BaseActivity implements ViewInt, View.OnClickListener {
    private Button btnSubActivity, btnLogin;
    private TextView tvMainButtonClickText;
    private PresenterClass presenter;
    private ViewInt mViewInt;
    private EditText etMainUsernaeme, etMainPassword;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        btnSubActivity = (Button) findViewById(R.id.btnMainSubActivity);
        tvMainButtonClickText = (TextView) findViewById(R.id.tvMainClickText);
        etMainPassword = (EditText) findViewById(R.id.etMainPasswordText);
        etMainUsernaeme = (EditText) findViewById(R.id.etMainNameText);
        btnLogin = (Button) findViewById(R.id.btnLoginMain);
        progress = (ProgressBar) findViewById(R.id.progressMainLogin);
        presenter = new PresenterClass();
        mViewInt = this;
        btnLogin.setOnClickListener(this);
        btnSubActivity.setOnClickListener(this);
    }


    @Override
    public void validationResult(int checkCode) {
        if (checkCode == 0) {
            tvMainButtonClickText.setText(getString(R.string.passwordError));
            UtilClass.hideProgress(progress);

        } else if (checkCode == UtilClass.PasswordError) {
            tvMainButtonClickText.setText(getString(R.string.usernameError));
            UtilClass.hideProgress(progress);
        }
        if (checkCode == 3) {
            tvMainButtonClickText.setText(getString(R.string.loginFail));
            UtilClass.hideProgress(progress);
        }
        if (checkCode == 2) {
            tvMainButtonClickText.setText(getString(R.string.loginSucess));
            UtilClass.hideProgress(progress);
            presenter.changeActivity(MainActivity.this, SubActivity.class, true);
        }
    }

    @Override
    public void onClick(View view) {
        if (presenter == null) {
            presenter = new PresenterClass();
        }
        if (view == btnLogin) {
            UtilClass.showProgress(progress);
            presenter.checkLogin(etMainUsernaeme.getText().toString(), etMainPassword.getText().toString(), mViewInt, MainActivity.this);
        } else if (view == btnSubActivity) {
            presenter.changeActivity(MainActivity.this, LoginActivity.class, true);
        }
    }

    public RequestQueue getRequestQueue() {
        RequestQueue requestQueue = null;
        if (requestQueue == null) {
//            requestQueue = Volley.newRequestQueue(getApplicationContext());
            // requestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack(null, newSslSocketFactory()));
        }
        return requestQueue;
    }
}
