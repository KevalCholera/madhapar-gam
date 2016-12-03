package com.madhapar.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.madhapar.R;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.ExecutionException;

/**
 * Created by smartsense on 30/09/16.
 */

public class SplashScreenActivity extends BaseActivity {
    protected Thread thread;
    protected long splashTime;
    protected static final int UPDATE_REQUEST = 100;
    protected AlertDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(splashTime);
                    if (SharedPreferenceUtil.getString(Constants.UserData.token, "").equalsIgnoreCase("")) {
                        UtilClass.changeActivity(SplashScreenActivity.this, LoginActivity.class, true);
                    } else {
                        UtilClass.changeActivity(SplashScreenActivity.this, MainActivity.class, true);
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        compareVersion();

    }

    private void compareVersion() {
        if (UtilClass.isInternetAvailabel(this)) {
            try {
                String latestVersion = new GetLatestVersion().execute().get();
                String currentVersion = checkCurrentVersionInfo();
                Log.d("versionCompare", latestVersion + "   " + currentVersion);
                if (Float.valueOf(currentVersion) < Float.valueOf(latestVersion)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = LayoutInflater.from(this);
                    View view = inflater.inflate(R.layout.alert_version, null, false);
                    alertBuilder.setView(view);
                    TextView tvMessage = (TextView) view.findViewById(R.id.tvVersionDialogMessage);
                    tvMessage.setText("A new version of MadhaparGamApp is available.please updaete to version " + latestVersion + " now. ");
                    TextView tvUpdate = (TextView) view.findViewById(R.id.tvVersionDialogUpdate);
                    TextView tvCancel = (TextView) view.findViewById(R.id.tvVersionDialgCancel);
                    tvUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            final String appPackageName = getPackageName();
                            try {
                                startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)), UPDATE_REQUEST);
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)), UPDATE_REQUEST);
                            }

                        }
                    });
                    tvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            splashTime = 1000;
                            thread.start();
                        }
                    });
                    dialog = alertBuilder.create();
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.alert_dialog_white_border);
                    dialog.setCancelable(false);
                    dialog.show();

                } else {
                    splashTime = 1000;
                    thread.start();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            splashTime = 2000;
            thread.start();
        }
    }


    private String checkCurrentVersionInfo() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return pInfo.versionName;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_REQUEST) {
            compareVersion();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class GetLatestVersion extends AsyncTask<String, String, String> {
        String latestVersion;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String urlOfAppFromPlayStore = "https://play.google.com/store/apps/details?id=com.madhapar";
                Document doc = Jsoup.connect(urlOfAppFromPlayStore).get();
                latestVersion = doc.getElementsByAttributeValue("itemprop", "softwareVersion").first().text();
            } catch (Exception e) {
                e.printStackTrace();

            }

            return latestVersion;
        }
    }


}
