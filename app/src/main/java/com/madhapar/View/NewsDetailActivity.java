package com.madhapar.View;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.madhapar.Model.NewsObject;
import com.madhapar.PagerUtil.AutoScrollViewPager;
import com.madhapar.PagerUtil.CirclePageIndicator;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.NewsImagePagerAdapter;
import com.madhapar.View.Adapter.NewsLikeCommentUpdateCallback;
import com.madhapar.View.Adapter.NewsListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smartsense on 10/10/16.
 */

public class NewsDetailActivity extends BaseActivity implements NewsLikeCommentUpdateCallback, NewsDetailViewInt {
    @BindView(R.id.asvNewsDetailPager)
    AutoScrollViewPager asvNewsDetailPager;
    @BindView(R.id.cpiNewsDetailPageIndicator)
    CirclePageIndicator cpiNewsDetailPageIndicator;
    @BindView(R.id.tvNewsDetailTitle)
    TextView tvNewsDetailTitle;
    @BindView(R.id.tvNewsDetailDateTime)
    TextView tvNewsDetailDateTime;
    @BindView(R.id.tvNewsDetailCity)
    TextView tvNewsDetailCity;
    @BindView(R.id.tvNewsDetailDescription)
    TextView tvNewsDetailDescription;
    @BindView(R.id.tvNewsDetailCommentCount)
    TextView tvNewsDetailCommentCount;
    @BindView(R.id.tvNewsDetailLikeCount)
    TextView tvNewsDetailLikeCount;
    @BindView(R.id.ivNewsDetailLike)
    ImageView ivNewsDetailLike;
    @BindView(R.id.ivNewsDetailComment)
    ImageView ivNewsDetailComment;
    @BindView(R.id.ivDetailNewsShare)
    ImageView ivDetailNewsShare;
    @BindView(R.id.screenshot)
    ImageView screenshot;
    Bitmap bitmap;
    @BindView(R.id.rlNewsDetailImage)
    RelativeLayout rlNewsDetailImage;
    private NewsObject newsDetailObj;
    private RequestPresenter presenter;
    private static final int CommentActivityRequestCode = 22;

    @OnClick(R.id.ivDetailNewsShare)
    void screenshotShare(){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        String imagePath = new String(Environment.getExternalStorageDirectory() + "screenshot.png");
        Log.e("pathURL",imagePath);
        File imagefileToShare = new File(imagePath);
        Uri bmpUri = Uri.fromFile(imagefileToShare);
        share.putExtra(Intent.EXTRA_STREAM,bmpUri);
        startActivity(Intent.createChooser(share, "Share Image"));
        screenShot(screenshot);
    }
    private void shareImage(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        String image = new String(Environment.getExternalStorageDirectory() + "screenshot.jpg");
        File imagefile = new File(image);
        Uri uri = Uri.fromFile(imagefile);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share Image"));

    }
    public void screenShot(View v){
        bitmap = getBitmapOFRootView(ivDetailNewsShare);
        createImage(bitmap);
        screenshot.setImageBitmap(bitmap);
        Log.e("screenshot","here");
    }

