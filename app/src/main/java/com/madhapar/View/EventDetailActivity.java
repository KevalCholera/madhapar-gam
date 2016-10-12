package com.madhapar.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.EventPresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventDetailActivity extends AppCompatActivity implements EventDetailCallback {
    @BindView(R.id.tvEventDetailTitle)
    TextView tvEventDetailTitle;
    @BindView(R.id.tvEventDate)
    TextView tvEventDate;
    @BindView(R.id.tvEventTime)
    TextView tvEventTime;
    @BindView(R.id.tvEventLocation)
    TextView tvEventLocation;
    @BindView(R.id.tEventDetailDescription)
    TextView tEventDetailDescription;
    @BindView(R.id.tvEventInfoGoingCount)
    TextView tvEventInfoGoingCount;
    @BindView(R.id.tvEventInfoInterestCount)
    TextView tvEventInfoInterestCount;
    @BindView(R.id.tvEventInfoNotInterestCount)
    TextView tvEventInfoNotInterestedCount;
    @BindView(R.id.tvEventOrganizer)
    TextView tvEventOrganizer;
    @BindView(R.id.llEventDetailGoing)
    LinearLayout llEventDetailGoing;

    @OnClick(R.id.llEventDetailGoing)
    public void going() {
        Intent goingIntent = new Intent(EventDetailActivity.this, StatusListActivity.class);
        goingIntent.putExtra("isGoingId", eventObj.optString("isGoingId"));
        goingIntent.putExtra("eventId", eventObj.optString("eventId").trim());
        goingIntent.putExtra("evetStatus", Constants.DifferentData.GoingStatus);
        startActivity(goingIntent);
    }

    @OnClick(R.id.llEventDetailIntrested)
    public void interested() {
        Intent goingIntent = new Intent(EventDetailActivity.this, StatusListActivity.class);
        goingIntent.putExtra("isInterestedId", eventObj.optString("isInterestedId"));
        goingIntent.putExtra("eventId", eventObj.optString("eventId").trim());
        goingIntent.putExtra("evetStatus", Constants.DifferentData.InterestedStatus);
        startActivity(goingIntent);
    }

    @OnClick(R.id.llEventDetailNotGoing)
    public void notGoing() {
        Intent goingIntent = new Intent(EventDetailActivity.this, StatusListActivity.class);
        goingIntent.putExtra("isMaybeId", eventObj.optString("isMaybeId"));
        goingIntent.putExtra("eventId", eventObj.optString("eventId").trim());
        goingIntent.putExtra("evetStatus", Constants.DifferentData.NotGoingStatus);
        startActivity(goingIntent);
    }


    JSONObject eventObj;
    EventPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenet_detail);
        ButterKnife.bind(this);

        if (getIntent().getStringExtra("event") != null) {
            try {
                eventObj = new JSONObject(getIntent().getStringExtra("event"));
                setUpView();
                setUpViewPager();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            getEventdetail("2");
        }
    }

    private void setUpViewPager() {

    }

    private void setUpView() {
        Log.e("eventObj", eventObj.toString());
        if (eventObj != null) {
            tvEventDetailTitle.setText(eventObj.optString("eventTitle"));
            tvEventDate.setText(eventObj.optString("eventFromDate"));
            tvEventLocation.setText(eventObj.optString("eventAddress"));
            tEventDetailDescription.setText(eventObj.optString("eventDescription"));
            tvEventOrganizer.setText(eventObj.optString("eventOrganizedBy"));
            tvEventInfoGoingCount.setText(eventObj.optString("going"));
            tvEventInfoInterestCount.setText(eventObj.optString("interested"));
            tvEventInfoNotInterestedCount.setText(eventObj.optString("cantGo"));
        }
    }

    private void getEventdetail(String eventId) {
        if (mPresenter == null) {
            mPresenter = new EventPresenter();
        }
        if (UtilClass.isInternetAvailabel(this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            mPresenter.getEventDetail(eventId, this);
        } else {
            UtilClass.hideProgress();
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }

    }


    @Override
    public void onSuccessEventDetail(JSONObject response) {
        this.eventObj = response.optJSONObject("response");
        UtilClass.hideProgress();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setUpView();
            }
        });
    }

    @Override
    public void onFailEventDetailRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }

    @Override
    public void onFailEventDetailResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, this, 0);
    }
}
