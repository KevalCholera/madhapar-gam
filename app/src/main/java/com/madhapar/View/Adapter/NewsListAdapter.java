package com.madhapar.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Model.NewsObject;
import com.madhapar.PagerUtil.AutoScrollViewPager;
import com.madhapar.PagerUtil.CirclePageIndicator;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.HomeViewInt;
import com.madhapar.View.NewsCommentActivity;
import com.madhapar.View.NewsDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/7/2016.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> implements NewsLikeCommentUpdateCallback, HomeViewInt {
    Context context;
    List<NewsObject> newsList;
    String newsId1;
    private RecyclerView rvNewsList;
    private LinearLayoutManager rvManager;
    private NewsObject.ComparatorClass comparator = new NewsObject.ComparatorClass();
    private RequestPresenter requestPresenter;

    public NewsListAdapter(Context context, List<NewsObject> newsList, RecyclerView rvNewsList, LinearLayoutManager rvManager) {
        this.rvNewsList = rvNewsList;
        this.newsList = newsList;
        this.context = context;
        this.rvManager = rvManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewNewsData = LayoutInflater.from(context).inflate(R.layout.element_news_list, parent, false);
        ButterKnife.bind(this, viewNewsData);
        return new MyViewHolder(viewNewsData);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NewsObject newsObj = newsList.get(position);
        holder.tvNewsTitle.setText(newsObj.getNewsTitle());
        holder.tvNewsCity.setText("\u2022 " + newsObj.getNewsCity());
        holder.tvNewsDescription.setText(newsObj.getNewsDescription());
        holder.tvNewsLikeCount.setText(newsObj.getNewsLikeCount());
        holder.tvNewsCommentCount.setText(newsObj.getNewsCommentCount());
        holder.tvNewsDateTime.setText(newsObj.getNewsDataAndTime());
        if (newsObj.isCommented()) {
            holder.ivNewsComment.setImageResource(R.mipmap.ic_news_comment_filled);
        } else {
            holder.ivNewsComment.setImageResource(R.mipmap.ic_news_comment);
        }
        if (newsObj.getNewsStatusId().equalsIgnoreCase("")) {
            holder.ivNewsLike.setImageResource(R.mipmap.ic_news_like);
        } else {
            holder.ivNewsLike.setImageResource(R.mipmap.ic_news_like_filled);
        }
        holder.ivNewsLike.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     newsId1 = newsObj.getNewsId();
                                                     if (requestPresenter == null) {
                                                         requestPresenter = new RequestPresenter();
                                                     }
                                                     if (newsObj.getNewsStatusId().equalsIgnoreCase("")) {
                                                         requestPresenter.updateLikeComment(newsObj.getNewsId(), "2", "", NewsListAdapter.this);
                                                     } else {
                                                         requestPresenter.removeLike(newsObj.getNewsStatusId(), NewsListAdapter.this);
                                                     }
                                                 }
                                             }
        );
        holder.ivNewsComment.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Intent intent = new Intent(context, NewsCommentActivity.class);
                                                        intent.putExtra("newsId", newsObj.getNewsId());
                                                        intent.putExtra("newsStatusId", "1");
                                                        context.startActivity(intent);
//                                                        newsId1 = newsObj.getNewsId();
//                                                        if (requestPresenter == null)
//                                                            requestPresenter = new RequestPresenter();
//                                                        requestPresenter.updateLikeComment(newsObj.getNewsId(), "1", "Comments..", NewsListAdapter.this);
                                                    }
                                                }

        );
        holder.llNewsDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("NewsData", newsObj);
                context.startActivity(intent);
            }
        });
        holder.AsvNewsPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("clicked", "openImage View Activity");
            }
        });
        try {
            JSONArray imageArray = new JSONArray(newsObj.getNewsImageArray());
            if (imageArray != null && imageArray.length() > 0) {
                if (imageArray.length() > 1)
                    holder.CpiNewsPageIndicator.setVisibility(View.VISIBLE);
                else
                    holder.CpiNewsPageIndicator.setVisibility(View.GONE);
                NewsImagePagerAdapter imagePagerAdapter = new NewsImagePagerAdapter(context, imageArray);
                holder.CpiNewsPageIndicator.setVisibility(View.VISIBLE);
                holder.AsvNewsPager.setAdapter(imagePagerAdapter);
                holder.AsvNewsPager.setVisibility(View.VISIBLE);
                holder.CpiNewsPageIndicator.setViewPager(holder.AsvNewsPager);
                holder.AsvNewsPager.setInterval(Constants.DifferentData.ViewPagerInterval);
                holder.AsvNewsPager.startAutoScroll();
            } else {
                holder.AsvNewsPager.setVisibility(View.GONE);
                holder.CpiNewsPageIndicator.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void successfulUpdateLike(JSONObject updateObj) {
        if (UtilClass.isInternetAvailabel(context)) {
            UtilClass.showProgress(context, context.getString(R.string.msgPleaseWait));
            if (requestPresenter == null) {
                requestPresenter = new RequestPresenter();
            }
            requestPresenter.getNewsList(this);
        } else {
            UtilClass.displyMessage(context.getString(R.string.msgPleaseWait), context, 0);
        }

//        comparator.newsId = newsId1;
//        int listIndex = newsList.indexOf(comparator);
//        Log.e("update", "index" + listIndex);
//        if (listIndex >= 0) {
//            View view = rvManager.getChildAt(listIndex - rvManager.findFirstVisibleItemPosition());
//            Log.e("view Index", "view" + (listIndex - rvManager.findFirstVisibleItemPosition()));
//            if (view != null) {
//                TextView tv = (TextView) view.findViewById(R.id.tvNewsTitle);
//                String title = tv.getText().toString();
//                Log.e("title", "title" + title);
//                ImageView ivNewsLike = (ImageView) view.findViewById(R.id.ivNewsLike);
//                ivNewsLike.setImageResource(R.mipmap.ic_news_like_filled);
//            }
//        }

    }

    @Override
    public void failUpdateResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, context, 0);
    }

    @Override
    public void failUpdateRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), context, 0);
    }

    @Override
    public void onSuccessNewsList(List<NewsObject> newsList) {
        UtilClass.hideProgress();
        updateAdapter(newsList);
    }


    @Override
    public void onFailRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), context, 0);
    }

    @Override
    public void onFailResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, context, 0);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNewsTitle)
        TextView tvNewsTitle;
        @BindView(R.id.tvNewsDateTime)
        TextView tvNewsDateTime;
        @BindView(R.id.tvNewsCity)
        TextView tvNewsCity;
        @BindView(R.id.tvNewsDescription)
        TextView tvNewsDescription;
        @BindView(R.id.tvNewsLikeCount)
        TextView tvNewsLikeCount;
        @BindView(R.id.tvNewsCommentCount)
        TextView tvNewsCommentCount;
        @BindView(R.id.ivNewsLike)
        ImageView ivNewsLike;
        @BindView(R.id.AsvNewsPager)
        AutoScrollViewPager AsvNewsPager;
        @BindView(R.id.CpiNewsPageIndicator)
        CirclePageIndicator CpiNewsPageIndicator;
        @BindView(R.id.ivNewsComment)
        ImageView ivNewsComment;
        @BindView(R.id.clNewsCard)
        CardView clNewsCard;
        @BindView(R.id.llNewsUpdate)
        LinearLayout llNewsUpdate;
        @BindView(R.id.llNewsDetail)
        LinearLayout llNewsDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public void updateAdapter(List<NewsObject> newsList) {
        UtilClass.hideProgress();
        this.newsList = newsList;
        notifyDataSetChanged();
    }
}
