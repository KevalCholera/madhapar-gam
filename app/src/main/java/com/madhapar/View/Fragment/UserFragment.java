package com.madhapar.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Util.Constants;
import com.madhapar.View.EventDetailActivity;
import com.madhapar.View.LoginActivity;
import com.madhapar.View.ProfileEditActivity;
import com.mpt.storage.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smartsense on 24/09/16.
 */

public class UserFragment extends BaseFragment {
    @BindView(R.id.etProfileEditProfileName)
    EditText tvProfileName;
    @BindView(R.id.etProfileMobileEditProfileNumber)
    EditText tvProfileMobileNumber;
    @BindView(R.id.etEditProfileLocation)
    EditText tvProfileLocation;
    @BindView(R.id.etEditProfileDOB)
    EditText tvProfileDOB;
    @BindView(R.id.etEditProfileBloodGroup)
    EditText tvProfileBloodGroup;
    @BindView(R.id.etEditMembershipNo)
    EditText tvMemberShipNumber;
    @BindView(R.id.etEditProfileEmail)
    EditText tvProfileEmail;
    @BindView(R.id.etEditProfileFacebbokId)
    EditText tvProfileFacebookId;
    @BindView(R.id.etProfileMobileEditLastName)
    EditText etProfileLastName;
    @BindView(R.id.ivProfilePhoto)
    ImageView ivProfilePhoto;
    private PresenterClass presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this,view);
        Log.e("log","here"+tvProfileLocation.getText().toString());
        tvProfileName.setText(SharedPreferenceUtil.getString(Constants.UserData.UserFirstName,Constants.RequestConstants.UserListUrl));
        tvProfileBloodGroup.setText(SharedPreferenceUtil.getString(Constants.UserData.UserBloodGroup,Constants.RequestConstants.UserListUrl));
        etProfileLastName.setText(SharedPreferenceUtil.getString(Constants.UserData.UserLastName,Constants.RequestConstants.UserListUrl));
        tvProfileDOB.setText(SharedPreferenceUtil.getString(Constants.UserData.UserDOB,Constants.RequestConstants.UserListUrl));
        tvProfileLocation.setText(SharedPreferenceUtil.getString(Constants.UserData.UserLocationName,Constants.RequestConstants.UserListUrl));
        Log.e("log","here"+SharedPreferenceUtil.getString(Constants.UserData.UserLocationName,Constants.RequestConstants.UserListUrl));
        tvProfileMobileNumber.setText(SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo,Constants.RequestConstants.UserListUrl));
        tvProfileEmail.setText(SharedPreferenceUtil.getString(Constants.UserData.UserEmail,Constants.RequestConstants.UserListUrl));
        tvProfileFacebookId.setText(SharedPreferenceUtil.getString(Constants.UserData.UserFBProfileName,Constants.RequestConstants.UserListUrl));
        presenter = new PresenterClass();
        return view;
    }
    @OnClick(R.id.ivEditIcon)
    public void editProfile(){
        presenter.changeActivity(getActivity(), ProfileEditActivity.class, true);

    }
}
