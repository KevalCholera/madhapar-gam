package com.madhapar.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.EventPresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smartsense on 13/10/16.
 */

public class EventStatusAlertDialog extends AlertDialog.Builder {
    private EventPresenter mPresenter;
    private JSONObject eventObj;
    private Activity activity;
    private EventDetailCallback.EventStatusUpdateCallback updateCallback;
    private EventDetailCallback.EventStatusCreateCallback createCallback;
    private String evetnStatusType;
    private AlertDialog alertDialog;
    @BindView(R.id.tvStatusDialogMessage)
    TextView tvStatusDialogMessage;
    @BindView(R.id.tvStatusDialogYes)
    TextView tvStatusDialogYes;
    @BindView(R.id.tvStatusDialogNo)
    TextView tvStatusDialogNo;
    @BindView(R.id.llAlertCancelConform)
    LinearLayout llAlertCancelConform;
    @BindView(R.id.llAlertYesNo)
    LinearLayout llAlertYesNo;
    @BindView(R.id.tvStatusDialogCancel)
    TextView tvStatusDialogCancel;
    @BindView(R.id.tvStatusDialogConfirm)
    TextView tvStatusDialogConfirm;
    @BindView(R.id.etStatusDialogCount)
    EditText etStatusDialogCount;

    @OnClick(R.id.tvStatusDialogConfirm)
    public void changeStatusGoingCount() {
        try {
            if (isValidFamilyMember(etStatusDialogCount.getText().toString())) {

                if (canCreateNewStatus(eventObj).equalsIgnoreCase("not")) {
                    mPresenter.createEventStatus(eventObj.optString("eventId"), evetnStatusType, etStatusDialogCount.getText().toString().trim(), createCallback);
                } else {
                    mPresenter.updateEventStatus(canCreateNewStatus(eventObj), evetnStatusType, etStatusDialogCount.getText().toString().trim(), updateCallback);
                }
                alertDialog.cancel();
            } else {
                UtilClass.displyMessage(activity.getString(R.string.msgEnterFamilyMember), activity, 0);
            }
        } catch (Exception e) {
            UtilClass.displyMessage(activity.getString(R.string.msgEnterFamilyMember), activity, 0);
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tvStatusDialogCancel)
    public void cancelGoingCount() {
        alertDialog.cancel();
    }


    @OnClick(R.id.tvStatusDialogYes)
    public void changeStatus() {
        if (evetnStatusType.equalsIgnoreCase(Constants.DifferentData.GoingStatus)) {
            llAlertYesNo.setVisibility(View.GONE);
            llAlertCancelConform.setVisibility(View.VISIBLE);
            tvStatusDialogMessage.setText(activity.getString(R.string.EventAlertFaimlyMemberMessage));
            etStatusDialogCount.setVisibility(View.VISIBLE);
            etStatusDialogCount.requestFocus();
        } else {
            if (canCreateNewStatus(eventObj).equalsIgnoreCase("not")) {
                mPresenter.createEventStatus(eventObj.optString("eventId"), evetnStatusType, "10", createCallback);
            } else {
                mPresenter.updateEventStatus(canCreateNewStatus(eventObj), evetnStatusType, "5", updateCallback);
            }
            alertDialog.dismiss();
        }
    }

    @OnClick(R.id.tvStatusDialogNo)
    public void cancel() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog.cancel();
        }
    }


    public EventStatusAlertDialog(Activity activity, JSONObject eventObj, String statusType, EventDetailCallback.EventStatusCreateCallback createCallback, EventDetailCallback.EventStatusUpdateCallback updateCallback) {
        super(activity);
        this.activity = activity;
        this.eventObj = eventObj;
        this.evetnStatusType = statusType;
        this.createCallback = createCallback;
        this.updateCallback = updateCallback;
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.alert_event_status, null, false);
        this.setView(view);
        ButterKnife.bind(this, view);
        alertDialog = create();
        alertDialog.show();
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.alert_dialog_white_border);
        if (mPresenter == null) {
            mPresenter = new EventPresenter();
        }
        if (statusType.equalsIgnoreCase(Constants.DifferentData.GoingStatus)) {
            tvStatusDialogMessage.setText(activity.getString(R.string.EventAlertGoingMessage));
        } else if (statusType.equalsIgnoreCase(Constants.DifferentData.InterestedStatus)) {
            tvStatusDialogMessage.setText(activity.getString(R.string.EventAlertInterestMessage));
        } else {
            tvStatusDialogMessage.setText(activity.getString(R.string.EventAlertNotGoingMessage));
        }

    }


    private String canCreateNewStatus(JSONObject eventObj) {
        if (eventObj.optString("isGoingId").equalsIgnoreCase("") && eventObj.optString("isInterestedId").equalsIgnoreCase("") && eventObj.optString("isMaybeId").equalsIgnoreCase("")) {
            return "not";
        } else if (eventObj.optString("isGoingId") != null && !eventObj.optString("isGoingId").trim().equalsIgnoreCase("")) {
            return eventObj.optString("isGoingId");
        } else if (eventObj.optString("isInterestedId") != null && !eventObj.optString("isInterestedId").trim().equalsIgnoreCase("")) {
            return eventObj.optString("isInterestedId");
        } else {
            return eventObj.optString("isMaybeId");
        }

    }


    private boolean isValidFamilyMember(String familyMember) {
        if (TextUtils.isEmpty(familyMember)) {
            return false;
        } else {
            try {
                Long value = Long.valueOf(familyMember);
                if (value < 100) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    }


}
