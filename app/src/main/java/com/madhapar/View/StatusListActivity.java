package com.madhapar.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madhapar.Presenter.EventPresenter;
import com.madhapar.R;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.EventStatusListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusListActivity extends AppCompatActivity implements EventDetailCallback.EventStatusListCallback, EventDetailCallback.EventStatusCreateCallback, EventDetailCallback.EventStatusUpdateCallback {
    @BindView(R.id.rvEventStatusList)
    RecyclerView rvEventStatusList;
    @BindView(R.id.tvEventStatusTitle)
    TextView tvEventStatusTitle;
    @BindView(R.id.ivEventStautsChangeImage)
    ImageView ivEventStautsChangeImage;
    @BindView(R.id.ivEventStatusEmpty)
    ImageView ivEventStatusEmpty;

    private LinearLayoutManager mLayoutManager;
    public EventStatusListAdapter eventStatusListAdapter;
    @BindView(R.id.toolBarEventStatus)
    Toolbar toolBarEventStatus;
    private EventPresenter mPresenter;
    private JSONObject eventObj;

    @OnClick(R.id.ivEventStautsChangeImage)
    public void changeStatus() {
        if (UtilClass.isInternetAvailabel(this)) {
            if (mPresenter == null) {
                mPresenter = new EventPresenter();
            }
            mPresenter.openEventAlert(this, eventObj, getIntent().getStringExtra("evetStatus"), this, this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going);
        ButterKnife.bind(this);
        try {
            eventObj = new JSONObject(getIntent().getStringExtra("eventObj"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.setSupportActionBar(toolBarEventStatus);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().getStringExtra("evetStatus").equalsIgnoreCase(Constants.DifferentData.GoingStatus)) {
            tvEventStatusTitle.setText(getString(R.string.TitleGoing));
            if (eventObj.optString("isGoingId").equalsIgnoreCase("")) {
                ivEventStautsChangeImage.setImageResource(R.mipmap.ic_event_going_white);
            } else {
                ivEventStautsChangeImage.setVisibility(View.GONE);
            }
        } else if (getIntent().getStringExtra("evetStatus").equalsIgnoreCase(Constants.DifferentData.InterestedStatus)) {
            tvEventStatusTitle.setText(getString(R.string.Titlenterested));
            if (getIntent().getStringExtra("isInterestedId").equalsIgnoreCase("")) {
                ivEventStautsChangeImage.setImageResource(R.mipmap.ic_event_interested_white);
            } else {
                ivEventStautsChangeImage.setVisibility(View.GONE);
            }
        } else {
            tvEventStatusTitle.setText(getString(R.string.TitleCantGo));
            if (getIntent().getStringExtra("isMaybeId").equalsIgnoreCase("")) {
                ivEventStautsChangeImage.setImageResource(R.mipmap.ic_event_not_going_white);
            } else {
                ivEventStautsChangeImage.setVisibility(View.GONE);
            }
        }
        if (UtilClass.isInternetAvailabel(this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            if (mPresenter == null) {
                mPresenter = new EventPresenter();
            }
            mPresenter.getEventStatusList(eventObj.optString("eventId"), getIntent().getStringExtra("evetStatus"), this);
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
        UtilClass.hideProgress();
        Intent backIntent = new Intent();
        backIntent.putExtra("eventId", eventObj.optString("eventId"));
        setResult(RESULT_OK, backIntent);
        finish();
    }

    @Override
    public void onSuccessEventStatusList(JSONArray statusArray) {
        UtilClass.hideProgress();
        if (statusArray.length() > 0) {
            rvEventStatusList.setVisibility(View.VISIBLE);
            ivEventStatusEmpty.setVisibility(View.GONE);
            if (eventStatusListAdapter == null) {
                eventStatusListAdapter = new EventStatusListAdapter(this, statusArray);
                mLayoutManager = new LinearLayoutManager(this);
                rvEventStatusList.setLayoutManager(mLayoutManager);
                rvEventStatusList.setAdapter(eventStatusListAdapter);
            } else {
                eventStatusListAdapter.updateStatusAdaptet(statusArray);
            }

        } else {
            rvEventStatusList.setVisibility(View.GONE);
            ivEventStatusEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailEventStatusListRequest() {
        UtilClass.hideProgress();
        UtilClass.closeKeyboard(this);
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);

    }

    @Override
    public void onFailEventStatusResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.closeKeyboard(this);
        UtilClass.displyMessage(message, this, 0);
    }

    @Override
    public void onSuccessStautsCreate(JSONObject updateObj) {
        UtilClass.closeKeyboard(this);
        if (ivEventStautsChangeImage.getVisibility() == View.VISIBLE) {
            ivEventStautsChangeImage.setVisibility(View.GONE);
        } else {
            ivEventStautsChangeImage.setVisibility(View.VISIBLE);
        }
        if (UtilClass.isInternetAvailabel(this)) {
            if (mPresenter == null) {
                mPresenter = new EventPresenter();
            }
            mPresenter.getEventStatusList(eventObj.optString("eventId"), getIntent().getStringExtra("evetStatus"), this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
            UtilClass.hideProgress();
        }
    }

    @Override
    public void onFailStautsCreateRequest() {
        UtilClass.hideProgress();
        UtilClass.closeKeyboard(this);
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }

    @Override
    public void onFailStautsCreateRequest(String message) {
        UtilClass.hideProgress();
        UtilClass.closeKeyboard(this);
        UtilClass.displyMessage(message, this, 0);
    }

    @Override
    public void onSuccessStautsUpdate(JSONObject updateObj) {
        UtilClass.closeKeyboard(this);
        if (ivEventStautsChangeImage.getVisibility() == View.VISIBLE) {
            ivEventStautsChangeImage.setVisibility(View.GONE);
        } else {
            ivEventStautsChangeImage.setVisibility(View.VISIBLE);
        }
        if (UtilClass.isInternetAvailabel(this)) {
            if (mPresenter == null) {
                mPresenter = new EventPresenter();
            }
            mPresenter.getEventStatusList(eventObj.optString("eventId"), getIntent().getStringExtra("evetStatus"), this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
            UtilClass.hideProgress();
        }

    }

    @Override
    public void onFailStautsUpdateRequest() {
        UtilClass.hideProgress();
        UtilClass.closeKeyboard(this);
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }

    @Override
    public void onFailStautsUpdateRequest(String message) {
        UtilClass.hideProgress();
        UtilClass.closeKeyboard(this);
        UtilClass.displyMessage(message, this, 0);
    }

}
