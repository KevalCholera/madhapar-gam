package com.madhapar.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.mpt.storage.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileEditActivity extends AppCompatActivity {
    @BindView(R.id.tvProfilFirstName)
    TextView tvProfilFirstName;
    @BindView(R.id.tvProfileLastName)
    TextView tvProfileLastName;
    @BindView(R.id.tvProfileMobileNumber)
    TextView tvProfileMobileNumber;
    @BindView(R.id.tvProfileLocation)
    TextView tvProfileLocation;
    @BindView(R.id.tvProfileDOB)
    TextView tvProfileDOB;
    @BindView(R.id.tvProfileBloddGroup)
    TextView tvProfileBloddGroup;

    @BindView(R.id.tvProfileEmail)
    TextView tvProfileEmail;
    @BindView(R.id.tvProfileFacebookId)
    TextView tvProfileFacebookId;
    @BindView(R.id.ivProfilePhoto)
    ImageView ivProfilePhoto;
    @BindView(R.id.ivProfilePhotoSmall)
    ImageView ivProfilePhotoSmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ButterKnife.bind(this);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String profileString = getIntent().getStringExtra("profileObj");
        if (profileString != null) {
            try {
                JSONObject profieObj = new JSONObject(profileString);
                if (profieObj != null) {
                    Log.e("profiel", "prfiel" + profieObj);
                    tvProfilFirstName.setText(profieObj.optString("userFirstName"));
                    tvProfileLastName.setText(profieObj.optString("userLastName"));
                    tvProfileBloddGroup.setText(profieObj.optString("userBloodGroup").equalsIgnoreCase("") ? "N/A" : profieObj.optString("userBloodGroup"));
                    tvProfileDOB.setText((profieObj.optString("userDOB").equalsIgnoreCase("") || profieObj.optString("userDOB").equalsIgnoreCase("null")) ? "N/A" : profieObj.optString("userDOB"));
                    JSONObject locationObj = profieObj.optJSONObject("userLocation");
                    if (locationObj != null) {
                        tvProfileLocation.setText((locationObj.optString("locationName").equalsIgnoreCase("") || locationObj.optString("locationName").equalsIgnoreCase("null")) ? "N/A" : locationObj.optString("locationName"));
                    } else {
                        tvProfileLocation.setText("N/A");
                    }
                    tvProfileMobileNumber.setText(profieObj.optString("userMobileNo"));
                    tvProfileEmail.setText(profieObj.optString("email").equalsIgnoreCase("") ? "N/A" : profieObj.optString("email"));
                    tvProfileFacebookId.setText(profieObj.optString("userFBProfileName").equalsIgnoreCase("") ? "N/A" : profieObj.optString("userFBProfileName"));
                    Picasso.with(this).load(Constants.RequestConstants.BaseUrlForImage + profieObj.optString("userProfilePicture")).error(R.drawable.ic_network_place_holder).placeholder(R.drawable.ic_network_place_holder).into(ivProfilePhotoSmall);
                    Picasso.with(this).load(Constants.RequestConstants.BaseUrlForImage + profieObj.optString("userProfilePicture")).error(R.drawable.cover_placeholder).placeholder(R.drawable.cover_placeholder).into(ivProfilePhoto);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
}
