package com.madhapar.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.ProfileUpdatePresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.EventDetailActivity;
import com.madhapar.View.LoginActivity;
import com.madhapar.View.ProfileEditActivity;
import com.madhapar.View.ProfileUpdateCallback;
import com.mpt.storage.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * Created by smartsense on 24/09/16.
 */

public class UserFragment extends BaseFragment implements ProfileUpdateCallback {
    @BindView(R.id.etProfileEditProfileFirstName)
    EditText etProfileFirstName;
    private ProfileUpdatePresenter mProfileUpdateListener;

    @OnFocusChange(R.id.etProfileEditProfileFirstName)
    void updateFirstName() {
        if (!etProfileFirstName.hasFocus()) {
            if (!etProfileFirstName.getTag().toString().equalsIgnoreCase(etProfileFirstName.getText().toString())) {
                if (mProfileUpdateListener == null) {
                    mProfileUpdateListener = new ProfileUpdatePresenter();
                }
                Map<String, String> params = new HashMap();
                if (TextUtils.isEmpty(etProfileFirstName.getText().toString().trim())) {
                    UtilClass.displyMessage("First name is required", getActivity(), 0);
                } else {
                    params.put("userFirstName", etProfileFirstName.getText().toString());
                    mProfileUpdateListener.updateUserFirstName(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), this);
                }

            }
        }
    }

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
        ButterKnife.bind(this, view);
        etProfileFirstName.setText(SharedPreferenceUtil.getString(Constants.UserData.UserFirstName, ""));
        etProfileFirstName.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserFirstName, ""));
        tvProfileBloodGroup.setText(SharedPreferenceUtil.getString(Constants.UserData.UserBloodGroup, ""));
        etProfileLastName.setText(SharedPreferenceUtil.getString(Constants.UserData.UserLastName, ""));
        tvProfileDOB.setText(SharedPreferenceUtil.getString(Constants.UserData.UserDOB, ""));
        tvProfileLocation.setText(SharedPreferenceUtil.getString(Constants.UserData.UserLocationName, ""));
        tvProfileMobileNumber.setText(SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo, ""));
        tvProfileEmail.setText(SharedPreferenceUtil.getString(Constants.UserData.UserEmail, ""));
        tvProfileFacebookId.setText(SharedPreferenceUtil.getString(Constants.UserData.UserFBProfileName, ""));
        presenter = new PresenterClass();
        return view;
    }

    @OnClick(R.id.ivEditIcon)
    public void editProfile() {
        presenter.changeActivity(getActivity(), ProfileEditActivity.class, true);

    }

    @Override
    public void onSuccessUpdateUserData(String name) {

    }

    @Override
    public void onFailUpdateUesrDate(String message) {

    }

    @Override
    public void onFailUpdateRequest() {

    }
}
