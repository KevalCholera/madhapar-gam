package com.madhapar.View.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.smartsense.newproject.R;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.madhapar.Application.MadhaparGamApp;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.ProfileUpdatePresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.MultiPartRequest;
import com.madhapar.Util.MultiPartRequestJson;
import com.madhapar.Util.MultipartUtility;
import com.madhapar.Util.UtilClass;
import com.madhapar.Util.WebServiceUtil;
import com.madhapar.View.LocationsActivity;
import com.madhapar.View.ProfileUpdateCallback;
import com.madhapar.View.UploadInterface;
import com.mpt.storage.SharedPreferenceUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.mime.HttpMultipart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.qqtheme.framework.picker.OptionPicker;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by smartsense on 24/09/16.
 */

public class UserFragment extends BaseFragment implements ProfileUpdateCallback, UploadInterface {
    @BindView(R.id.etProfileEditProfileFirstName)
    EditText etProfileFirstName;
    private ProfileUpdatePresenter mProfileUpdatePresenter;
    private com.jzxiang.pickerview.TimePickerDialog mDialogAll;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM,yyyy");
    private Calendar calendar = Calendar.getInstance();
    private int selectedPosition;
    private static final int REQUEST_CONSTANT = 200;
    private int locationId = 0;
    private int whichSelect = 0;

    private int REQUEST_CAMERA = 102;
    private int SELECT_FILE = 101;
    private int PERMISSION_REQUEST_CODE = 103;
    private Activity activity;
    @BindView(R.id.ivProfilePhotoSmall)
    de.hdodenhof.circleimageview.CircleImageView ivProfilePhotoSmall;
    @BindView(R.id.ivProfilePhoto)
    ImageView ivProfilePhoto;
    @BindView(R.id.ivEditIcon)
    ImageView ivEditIcon;

    @OnClick(R.id.ivEditIcon)
    void updateProfilePic() {
        updatePhoto();
    }

