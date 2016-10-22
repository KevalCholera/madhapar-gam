package com.madhapar.View;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.ProfileUpdatePresenter;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.LocationsListAdapter;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class LocationsActivity extends AppCompatActivity implements ProfileUpdateCallback {

    @BindView(R.id.rvLocationList)
    RecyclerView rvLocationList;
    @BindView(R.id.etLocationSearch)
    EditText etLocationSearch;
    @BindView(R.id.ivSearchHint)
    ImageView ivSearchHint;

    @OnTextChanged(R.id.etLocationSearch)
    void searchFocus() {
        if (etLocationSearch.getText().toString().length() > 0) {
            ivSearchHint.setVisibility(View.GONE);
        } else {
            ivSearchHint.setVisibility(View.VISIBLE);
        }
        if (mLocationAdapter != null) {
            mLocationAdapter.getFilter().filter(etLocationSearch.getText().toString());
        }
    }

    private ProfileUpdatePresenter mProfilePresenter;
    private LocationsListAdapter mLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mProfilePresenter = new ProfileUpdatePresenter();
        if (UtilClass.isInternetAvailabel(this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            mProfilePresenter.getLocationList(this);
        } else {
            UtilClass.hideProgress();
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }

    }

    @Override
    public void onSuccessUpdateUserData(String name) {

    }

    @Override
    public void onFailUpdateUesrDate(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, this, 0);

    }

    @Override
    public void onFailUpdateRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);

    }

    @Override
    public void onSuccessLocationList(JSONArray locationList) {
        UtilClass.hideProgress();
        if (locationList != null) {
            if (mLocationAdapter == null) {
                mLocationAdapter = new LocationsListAdapter(this, locationList);
                rvLocationList.setAdapter(mLocationAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                rvLocationList.setLayoutManager(layoutManager);
            } else {
                mLocationAdapter.updateLocationList(locationList);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.action_add_location) {
            if (mProfilePresenter == null) {
                mProfilePresenter = new ProfileUpdatePresenter();
            }
            mProfilePresenter.addNewLocationAlert(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location, menu);
        MenuItem menuItem = menu.findItem(R.id.action_add_location);
        menuItem.setVisible(true);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("isSelected", false);
        setResult(RESULT_OK, intent);
        finish();
    }
}
