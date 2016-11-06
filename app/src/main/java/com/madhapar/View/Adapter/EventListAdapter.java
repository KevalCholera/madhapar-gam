package com.madhapar.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.EventPresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.EventDetailActivity;
import com.madhapar.View.EventDetailCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
    private static final int REQUEST_CODE_FOR_EVENT_STATUS = 102;

    public EventListAdapter(Context context, JSONArray jsonArray, Activity activity) {
        this.eventArry = jsonArray;
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
            holder.tvNotInterest.setText(" " + eventObj.optString("cantGo") + " ");
            holder.tvTime.setText(getDisplayTime(eventObj));
            holder.tvGoing.setText(" " + eventObj.optString("going") + " ");
            holder.tvInterest.setText(" " + eventObj.optString("interested") + " ");
            if (isSelected(eventObj, Constants.DifferentData.GoingStatus)) {
                holder.llGoing.setBackgroundColor(context.getResources().getColor(R.color.colorEventSelected));
                holder.llGoing.setClickable(false);
            } else {
                holder.llGoing.setBackgroundColor(Color.WHITE);
                holder.llGoing.setClickable(true);
            }
            if (isSelected(eventObj, Constants.DifferentData.NotGoingStatus)) {
                holder.llNotGoing.setClickable(false);
                holder.llNotGoing.setBackgroundColor(context.getResources().getColor(R.color.colorEventSelected));
            } else {
                holder.llNotGoing.setClickable(true);
                holder.llNotGoing.setBackgroundColor(Color.WHITE);
            }
            if (isSelected(eventObj, Constants.DifferentData.InterestedStatus)) {
                holder.llInterested.setClickable(false);
                holder.llInterested.setBackgroundColor(context.getResources().getColor(R.color.colorEventSelected));
            } else {
                holder.llInterested.setClickable(true);
                holder.llInterested.setBackgroundColor(Color.WHITE);
            }
            holder.llEventDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    intent.putExtra("event", eventObj.toString());
                    intent.putExtra("canEventStatusChange", isEventStatusValid(eventObj));
                    activity.startActivityForResult(intent, REQUEST_CODE_FOR_EVENT_STATUS);

                }
            });
            holder.llGoing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (UtilClass.isInternetAvailabel(context)) {
                        if (!isSelected(eventObj, Constants.DifferentData.GoingStatus)) {
                            //  if (isEventStatusValid(eventObj)) {
                            if (mPresenter == null) {
                                mPresenter = new EventPresenter();
                            }
                            mPresenter.openEventAlert(activity, eventObj, Constants.DifferentData.GoingStatus, EventListAdapter.this, EventListAdapter.this);
//                            } else {
//                                UtilClass.displyMessage(context.getString(R.string.msgStatusCanNotUpdate), context, 0);
//                            }
                        }
                    } else {
                        UtilClass.displyMessage(context.getString(R.string.msgCheckInternet), context, 0);
                    }
                }
            });
            holder.llInterested.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (UtilClass.isInternetAvailabel(context)) {
                        if (!isSelected(eventObj, Constants.DifferentData.InterestedStatus)) {
//                            if (isEventStatusValid(eventObj)) {
                            if (mPresenter == null) {
                                mPresenter = new EventPresenter();
                            }
                            mPresenter.openEventAlert(activity, eventObj, Constants.DifferentData.InterestedStatus, EventListAdapter.this, EventListAdapter.this);
//                            } else {
//                                UtilClass.displyMessage(context.getString(R.string.msgStatusCanNotUpdate), context, 0);
//                            }
                        }
                    } else {
                        UtilClass.displyMessage(context.getString(R.string.msgCheckInternet), context, 0);
                    }
                }
            });
            holder.llNotGoing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (UtilClass.isInternetAvailabel(context)) {
                        if (!isSelected(eventObj, Constants.DifferentData.NotGoingStatus)) {
//                            if (isEventStatusValidForNotGoing(eventObj)) {
                            if (mPresenter == null) {
                                mPresenter = new EventPresenter();
                            }
                            mPresenter.openEventAlert(activity, eventObj, Constants.DifferentData.NotGoingStatus, EventListAdapter.this, EventListAdapter.this);
//                            } else {
//                                UtilClass.displyMessage(context.getString(R.string.msgStatusCanNotUpdateNotGoing), context, 0);
//                            }
                        }
                    } else {
                        UtilClass.displyMessage(context.getString(R.string.msgCheckInternet), context, 0);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getDisplayTime(JSONObject eventObj) {
        return getDates(eventObj.optString("eventFromDate")) + " - " + getDates(eventObj.optString("eventToDate"));

    }

    private String getDates(String fromDate) {
        return   fromDate.substring(0, 2) + "-" + fromDate.substring(3, 6) + "-" + fromDate.substring(8, 13);


    }


    @Override
    public int getItemCount() {
        return eventArry.length();
    }

    public void updateAdapter(JSONArray eventArray) {
        this.eventArry = eventArray;
        notifyDataSetChanged();
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
    }

    @Override
    public void onFailStautsCreateRequest(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, context, 0);

    }

    @Override
    public void onSuccessEventList(final JSONArray eventArray) {
        UtilClass.hideProgress();
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
    public void onFailStautsUpdateRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), context, 0);
    }

    @Override
    public void onFailStautsUpdateRequest(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, context, 0);
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

    private boolean isSelected(JSONObject eventObj, String statusType) {
        if (statusType.equalsIgnoreCase(Constants.DifferentData.GoingStatus)) {
            if (eventObj.optString("isGoingId") != null && !eventObj.optString("isGoingId").trim().equalsIgnoreCase("")) {
                return true;
            } else {
                return false;
            }
        } else if (statusType.equalsIgnoreCase(Constants.DifferentData.InterestedStatus)) {
            if (eventObj.optString("isInterestedId") != null && !eventObj.optString("isInterestedId").trim().equalsIgnoreCase("")) {
                return true;
            } else {
                return false;
            }
        } else {
            if (eventObj.optString("isMaybeId") != null && !eventObj.optString("isMaybeId").trim().equalsIgnoreCase("")) {
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean isEventStatusValid(JSONObject eventObj) {
        boolean isValid = false;
        try {
            String fromDate = eventObj.optString("eventFromDate");

            String finalDate = fromDate.substring(0, 2) + "-" + fromDate.substring(3, 6) + "-" + fromDate.substring(8, 13) + fromDate.substring(16, 18) + ":" + fromDate.substring(19, 21) + ":" + "00" + " " + fromDate.substring(22);
            Date date = sdf.parse(finalDate);
            long Eventtime = date.getTime();
            long curruntTime = System.currentTimeMillis();
            if ((Eventtime - curruntTime) > (3 * 24 * 60 * 60 * 1000)) {
                isValid = true;
            } else {
                isValid = false;
            }
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return isValid;
    }

    private boolean isEventStatusValidForNotGoing(JSONObject eventObject) {
        boolean isValid = false;
        try {
            String fromDate = eventObject.optString("eventToDate");
            String finalDate = fromDate.substring(0, 2) + "-" + fromDate.substring(3, 6) + "-" + fromDate.substring(8, 13) + fromDate.substring(16, 18) + ":" + fromDate.substring(19, 21) + " " + fromDate.substring(22);
            Date date = sdf.parse(finalDate);
            long EventLasttime = date.getTime();
            long curruntTime = System.currentTimeMillis();
            if ((EventLasttime - curruntTime) > (1 * 24 * 60 * 60 * 1000)) {
                isValid = true;
            } else {
                isValid = false;
            }
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return isValid;
    }
}
