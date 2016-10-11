package com.madhapar.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClassSecond;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventInfoActivity extends AppCompatActivity {
//    @BindView(R.id.tvEventInfoTitle)
//    TextView tvEventInfoTitle;
    @BindView(R.id.tvEventInfoDtaeTime)
    TextView tvEventInfoDateTime;
    @BindView(R.id.tvEventInfoAddress)
    TextView tvEventInfoAddress;
    @BindView(R.id.tvEventDescription)
    TextView tvEventInfoDescription;
    @BindView(R.id.tvEventInfoGoingCount)
    TextView tvEventInfoGoingCount;
    @BindView(R.id.tvEventInfoInterestCount)
    TextView tvEventInfoInterestCount;
    @BindView(R.id.tvEventInfoNotInterestCount)
    TextView tvEventInfoNotInterestedCount;
    public JSONArray eventInfoArry;
    private Context context;
    PresenterClassSecond presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        ButterKnife.bind(this);
        presenter=new PresenterClassSecond();
        JSONObject eventInfo=presenter.getEventIndo();
        if(eventInfo!=null);
        Log.i("Event Title",eventInfo.optString("EventInfoName"));
    }

}