    private void createImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File file = new File(Environment.getExternalStorageDirectory() +
                "/screenshot.jpg");
        try {
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes.toByteArray());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapOFRootView(View view1) {
        View rootview = view1.getRootView();
        rootview.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = rootview.getDrawingCache();
        return bitmap1;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().getSerializableExtra("NewsData") != null) {
            newsDetailObj = (NewsObject) getIntent().getSerializableExtra("NewsData");
            setupViews();
            setUpViewPager();
        } else {
            if (presenter == null) {
                presenter = new RequestPresenter();
            }
            presenter.getNewsDetail("1", this);
        }


    }

    private void setUpViewPager() {
        try {
            if (newsDetailObj != null) {
                JSONArray imageArray = new JSONArray(newsDetailObj.getNewsImageArray());
                if (imageArray != null && imageArray.length() > 0) {
                    rlNewsDetailImage.setVisibility(View.VISIBLE);
                    if (imageArray.length() > 1)
                        cpiNewsDetailPageIndicator.setVisibility(View.VISIBLE);
                    NewsImagePagerAdapter imagePagerAdapter = new NewsImagePagerAdapter(this, imageArray);
                    asvNewsDetailPager.setAdapter(imagePagerAdapter);
                    asvNewsDetailPager.setInterval(Constants.DifferentData.ViewPagerInterval);
                    cpiNewsDetailPageIndicator.setViewPager(asvNewsDetailPager);
                    asvNewsDetailPager.startAutoScroll();
                } else {
                    rlNewsDetailImage.setVisibility(View.GONE);
                    cpiNewsDetailPageIndicator.setVisibility(View.GONE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
            return true;
        }
//        if (item.getItemId() == R.id.ivDetailNewsShare) {
//            String imagePath = new String(Environment.getExternalStorageDirectory() + "/screenshot.png");
//            Log.e("pathURL", imagePath);
//            Uri bmpUri = Uri.fromFile(new File(imagePath));
//            shareImage(bmpUri);
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    public void successfulUpdateLike(final JSONObject updateObj) {
        if (UtilClass.isInternetAvailabel(this)) {
            if (presenter == null) {
                presenter = new RequestPresenter();
            }
            presenter.getNewsDetail(newsDetailObj.getNewsId(), this);
        } else {
            UtilClass.hideProgress();
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
        }

    }


    @Override
    public void failUpdateResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, this, 0);
    }

    @Override
    public void failUpdateRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }

    private void setupViews() {
        if (newsDetailObj != null) {
            tvNewsDetailTitle.setText(newsDetailObj.getNewsTitle());
            tvNewsDetailDateTime.setText(newsDetailObj.getNewsDataAndTime());
            tvNewsDetailCity.setText("\u2022 " + newsDetailObj.getNewsCity());
            tvNewsDetailDescription.setText(newsDetailObj.getNewsDescription());
            tvNewsDetailLikeCount.setText(newsDetailObj.getNewsLikeCount());
            tvNewsDetailCommentCount.setText(newsDetailObj.getNewsCommentCount());
            Log.e("newsActivity", newsDetailObj.getNewsStatusId());
            Log.e("newsId", newsDetailObj.getNewsId());
            if (newsDetailObj.getNewsStatusId().equalsIgnoreCase("null") || newsDetailObj.getNewsStatusId() == null || newsDetailObj.getNewsStatusId().equalsIgnoreCase("")) {
                ivNewsDetailLike.setImageResource(R.mipmap.ic_news_like);
            } else {
                ivNewsDetailLike.setImageResource(R.mipmap.ic_news_like_filled);
            }
            if (newsDetailObj.isCommented()) {
                ivNewsDetailComment.setImageResource(R.mipmap.ic_news_comment_filled);
            } else {
                ivNewsDetailComment.setImageResource(R.mipmap.ic_news_comment);
            }
            ivNewsDetailLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (presenter == null) {
                        presenter = new RequestPresenter();
                    }
                    if (UtilClass.isInternetAvailabel(NewsDetailActivity.this)) {
                        UtilClass.showProgress(NewsDetailActivity.this, getString(R.string.msgPleaseWait));
                        if (newsDetailObj.getNewsStatusId().equalsIgnoreCase("null") || newsDetailObj.getNewsStatusId() == null || newsDetailObj.getNewsStatusId().equalsIgnoreCase("")) {
                            presenter.updateLikeComment(newsDetailObj.getNewsId(), "2", "", NewsDetailActivity.this);
                        } else {
                            presenter.removeLike(newsDetailObj.getNewsStatusId(), NewsDetailActivity.this);
                        }
                    } else {
                        UtilClass.displyMessage(getString(R.string.msgCheckInternet), NewsDetailActivity.this, 0);
                    }

                }
            });
            ivNewsDetailComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewsDetailActivity.this, NewsCommentActivity.class);
                    intent.putExtra("newsId", newsDetailObj.getNewsId());
                    intent.putExtra("newsStatusId", "1");
                    startActivityForResult(intent, CommentActivityRequestCode);
                }
            });
        }
    }


    @Override
    public void onSuccessNewsDetail(NewsObject newsObject) {
        this.newsDetailObj = newsObject;
        UtilClass.hideProgress();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setupViews();
                setUpViewPager();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CommentActivityRequestCode) {
                if (UtilClass.isInternetAvailabel(this)) {
                    UtilClass.showProgress(this, getString(R.string.msgPleaseWait));
                    if (presenter == null) {
                        presenter = new RequestPresenter();
                    }
                    presenter.getNewsDetail(data.getStringExtra("newsId"), this);
                } else {
                    UtilClass.hideProgress();
                    UtilClass.displyMessage(getString(R.string.msgCheckInternet), this, 0);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFailRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }

    @Override
    public void onFailResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, this, 0);
    }
}
