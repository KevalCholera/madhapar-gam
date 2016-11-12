package com.madhapar.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.madhapar.Presenter.PresenterClass;
import com.madhapar.R;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.FundRaisingListAdapter;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
public class FundRaisingActivity extends AppCompatActivity implements FundRaisigListCallback {
    @BindView(R.id.rvFundRaisingList)
    RecyclerView rvFundRaisingList;
    private PresenterClass mPresenter;
    @BindView(R.id.ivFundRaisingImagePlaceHolder)
    ImageView ivFundRaisingImagePlaceHolder;

    private FundRaisingListAdapter mFundAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_raising);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter = new PresenterClass();
        if (UtilClass.isInternetAvailabel(this)) {
            mPresenter.getFundRaisingList(this);
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }
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

    @Override
    public void onSuccessFundRaisingList(JSONArray response) {
        UtilClass.hideProgress();
        if (response.length() > 0) {
            rvFundRaisingList.setVisibility(View.VISIBLE);
            ivFundRaisingImagePlaceHolder.setVisibility(View.GONE);
            if (mFundAdapter == null) {
                mFundAdapter = new FundRaisingListAdapter(this, response);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                rvFundRaisingList.setLayoutManager(layoutManager);
                rvFundRaisingList.setAdapter(mFundAdapter);
            } else {
                mFundAdapter.updateAdapter(response);
            }
        } else {
            rvFundRaisingList.setVisibility(View.GONE);
            ivFundRaisingImagePlaceHolder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailFundRaisingList(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, this, 0);
    }

    @Override
    public void onFailRequestFundRaisingList() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);

    }
}
