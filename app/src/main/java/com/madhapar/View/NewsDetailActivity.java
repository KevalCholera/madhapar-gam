package com.madhapar.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Model.NewsObject;
import com.madhapar.PagerUtil.AutoScrollViewPager;
import com.madhapar.PagerUtil.CirclePageIndicator;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.CustomGrid;
import com.madhapar.View.Adapter.NewsImagePagerAdapter;
import com.madhapar.View.Adapter.NewsLikeCommentUpdateCallback;

import org.json.JSONArray;
import org.json.JSONException;

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
    @BindView(R.id.rlNewsDetailImage)
    RelativeLayout rlNewsDetailImage;
    private NewsObject newsDetailObj;
    private RequestPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        if (getIntent().getSerializableExtra("NewsData") != null) {
            newsDetailObj = (NewsObject) getIntent().getSerializableExtra("NewsData");
            setupViews();

            setUpViewPager();
        } else {
            if (presenter == null) {
                presenter = new RequestPresenter();
                presenter.getNewsDetail("1", this);
            }
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
    public void successfulUpdateLike() {
        UtilClass.hideProgress();
        if (newsDetailObj.getNewsStatusId().equalsIgnoreCase("")) ;
        // updateView();
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
            if (newsDetailObj.getNewsStatusId() == null || newsDetailObj.getNewsStatusId().equalsIgnoreCase("")) {
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
                    if (newsDetailObj.getNewsStatusId().equalsIgnoreCase("")) {
                        if (presenter == null) {
                            presenter = new RequestPresenter();
                            if (UtilClass.isInternetAvailabel(NewsDetailActivity.this)) {
                                UtilClass.showProgress(NewsDetailActivity.this, getString(R.string.msgPleaseWait));
                                presenter.updateLikeComment(newsDetailObj.getNewsId(), "2", "", NewsDetailActivity.this);
                            } else {
                                UtilClass.displyMessage(getString(R.string.msgCheckInternet), NewsDetailActivity.this, 0);
                            }
                        }
                    }
                }
            });
            ivNewsDetailComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewsDetailActivity.this, NewsCommentActivity.class);
                    intent.putExtra("newsId", newsDetailObj.getNewsId());
                    intent.putExtra("newsStatusId", "1");
                    startActivity(intent);
                }
            });
        }


    }


    @Override
    public void onSuccessNewsDetail(NewsObject newsObject) {
        this.newsDetailObj = newsObject;
        Log.e("newsDetail", "title" + newsObject.getNewsTitle());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setupViews();
                setUpViewPager();
            }
        });

    }

    @Override
    public void onFailRequest() {
        Log.e("onSuccess", "Request Fail");
    }

    @Override
    public void onFailResponse(String message) {
        Log.e("onSuccess", "Response Fail" + message);
    }
}
