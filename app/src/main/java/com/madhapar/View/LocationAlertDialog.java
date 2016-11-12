package com.madhapar.View;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.madhapar.Presenter.ProfileUpdatePresenter;
import com.madhapar.R;
import com.madhapar.Util.UtilClass;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smartsense on 22/10/16.
 */
public class LocationAlertDialog extends AlertDialog.Builder implements ProfileUpdateCallback {
    private AlertDialog dialog;
    private AppCompatActivity activity;
    private ProfileUpdatePresenter mProfileUpdatePresenter;
    @BindView(R.id.tvDoneLocation)
    TextView tvDoneLocation;
    @BindView(R.id.tvCancelLocation)
    TextView tvCancelLocation;

    @OnClick(R.id.tvCancelLocation)
    void dismissDialog() {
        UtilClass.closeKeyboard(activity);
        dialog.dismiss();
    }


    @OnClick(R.id.tvDoneLocation)
    void addLocation() {
        UtilClass.closeKeyboard(activity);
        if (UtilClass.isInternetAvailabel(activity)) {
            if (mProfileUpdatePresenter == null) {
                mProfileUpdatePresenter = new ProfileUpdatePresenter();
            }
            if (etLocationAdd.getText().toString().trim().length() > 0) {
                mProfileUpdatePresenter.createNewLocation(etLocationAdd.getText().toString(), this);
            } else {
                UtilClass.displyMessage(activity.getString(R.string.locationRequiredError), activity, 0);
            }
        } else {
            UtilClass.displyMessage(activity.getString(R.string.msgCheckInternet), activity, 0);
        }


    }

    @BindView(R.id.etLocationAdd)
    EditText etLocationAdd;

    public LocationAlertDialog(Context context) {
        super(context);
    }

    public void openLocationDialog(AppCompatActivity activity) {
        this.activity = activity;
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.alert_location, null, false);
        this.setView(view);
        ButterKnife.bind(this, view);
        dialog = create();
        dialog.show();
        etLocationAdd.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.alertdialogdesign);
    }

    @Override
    public void onSuccessUpdateUserData(String name) {
        dialog.dismiss();
        UtilClass.showProgress(activity, activity.getString(R.string.msgPleaseWait));
        mProfileUpdatePresenter.getLocationList(this);
    }

    @Override
    public void onFailUpdateUesrDate(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, activity, 0);
    }

    @Override
    public void onFailUpdateRequest() {
        UtilClass.hideProgress();
        dialog.dismiss();
        UtilClass.displyMessage(activity.getString(R.string.msgSomethigWentWrong), activity, 0);
    }

    @Override
    public void onSuccessLocationList(JSONArray jsonArray) {
        if (activity instanceof LocationsActivity) {
            ((LocationsActivity) activity).onSuccessLocationList(jsonArray);
        }
    }
}