    @OnFocusChange(R.id.etProfileEditProfileFirstName)
    void updateFirstName() {
        UtilClass.closeKeyboard(getActivity());
        if (!etProfileFirstName.hasFocus()) {
            if (!etProfileFirstName.getTag().toString().equalsIgnoreCase(etProfileFirstName.getText().toString())) {
                if (mProfileUpdatePresenter == null) {
                    mProfileUpdatePresenter = new ProfileUpdatePresenter();
                }
                Map<String, String> params = new HashMap();
                if (!TextUtils.isEmpty(etProfileFirstName.getText().toString().trim())) {
                    etProfileFirstName.setTag(etProfileFirstName.getText().toString());
                    params.put("userFirstName", etProfileFirstName.getText().toString());
                    mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), this);
                }
            }
        } else {
            boolean isAnyEmpty = changeFocusIfEmpty(etProfileFirstName);
            if (!isAnyEmpty) {
                etProfileFirstName.setCursorVisible(true);
            }
        }
    }

    @BindView(R.id.etProfileEditLastName)
    EditText etProfileLastName;

    @OnFocusChange(R.id.etProfileEditLastName)
    void UpdateLastName() {
        UtilClass.closeKeyboard(getActivity());
        if (!etProfileLastName.hasFocus()) {
            if (!etProfileLastName.getTag().toString().equalsIgnoreCase(etProfileLastName.getText().toString())) {
                if (mProfileUpdatePresenter == null) {
                    mProfileUpdatePresenter = new ProfileUpdatePresenter();
                }
                Map<String, String> params = new HashMap();
                if (!TextUtils.isEmpty(etProfileLastName.getText().toString().trim())) {
                    params.put("userLastName", etProfileLastName.getText().toString());
                    etProfileLastName.setTag(etProfileLastName.getText().toString());
                    mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), this);
                }

            }
        } else {
            boolean isAnyEmpty = changeFocusIfEmpty(etProfileLastName);
            if (!isAnyEmpty) {
                etProfileLastName.setCursorVisible(true);
            }

        }
    }

    @BindView(R.id.etProfileMobileNumber)
    EditText etProfileMobileNumber;

    @OnFocusChange(R.id.etProfileMobileNumber)
    void updateMobileNumber() {
        UtilClass.closeKeyboard(getActivity());
        if (!etProfileMobileNumber.hasFocus()) {
            if (!etProfileMobileNumber.getTag().toString().equalsIgnoreCase(etProfileMobileNumber.getText().toString())) {
                if (mProfileUpdatePresenter == null) {
                    mProfileUpdatePresenter = new ProfileUpdatePresenter();
                }
                Map<String, String> params = new HashMap();
                if (!TextUtils.isEmpty(etProfileMobileNumber.getText().toString().trim())) {
                    etProfileMobileNumber.setTag(etProfileMobileNumber.getText().toString());
                    params.put("userMobileNo", etProfileMobileNumber.getText().toString());
                    mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), this);
                }
            }
        } else {
            boolean isAnyEmpty = changeFocusIfEmpty(etProfileMobileNumber);
            if (!isAnyEmpty) {
                etProfileMobileNumber.setCursorVisible(true);
            }
        }
    }

    @BindView(R.id.etEditProfileFacebbokId)
    EditText etEditProfileFacebbokId;

    @OnFocusChange(R.id.etEditProfileFacebbokId)
    void updateFacebookId() {
        UtilClass.closeKeyboard(getActivity());
        if (!etEditProfileFacebbokId.hasFocus()) {
            if (!etEditProfileFacebbokId.getTag().toString().equalsIgnoreCase(etEditProfileFacebbokId.getText().toString())) {
                if (mProfileUpdatePresenter == null) {
                    mProfileUpdatePresenter = new ProfileUpdatePresenter();
                }
                Map<String, String> params = new HashMap();
                if (!TextUtils.isEmpty(etEditProfileFacebbokId.getText().toString().trim())) {
                    etEditProfileFacebbokId.setTag(etEditProfileFacebbokId.getText().toString());
                    params.put("userFBProfileName", etEditProfileFacebbokId.getText().toString());
                    mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), this);
                }
            }
        } else {
            boolean isAnyEmpty = changeFocusIfEmpty(etEditProfileFacebbokId);
            if (!isAnyEmpty) {
                etEditProfileFacebbokId.setCursorVisible(true);
            }
        }
    }

    @BindView(R.id.etEditProfileEmail)
    EditText etEditProfileEmail;

    @OnFocusChange(R.id.etEditProfileEmail)
    void updateEmail() {
        UtilClass.closeKeyboard(getActivity());
        if (!etEditProfileEmail.hasFocus()) {
            if (!etEditProfileEmail.getTag().toString().equalsIgnoreCase(etEditProfileEmail.getText().toString())) {
                if (mProfileUpdatePresenter == null) {
                    mProfileUpdatePresenter = new ProfileUpdatePresenter();
                }
                Map<String, String> params = new HashMap();
                etEditProfileEmail.setTag(etEditProfileEmail.getText().toString());
                params.put("email", etEditProfileEmail.getText().toString());
                mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), this);
            }
        } else {
            boolean isAnyEmpty = changeFocusIfEmpty(etEditProfileEmail);
            if (!isAnyEmpty) {
                etEditProfileEmail.setCursorVisible(true);
            }
        }
    }

    @BindView(R.id.etEditProfileBloodGroup)
    EditText etEditProfileBloodGroup;

    @OnFocusChange(R.id.etEditProfileBloodGroup)
    void updateBloodGroup() {

        if (!etEditProfileBloodGroup.hasFocus()) {
            if (!etEditProfileBloodGroup.getTag().toString().equalsIgnoreCase(etEditProfileBloodGroup.getText().toString())) {
                if (mProfileUpdatePresenter == null) {
                    mProfileUpdatePresenter = new ProfileUpdatePresenter();
                }
                Map<String, String> params = new HashMap();
                if (!TextUtils.isEmpty(etEditProfileBloodGroup.getText().toString().trim())) {
                    etEditProfileBloodGroup.setTag(etEditProfileBloodGroup.getText().toString());
                    params.put("userBloodGroup", etEditProfileBloodGroup.getText().toString());
                    mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), this);
                }
            }
        } else {
            boolean isAnyEmpty = changeFocusIfEmpty(etEditProfileBloodGroup);
            if (!isAnyEmpty) {
                etEditProfileBloodGroup.setCursorVisible(false);
                openBloodGroupSelector();
            }
        }
    }

    @BindView(R.id.etEditProfileDOB)
    EditText etEditProfileDOB;

    @OnFocusChange(R.id.etEditProfileDOB)
    void updateDateDateOfBirth() {
        if (!etEditProfileDOB.hasFocus()) {
            if (!etEditProfileDOB.getTag().toString().equalsIgnoreCase(etEditProfileDOB.getText().toString())) {
                if (mProfileUpdatePresenter == null) {
                    mProfileUpdatePresenter = new ProfileUpdatePresenter();
                }
                Map<String, String> params = new HashMap();
                if (!TextUtils.isEmpty(etEditProfileDOB.getText().toString().trim())) {
                    etEditProfileDOB.setTag(etEditProfileDOB.getText().toString());

                    params.put("userDOB", getDateOfBirthToSend(etEditProfileDOB.getText().toString()));
                    mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), this);
                }
            }
        } else {
            boolean isAnyEmpty = changeFocusIfEmpty(etEditProfileDOB);
            if (!isAnyEmpty) {
                etEditProfileDOB.setCursorVisible(false);
                openDateSelector();
            }
        }

    }


    @BindView(R.id.etEditProfileLocation)
    EditText etEditProfileLocation;

    @OnFocusChange(R.id.etEditProfileLocation)
    void updateCity() {

        if (!etEditProfileLocation.hasFocus()) {
            if (!etEditProfileLocation.getTag().toString().equalsIgnoreCase(etEditProfileLocation.getText().toString())) {
                if (mProfileUpdatePresenter == null) {
                    mProfileUpdatePresenter = new ProfileUpdatePresenter();
                }
                Map<String, String> params = new HashMap();
                if (!TextUtils.isEmpty(etEditProfileLocation.getText().toString().trim())) {
                    etEditProfileLocation.setTag(etEditProfileLocation.getText().toString());
                    params.put("userLocation", locationId + "");
                    mProfileUpdatePresenter.updateUserDetail(params, SharedPreferenceUtil.getString(Constants.UserData.UserId, ""), this);
                }
            }
        } else {
            boolean isAnyEmpty = changeFocusIfEmpty(etEditProfileLocation);
            if (!isAnyEmpty) {
                etEditProfileLocation.setCursorVisible(false);
                startActivityForResult(new Intent(getActivity(), LocationsActivity.class), REQUEST_CONSTANT);
                etEditProfileDOB.clearFocus();
            }
        }
    }

    @BindView(R.id.etEditMembershipNo)
    EditText tvMemberShipNumber;

    private PresenterClass presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        String firstName = SharedPreferenceUtil.getString(Constants.UserData.UserFirstName, "");
        etProfileFirstName.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserFirstName, ""));
        etProfileFirstName.setHint(getString(R.string.firstName));
        if (firstName != null && firstName.length() > 0) {
            String first = String.valueOf(firstName.charAt(0)).toUpperCase();
            etProfileFirstName.setText(first + firstName.substring(1));
        }
        String lastName = SharedPreferenceUtil.getString(Constants.UserData.UserLastName, "");
        etProfileLastName.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserLastName, ""));
        etProfileFirstName.setHint(getString(R.string.lastName));
        if (lastName != null && lastName.length() > 0) {
            String first = String.valueOf(lastName.charAt(0)).toUpperCase();
            etProfileLastName.setText(first + lastName.substring(1));
        }


        etEditProfileBloodGroup.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserBloodGroup, ""));
        etEditProfileBloodGroup.setText(SharedPreferenceUtil.getString(Constants.UserData.UserBloodGroup, ""));
        etEditProfileBloodGroup.setHint(getString(R.string.BloodGroop));


        etEditProfileDOB.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserDOB, ""));
        etEditProfileDOB.setText(SharedPreferenceUtil.getString(Constants.UserData.UserDOB, ""));
        etEditProfileDOB.setTag(getString(R.string.DateOfBirth));


        etEditProfileLocation.setText(SharedPreferenceUtil.getString(Constants.UserData.UserLocationName, ""));
        etEditProfileLocation.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserLocationName, ""));
        etEditProfileLocation.setHint(getString(R.string.Location));

        etProfileMobileNumber.setHint(SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo, ""));
        etProfileMobileNumber.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserMobileNo, ""));
        etEditProfileEmail.setText(SharedPreferenceUtil.getString(Constants.UserData.UserEmail, ""));
        etEditProfileEmail.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserEmail, ""));
        etEditProfileFacebbokId.setText(SharedPreferenceUtil.getString(Constants.UserData.UserFBProfileName, ""));
        etEditProfileFacebbokId.setTag(SharedPreferenceUtil.getString(Constants.UserData.UserFBProfileName, ""));
        etProfileMobileNumber.setEnabled(false);
        etProfileFirstName.setCursorVisible(false);
        this.activity = getActivity();
        loadProfilePic(SharedPreferenceUtil.getString(Constants.UserData.UserProfilePic, ""));
        presenter = new PresenterClass();
        return view;
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

    @Override
    public void onSuccessLocationList(JSONArray jsonArray) {

    }


    private boolean changeFocusIfEmpty(EditText et) {
        if (mDialogAll != null) {
            if (!mDialogAll.isVisible()) {
                etEditProfileDOB.clearFocus();
            }
        }
        if (TextUtils.isEmpty(etProfileFirstName.getText().toString())) {
            etProfileFirstName.requestFocus();
            et.setCursorVisible(false);
            etProfileFirstName.setCursorVisible(true);
            UtilClass.displyMessage("First name is required", getActivity(), 0);
            return true;
        } else if (TextUtils.isEmpty(etProfileLastName.getText().toString())) {
            etProfileLastName.requestFocus();
            et.setCursorVisible(false);
            UtilClass.displyMessage("Last name is required", getActivity(), 0);
            etProfileLastName.setCursorVisible(true);
            return true;
        } else if (TextUtils.isEmpty(etProfileMobileNumber.getHint().toString()) || !(etProfileMobileNumber.getHint().toString().length() > 7 && etProfileMobileNumber.getHint().toString().length() < 14 && TextUtils.isDigitsOnly(etProfileMobileNumber.getHint().toString()))) {
            etProfileMobileNumber.requestFocus();
            et.setCursorVisible(false);
            if (TextUtils.isEmpty(etProfileMobileNumber.getHint().toString())) {
                UtilClass.displyMessage("Contact number  is required", getActivity(), 0);
            } else {
                UtilClass.displyMessage("Please enter valid contact number", getActivity(), 0);
            }
            etProfileMobileNumber.setCursorVisible(true);
            return true;
        } else if (!TextUtils.isEmpty(etEditProfileEmail.getText().toString()) && !UtilClass.isValidEmail(etEditProfileEmail.getText().toString())) {
            etEditProfileEmail.requestFocus();
            et.setCursorVisible(false);
            UtilClass.displyMessage("Please enter valid Email ", getActivity(), 0);
            etEditProfileEmail.setCursorVisible(true);
            return true;
        } else {
            return false;
        }
    }


    private void openDateSelector() {
        long minMaxTime = 100L * 365 * 1000 * 60 * 60 * 24L;
        long curruntMillis = System.currentTimeMillis();
        try {
            Date date = dateFormat.parse(etEditProfileDOB.getText().toString().trim());
            calendar.setTime(date);
            curruntMillis = calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mDialogAll = new com.jzxiang.pickerview.TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        calendar.setTimeInMillis(millseconds);
                        Date date = calendar.getTime();
                        if (date != null) {
                            String selectedDate = dateFormat.format(date);
                            etEditProfileDOB.setText(selectedDate);
                            etEditProfileDOB.clearFocus();
                            updateDateDateOfBirth();
                            Log.e("Selected", "date" + selectedDate);
                        }
                    }
                })
                .setCancelStringId("Cancel")
                .setSureStringId("Done")
                .setCurrentMillseconds(curruntMillis)
                .setTitleStringId("")
                .setYearText("")
                .setMonthText("")
                .setDayText("")
                .setCyclic(true)
                .setThemeColor(ActivityCompat.getColor(getActivity(), R.color.colorPrimaryDark))
                .setType(Type.YEAR_MONTH_DAY)
                .setMaxMillseconds(System.currentTimeMillis() + minMaxTime)
                .setMinMillseconds(System.currentTimeMillis() - minMaxTime)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.colorTransparentGray))
                .setWheelItemTextSelectorColor(ActivityCompat.getColor(getActivity(), R.color.colorBlack))
                .setWheelItemTextSize(18)
                .build();
        mDialogAll.show(getChildFragmentManager(), "month");
        mDialogAll.setCancelable(false);
    }


    private String getDateOfBirthToSend(String dob) {
        SimpleDateFormat dateFormate1 = new SimpleDateFormat("yyyy-MM-dd");
        String returnDate = "";
        try {
            Date date = dateFormat.parse(dob);
            returnDate = dateFormate1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }


    private void openBloodGroupSelector() {
        final String[] options = getResources().getStringArray(R.array.bloodGroups);
        OptionPicker picker = new OptionPicker(getActivity(), options);
        picker.setTextSize(18);
        picker.setLineColor(ActivityCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        picker.setTitleTextColor(ActivityCompat.getColor(getActivity(), R.color.colorWhite));
        picker.setTopBackgroundColor(ActivityCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        picker.setTopLineColor(ActivityCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        picker.setTextColor(ActivityCompat.getColor(getActivity(), R.color.colorBlack), ActivityCompat.getColor(getActivity(), R.color.colorTransparentGray));
        picker.setSelectedIndex(selectedPosition);
        picker.setSubmitText("Done");

        picker.setCancelText("Cancel");
        picker.setCancelTextColor(ActivityCompat.getColor(getActivity(), R.color.colorWhite));
        picker.setSubmitTextColor(ActivityCompat.getColor(getActivity(), R.color.colorWhite));
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String option) {
                int i = Arrays.asList(options).indexOf(option);
                selectedPosition = i;
                etEditProfileBloodGroup.setText(option);
                etEditProfileBloodGroup.clearFocus();
                updateBloodGroup();
            }
        });
        picker.show();
        picker.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.e("dismiss", "callled");
                etEditProfileBloodGroup.clearFocus();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CONSTANT) {
                try {
                    etEditProfileLocation.clearFocus();
                    if (data.getBooleanExtra("isSelected", false)) {
                        JSONObject selectedLocation = new JSONObject(data.getStringExtra("selectedCity"));
                        if (selectedLocation != null) {
                            etEditProfileLocation.setText(selectedLocation.optString("locationName"));
                            this.locationId = selectedLocation.optInt("locationId");
                            updateCity();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CAMERA) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                String imageBase64 =
                        UtilClass.getRealPathFromURI(UtilClass.getImageUri(getActivity(), bitmap), getActivity());
                uploadPhoto(imageBase64);
            } else {
                List<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (pathList != null) {
                    Log.e("selected image", "path+" + pathList.get(0));
                    uploadPhoto(pathList.get(0));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void updatePhoto() {
        final CharSequence[] items = {getResources().getString(R.string.takePhoto), getResources().getString(R.string.selectYourPhoto), getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.selectSources));
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getResources().getString(R.string.takePhoto))) {
                    whichSelect = REQUEST_CAMERA;
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals(getResources().getString(R.string.selectYourPhoto))) {
                    whichSelect = SELECT_FILE;
                    if (!checkPermission()) {
                        requestPermission();
                    } else {
                        getPhotoFromGallary();
                    }
                } else if (items[item].equals(getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void uploadPhoto(String imageData) {
        File file = new File(imageData);
        HashMap<String, String> params = new HashMap<>();
        params.put("userProfilePicture", file.getAbsoluteFile().toString());
        WebServiceUtil webServiceUtil = new WebServiceUtil(getActivity(), UtilClass.getProfileUpdateUrl(SharedPreferenceUtil.getString(Constants.UserData.UserId, "")), params, true, params, this);
        webServiceUtil.execute();
    }


    private void getPhotoFromGallary() {
        Intent intent = new Intent(getActivity(), MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        startActivityForResult(intent, SELECT_FILE);
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showMessageOKCancel("You need to allow access to external storage",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        PERMISSION_REQUEST_CODE);
                            }
                        }
                    });

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void onSuccessUploadImage(JSONObject response) {
        Log.e("onSuccess", "called" + response);
        SharedPreferenceUtil.putValue(Constants.UserData.UserProfilePic, response.optJSONObject("response").optString("userProfilePic"));
        SharedPreferenceUtil.save();
        loadProfilePic(response.optJSONObject("response").optString("userProfilePic"));

    }

    @Override
    public void onFailUpload(String message) {
        Log.e("onFailed", "called");

    }

    private void loadProfilePic(final String url) {
        if (isAdded() && activity != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("string url","url"+url);
                    Picasso.with(getActivity()).load(Constants.RequestConstants.BaseUrlForImage + url).placeholder(R.drawable.icon_placeholde).error(R.drawable.icon_placeholde).into(ivProfilePhotoSmall);
                    Picasso.with(getActivity()).load(Constants.RequestConstants.BaseUrlForImage + url).placeholder(R.drawable.cover_placeholder).error(R.drawable.cover_placeholder).into(ivProfilePhoto);
                }
            });



        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhotoFromGallary();
            } else {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
