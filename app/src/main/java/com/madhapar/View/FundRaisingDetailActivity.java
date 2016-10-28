package com.madhapar.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.madhapar.View.Fragment.FundRaisingLessFragment;
import com.madhapar.View.Fragment.FundRaisingMoreFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smartsense on 20/10/16.
 */

public class FundRaisingDetailActivity extends BaseActivity {
    @BindView(R.id.tvFundrainsingDetailProjectName)
    TextView tvFundrainsingDetailProjectName;
    @BindView(R.id.tvFundRaisingDetailProjectDate)
    TextView tvFundRaisingDetailProjectDate;
    @BindView(R.id.tvFundRaisingDetailProjectPlace)
    TextView tvFundRaisingDetailProjectPlace;
    @BindView(R.id.tvFundraisingDetailProjectOrganizer)
    TextView tvFundraisingDetailProjectOrganizer;
    @BindView(R.id.btnFundRaisingMoreLess)
    Button btnFundRaisingMoreLess;
    private FragmentManager mFragmetnManager;
    private JSONObject projectDetail;
    @BindView(R.id.ivFundRaisingImage)
    ImageView ivFundRaisingImage;

    @OnClick(R.id.btnFundRaisingMoreLess)
    void changeFragment() {
        if (btnFundRaisingMoreLess.getText().toString().equalsIgnoreCase(getString(R.string.fundInfoMore))) {
            btnFundRaisingMoreLess.setText(getString(R.string.fundInfoLess));
            Bundle bundle = new Bundle();
            bundle.putString("projectDescription", projectDetail.optString("projectDescription"));
            Fragment fundRaisingMoreFragment = new FundRaisingMoreFragment();
            fundRaisingMoreFragment.setArguments(bundle);
            mFragmetnManager.beginTransaction().replace(R.id.flFundRaisingContainer, fundRaisingMoreFragment).commit();
        } else {
            btnFundRaisingMoreLess.setText(getString(R.string.fundInfoMore));
            Bundle bundle = new Bundle();
            bundle.putString("projectDetail", projectDetail.toString());
            Fragment fundRaisingLessFragment = new FundRaisingLessFragment();
            fundRaisingLessFragment.setArguments(bundle);
            mFragmetnManager.beginTransaction().replace(R.id.flFundRaisingContainer, fundRaisingLessFragment).commit();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_raising_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFragmetnManager = getSupportFragmentManager();
        btnFundRaisingMoreLess.setText(getString(R.string.fundInfoMore));
        if (getIntent().getStringExtra("projectDetail") != null) {
            try {
                projectDetail = new JSONObject(getIntent().getStringExtra("projectDetail"));
                if (projectDetail != null) {
                    tvFundrainsingDetailProjectName.setText(projectDetail.optString("projectName"));
                    JSONObject locationObj = projectDetail.optJSONObject("newsLocation");
                    if (locationObj != null) {
                        tvFundRaisingDetailProjectPlace.setText(locationObj.optString("locationName"));
                    }
                    tvFundraisingDetailProjectOrganizer.setText("Managed By: " + projectDetail.optString("projectHandleby"));
                    String projectDate = projectDetail.optString("projectFromDate") + " - " + projectDetail.optString("projectToDate");
                    tvFundRaisingDetailProjectDate.setText(projectDate);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Picasso.with(this).load(Constants.RequestConstants.BaseUrlForImage + projectDetail.optString("projectPic")).placeholder(R.mipmap.img_fund_raising_banner).error(R.mipmap.img_fund_raising_banner).into(ivFundRaisingImage);
        Bundle bundle = new Bundle();

        bundle.putString("projectDetail", projectDetail.toString());
        Fragment fundRaisingLessFragment = new FundRaisingLessFragment();
        fundRaisingLessFragment.setArguments(bundle);
        mFragmetnManager.beginTransaction().replace(R.id.flFundRaisingContainer, fundRaisingLessFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
