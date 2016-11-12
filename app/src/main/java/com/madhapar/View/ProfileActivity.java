package com.madhapar.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.R;
import com.madhapar.Util.Constants;
import com.mpt.storage.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.tvProfileName)
    TextView tvProfileName;
    @BindView(R.id.tvProfileMobileNumber)
    TextView tvProfileMobileNumber;
    @BindView(R.id.tvProfileLocation)
    TextView tvProfileLocation;
    @BindView(R.id.tvProfileDOB)
    TextView tvProfileDOB;
    @BindView(R.id.tvProfileBloodGroup)
    TextView tvProfileBloodGroup;
    @BindView(R.id.tvMembershipNo)
    TextView tvMemberShipNumber;
    @BindView(R.id.tvProfileEmail)
    TextView tvProfileEmail;
    @BindView(R.id.tvProfileFacebbokId)
    TextView tvProfileFacebookId;
    @BindView(R.id.ivProfilePhoto)
    ImageView ivProfilePhoto;
    RequestPresenter presenterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvProfileName.setText(SharedPreferenceUtil.getString(Constants.UserData.UserFirstName, Constants.RequestConstants.UserListUrl));
        tvProfileBloodGroup.setText(SharedPreferenceUtil.getString(Constants.UserData.UserBloodGroup, Constants.RequestConstants.UserListUrl));
        tvProfileDOB.setText(SharedPreferenceUtil.getString(Constants.UserData.UserDOB, Constants.RequestConstants.UserListUrl));
        tvProfileLocation.setText(SharedPreferenceUtil.getString(Constants.UserData.UserLocationName, Constants.RequestConstants.UserListUrl));
        tvProfileMobileNumber.setText(SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo, Constants.RequestConstants.UserListUrl));
        tvProfileEmail.setText(SharedPreferenceUtil.getString(Constants.UserData.UserEmail, Constants.RequestConstants.UserListUrl));
        tvProfileFacebookId.setText(SharedPreferenceUtil.getString(Constants.UserData.UserFBProfileName, Constants.RequestConstants.UserListUrl));

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

    }
}
