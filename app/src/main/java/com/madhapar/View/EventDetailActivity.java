package com.madhapar.View;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.madhapar.Presenter.EventPresenter;
import com.madhapar.R;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventDetailActivity extends AppCompatActivity implements EventDetailCallback {
    @BindView(R.id.tvEventDetailTitle)
    TextView tvEventDetailTitle;
    @BindView(R.id.tvEventFromDate)
    TextView tvEventFromDate;
    @BindView(R.id.tvEventToDate)
    TextView tvEventToDate;
    @BindView(R.id.svEventDetail)
    ScrollView svEventDetail;
    @BindView(R.id.tvEventLocation)
    TextView tvEventLocation;
    @BindView(R.id.tEventDetailDescription)
    TextView tEventDetailDescription;
    @BindView(R.id.tvEventDetailGoingCount)
    TextView tvEventDetailGoingCount;
    @BindView(R.id.tvEventDetailInterestCount)
    TextView tvEventDetailInterestCount;
    @BindView(R.id.tvEventDetailNotInterestedCount)
    TextView tvEventDetailNotInterestedCount;
    @BindView(R.id.tvEventOrganizer)
    TextView tvEventOrganizer;
    @BindView(R.id.llEventDetailGoing)
    LinearLayout llEventDetailGoing;
    @BindView(R.id.ivEventCoverImage)
    ImageView ivEventCoverImage;
    @BindView(R.id.llEventDetailNotGoing)
    LinearLayout llEventDetailNotGoing;
    @BindView(R.id.llEventDetailIntrested)
    LinearLayout llEventDetailIntrested;
    @BindView(R.id.tvOpenPhotosActivity)
    TextView tvOpenPhotosActivity;
    @BindView(R.id.btnEventInfoShare)
    Button btnEventInfoShare;
    private int PERMISSION_REQUEST_CODE = 103;

    @OnClick(R.id.btnEventInfoShare)
    void shareEvent() {
        if (checkPermission()) {
            svEventDetail.smoothScrollTo(0, svEventDetail.getTop());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = loadBitmapFromView(this, this.getWindow().getDecorView().getRootView());
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
            Uri imageUri = Uri.parse(path);
            shareImage(imageUri);

        } else {
            requestPermission();
        }
    }

    private void shareImage(Uri imagePath) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    @OnClick(R.id.tvOpenPhotosActivity)
    void openPhotos() {
        Intent intent = new Intent(EventDetailActivity.this, EventPhotoActivity.class);
        intent.putExtra("eventPhotos", eventObj.optJSONArray("eventPhotos").toString());
        intent.putExtra("albumName", eventObj.optString("eventTitle"));
        startActivity(intent);

    }

    @OnClick(R.id.ivEventCoverImage)
    void openPhotosForCoverImage() {
        Intent intent = new Intent(EventDetailActivity.this, PhotoActivity.class);
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject coverImageObj = new JSONObject();
            coverImageObj.put("eventImage", eventObj.optString("coverImage"));
            jsonArray.put(coverImageObj);
            intent.putExtra("images", jsonArray.toString());
            intent.putExtra("position", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        intent.putExtra("albumName", eventObj.optString("eventTitle"));
        startActivity(intent);

    }


    private static final int StatusListActivityConstant = 200;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

    @OnClick(R.id.llEventDetailGoing)
    public void going() {
        Intent goingIntent = new Intent(EventDetailActivity.this, StatusListActivity.class);
        goingIntent.putExtra("eventObj", eventObj.toString());
        goingIntent.putExtra("canChangeStatus", getIntent().getBooleanExtra("canEventStatusChange", false));
        goingIntent.putExtra("evetStatus", Constants.DifferentData.GoingStatus);
        startActivityForResult(goingIntent, StatusListActivityConstant);
    }

    @OnClick(R.id.llEventDetailIntrested)
    public void interested() {
        Intent goingIntent = new Intent(EventDetailActivity.this, StatusListActivity.class);
        goingIntent.putExtra("isInterestedId", eventObj.optString("isInterestedId"));
        goingIntent.putExtra("eventObj", eventObj.toString());
        goingIntent.putExtra("evetStatus", Constants.DifferentData.InterestedStatus);
        goingIntent.putExtra("canChangeStatus", getIntent().getBooleanExtra("canEventStatusChange", false));
        startActivityForResult(goingIntent, StatusListActivityConstant);
    }

    @OnClick(R.id.llEventDetailNotGoing)
    public void notGoing() {
        Intent goingIntent = new Intent(EventDetailActivity.this, StatusListActivity.class);
        goingIntent.putExtra("isMaybeId", eventObj.optString("isMaybeId"));
        goingIntent.putExtra("eventObj", eventObj.toString());
        // goingIntent.putExtra("canChangeStatus", isEventStatusValidForNotGoing(eventObj));
        goingIntent.putExtra("evetStatus", Constants.DifferentData.NotGoingStatus);
        startActivityForResult(goingIntent, StatusListActivityConstant);
    }


    JSONObject eventObj;
    EventPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenet_detail);
        ButterKnife.bind(this);
        setUpActiobBar();
        if (getIntent().getStringExtra("event") != null) {
            try {
                eventObj = new JSONObject(getIntent().getStringExtra("event"));

                setUpView();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpActiobBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    private void setUpView() {

        if (eventObj != null) {
            tvEventDetailTitle.setText(eventObj.optString("eventTitle"));
            tvEventFromDate.setText(eventObj.optString("eventFromDate"));
            tvEventToDate.setText(eventObj.optString("eventToDate"));
            tvEventLocation.setText(eventObj.optString("eventAddress"));
            tEventDetailDescription.setText(eventObj.optString("eventDescription"));
            tvEventOrganizer.setText(eventObj.optString("eventOrganizedBy"));
            String s = tvEventDetailGoingCount.getText().toString();
            tvEventDetailGoingCount.setText(" " + eventObj.optString("going") + " ");
            tvEventDetailInterestCount.setText(" " + eventObj.optString("interested") + " ");
            tvEventDetailNotInterestedCount.setText(" " + eventObj.optString("cantGo") + " ");
            String coverImageUrl = eventObj.optString("coverImage");
            if (eventObj.optJSONArray("eventPhotos").length() > 0) {
                tvOpenPhotosActivity.setVisibility(View.VISIBLE);
            } else {
                tvOpenPhotosActivity.setVisibility(View.GONE);
            }
            if (isSelected(eventObj, Constants.DifferentData.GoingStatus)) {
                llEventDetailGoing.setBackgroundColor(getResources().getColor(R.color.colorEventSelected));
            } else {
                llEventDetailGoing.setBackgroundColor(Color.WHITE);
            }
            if (isSelected(eventObj, Constants.DifferentData.NotGoingStatus)) {
                llEventDetailNotGoing.setBackgroundColor(getResources().getColor(R.color.colorEventSelected));
            } else {
                llEventDetailNotGoing.setBackgroundColor(Color.WHITE);
            }
            if (isSelected(eventObj, Constants.DifferentData.InterestedStatus)) {
                llEventDetailIntrested.setBackgroundColor(getResources().getColor(R.color.colorEventSelected));
            } else {
                llEventDetailIntrested.setBackgroundColor(Color.WHITE);
            }
            Picasso.with(this).load(Constants.RequestConstants.BaseUrlForImage + coverImageUrl).placeholder(R.mipmap.img_event_detail_photo_place_holder).error(R.mipmap.img_event_detail_photo_place_holder).into(ivEventCoverImage);
        }
    }

    private void getEventdetail(String eventId) {
        if (mPresenter == null) {
            mPresenter = new EventPresenter();
        }
        if (UtilClass.isInternetAvailabel(this)) {
            UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
            mPresenter.getEventDetail(eventId, this);
        } else {
            UtilClass.hideProgress();
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }

    }

    @Override
    public void onSuccessEventDetail(JSONObject response) {
        this.eventObj = response.optJSONObject("response");
        UtilClass.hideProgress();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setUpView();
            }
        });
    }

    @Override
    public void onFailEventDetailRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }

    @Override
    public void onFailEventDetailResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, this, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (UtilClass.isInternetAvailabel(this)) {
                if (mPresenter == null) {
                    mPresenter = new EventPresenter();
                }
                String eventId = data.getStringExtra("eventId");
                mPresenter.getEventDetail(eventId, this);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    private boolean isEventStatusValidForNotGoing(JSONObject eventObject) {
        boolean isValid = false;
        try {
            String fromDate = eventObject.optString("eventToDate");
            String finalDate = fromDate.substring(0, 2) + "-" + fromDate.substring(3, 6) + "-" + fromDate.substring(8, 13) + fromDate.substring(16, 18) + ":" + fromDate.substring(19, 21) + " " + fromDate.substring(22);
            Date date = sdf.parse(finalDate);
            long EventLasttime = date.getTime();
            long curruntTime = System.currentTimeMillis();
            if ((EventLasttime - curruntTime) > (24 * 60 * 60 * 100)) {
                isValid = true;
            } else {
                isValid = false;
            }
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return isValid;

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
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
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                shareEvent();
            } else {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public static Bitmap loadBitmapFromView(Context context, View v) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        v.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap returnedBitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(returnedBitmap);
        v.draw(c);

        return returnedBitmap;
    }

}

