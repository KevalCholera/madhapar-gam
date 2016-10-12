package com.madhapar.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.EventPresenter;
import com.madhapar.Util.Constants;
import com.madhapar.View.Adapter.EventStatusListAdapter;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusListActivity extends AppCompatActivity implements EventDetailCallback.EventStatusListCallback {
    @BindView(R.id.rvEventStatusList)
    RecyclerView rvEventStatusList;
    @BindView(R.id.tvEventStatusTitle)
    TextView tvEventStatusTitle;
    @BindView(R.id.ivEventStautsChangeImage)
    ImageView ivEventStautsChangeImage;
    EventPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    public EventStatusListAdapter eventStatusListAdapter;
    @BindView(R.id.toolBarEventStatus)
    Toolbar toolBarEventStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going);
        ButterKnife.bind(this);
        super.setSupportActionBar(toolBarEventStatus);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().getStringExtra("evetStatus").equalsIgnoreCase(Constants.DifferentData.GoingStatus)) {
            tvEventStatusTitle.setText(getString(R.string.TitleGoing));
            if (getIntent().getStringExtra("isGoingId").equalsIgnoreCase("")) {
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
            tvEventStatusTitle.setText(getString(R.string.TitleNotGoing));
            if (getIntent().getStringExtra("isMaybeId").equalsIgnoreCase("")) {
                ivEventStautsChangeImage.setImageResource(R.mipmap.ic_event_not_going_white);
            } else {
                ivEventStautsChangeImage.setVisibility(View.GONE);
            }
        }

        mPresenter = new EventPresenter();
        mPresenter.getEventStatusList(getIntent().getStringExtra("eventId"), getIntent().getStringExtra("evetStatus"), this);
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
    public void onSuccessEventStatusList(JSONArray statusArray) {
        eventStatusListAdapter = new EventStatusListAdapter(this, statusArray);
        mLayoutManager = new LinearLayoutManager(this);
        rvEventStatusList.setLayoutManager(mLayoutManager);
        rvEventStatusList.setAdapter(eventStatusListAdapter);
    }

    @Override
    public void onFailEventStatusListRequest() {

    }

    @Override
    public void onFailEventStatusResponse(String message) {

    }
}
