package com.madhapar.View;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends AppCompatActivity {
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.tvPageNotFound)
    TextView tvPageNotFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        if (UtilClass.isInternetAvailabel(this)) {
            webView.setVisibility(View.VISIBLE);
            tvPageNotFound.setVisibility(View.GONE);
            if (!getIntent().getBooleanExtra("isAboutUs", false)) {
                webView.loadUrl(Constants.RequestConstants.IntroductionUrl);
                getSupportActionBar().setTitle(R.string.more1);
            } else {
                webView.loadUrl(Constants.RequestConstants.AboutUsUrl);
                getSupportActionBar().setTitle(R.string.aboutus);
            }
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    webView.setVisibility(View.GONE);
                    tvPageNotFound.setVisibility(View.VISIBLE);
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    webView.stopLoading();
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });


        } else {
            webView.setVisibility(View.GONE);
            tvPageNotFound.setVisibility(View.VISIBLE);
            UtilClass.displyMessage(getString(R.string.msgInternetAppersOffline), this, 0);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
