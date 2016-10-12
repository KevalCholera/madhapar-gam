package com.madhapar.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.EventPresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.EventDetailActivity;
import com.madhapar.View.EventDetailCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 30/09/16.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> implements EventDetailCallback.EventStatusCreateCallback, EventDetailCallback.EventStatusUpdateCallback, EventDetailCallback.EventListCallback {
    private JSONArray eventArry;
    private Context context;
    private EventPresenter mPresenter;
    private Activity activity;


    public EventListAdapter(Context context, JSONArray jsonArray, Activity activity) {
        this.eventArry = jsonArray;
        Log.e("crete", "list" + jsonArray.length());
        this.context = context;
        this.activity = activity;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(context).inflate(R.layout.element_event_list, parent, false);
        ButterKnife.bind(this, eventView);
        return new MyViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            final JSONObject eventObj = eventArry.getJSONObject(position);
            holder.tvEventName.setText(eventObj.optString("eventTitle"));
            holder.tvAddress.setText(eventObj.optString("eventAddress"));
            holder.tvNotInterest.setText(eventObj.optString("cantGo"));
            holder.tvTime.setText(eventObj.optString("eventFromDate"));
            holder.tvGoing.setText(eventObj.optString("going"));
            holder.tvInterest.setText(eventObj.optString("interested"));
            holder.llEventDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    intent.putExtra("event", eventObj.toString());
                    context.startActivity(intent);

                }
            });
            holder.llGoing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPresenter == null) {
                        mPresenter = new EventPresenter();
                    }
                    if (UtilClass.isInternetAvailabel(context)) {
                        UtilClass.showProgress(context, context.getString(R.string.msgPleaseWait));
                        if (canCreateNewStatus(eventObj)) {
                            mPresenter.createEventStatus(eventObj.optString("eventId"), Constants.DifferentData.GoingStatus, "10", EventListAdapter.this);
                        } else {
                            mPresenter.updateEventStatus(eventObj.optString("eventId"), Constants.DifferentData.GoingStatus, "5", EventListAdapter.this);
                        }
                    } else {
                        UtilClass.hideProgress();
                        UtilClass.displyMessage(context.getString(R.string.msgCheckInternet), context, 0);
                    }
                }
            });
            holder.llInterested.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mPresenter == null) {
                        mPresenter = new EventPresenter();
                    }
                    if (UtilClass.isInternetAvailabel(context)) {
                        UtilClass.showProgress(context, context.getString(R.string.msgPleaseWait));
                        if (canCreateNewStatus(eventObj)) {
                            mPresenter.createEventStatus(eventObj.optString("eventId"), Constants.DifferentData.InterestedStatus, "", EventListAdapter.this);
                        } else {
                            mPresenter.updateEventStatus(eventObj.optString("eventId"), Constants.DifferentData.InterestedStatus, "", EventListAdapter.this);
                        }

                    } else {
                        UtilClass.hideProgress();
                        UtilClass.displyMessage(context.getString(R.string.msgCheckInternet), context, 0);
                    }
                }

            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return eventArry.length();
    }

    public void updateAdapter(JSONArray eventArray) {
        this.eventArry = eventArray;
        notifyDataSetChanged();
        Log.e("event", "update adater called");
    }

    @Override
    public void onSuccessStautsCreate(JSONObject updateObj) {
        if (UtilClass.isInternetAvailabel(context)) {
            if (mPresenter == null) {
                mPresenter = new EventPresenter();
            }
            mPresenter.getEventList(this);
        } else {
            UtilClass.hideProgress();
            UtilClass.displyMessage(context.getString(R.string.msgCheckInternet), context, 0);
        }
    }

    @Override
    public void onFailStautsCreateRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), context, 0);
        Log.e("eventSuccess", "eventObj Fail Request");
    }

    @Override
    public void onFailStautsCreateRequest(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, context, 0);
        Log.e("eventSuccess", "eventObj" + message);

    }

    @Override
    public void onSuccessEventList(final JSONArray eventArray) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateAdapter(eventArray);
            }
        });

    }

    @Override
    public void onFailEventListRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), context, 0);
    }

    @Override
    public void onFailEventListResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, context, 0);

    }

    @Override
    public void onSuccessStautsUpdate(JSONObject updateObj) {

    }

    @Override
    public void onFailStautsUpdateRequest() {

    }

    @Override
    public void onFailStautsUpdateRequest(String message) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvEventName)
        TextView tvEventName;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvGoingCount)
        TextView tvGoing;
        @BindView(R.id.tvInterestCount)
        TextView tvInterest;
        @BindView(R.id.tvNotInterestCount)
        TextView tvNotInterest;
        @BindView(R.id.llEventDetail)
        LinearLayout llEventDetail;
        @BindView(R.id.llGoing)
        LinearLayout llGoing;
        @BindView(R.id.llInterested)
        LinearLayout llInterested;
        @BindView(R.id.llNotGoing)
        LinearLayout llNotGoing;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    boolean canCreateNewStatus(JSONObject eventObj) {
        if (eventObj.optString("isGoingId").equalsIgnoreCase("") && eventObj.optString("isInterestedId").equalsIgnoreCase("") && eventObj.optString("isMaybeId").equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }


}
