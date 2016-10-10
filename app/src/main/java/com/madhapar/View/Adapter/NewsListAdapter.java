package com.madhapar.View.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Model.NewsObject;
import com.madhapar.PagerUtil.AutoScrollViewPager;
import com.madhapar.PagerUtil.CirclePageIndicator;
import com.madhapar.Presenter.RequestPresenter;

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
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> implements NewsLikeCommentUpdateCallback {
    Context context;
    List<NewsObject> newsList;
    static String newsId1;
    private RecyclerView rvNewsList;
    private LinearLayoutManager rvManager;
    private NewsObject.ComparatorClass comparator = new NewsObject.ComparatorClass();

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
        holder.tvNewsCity.setText("- " + newsObj.getNewsCity());
        holder.tvNewsDescription.setText(newsObj.getNewsDescription());
        holder.tvNewsLikeCount.setText(newsObj.getNewsLikeCount());
        holder.tvNewsCommentCount.setText(newsObj.getNewsCommentCount());
        holder.tvNewsDateTime.setText(newsObj.getNewsDataAndTime());
        holder.ivNewsLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsId1 = newsObj.getNewsId();
                new RequestPresenter().updateLikeComment(newsObj.getNewsId(), "1", "hello", NewsListAdapter.this);
            }
        });
        if (newsObj.getNewsImageArray().length() > 0) {
            holder.AsvNewsPager.setVisibility(View.VISIBLE);
            if (newsObj.getNewsImageArray().length() > 1)
                holder.CpiNewsPageIndicator.setVisibility(View.VISIBLE);

            NewsImagePagerAdapter imagePagerAdapter = new NewsImagePagerAdapter(context, newsObj.getNewsImageArray());
            holder.AsvNewsPager.setAdapter(imagePagerAdapter);
            holder.CpiNewsPageIndicator.setViewPager(holder.AsvNewsPager);
            holder.AsvNewsPager.startAutoScroll();
            holder.AsvNewsPager.setInterval(2000);
            holder.AsvNewsPager.startAutoScroll();
        } else {
            holder.AsvNewsPager.setVisibility(View.GONE);
            holder.CpiNewsPageIndicator.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void successfulUpdateLike(NewsObject updatedObj) {
        comparator.newsId = newsId1;
        int listIndex = newsList.indexOf(comparator);
        if (listIndex >= 0) {
            View view = rvManager.getChildAt(listIndex - rvManager.findFirstVisibleItemPosition());
            if (view != null) {
                ImageView ivNewsLike = (ImageView) view.findViewById(R.id.ivNewsLike);
                ivNewsLike.setImageResource(R.mipmap.ic_news_like_filled);
            }
        }

    }

    @Override
    public void failUpdateResponse(String message) {
        Log.e("update", "message" + message);
    }

    @Override
    public void failUpdateRequest() {
        Log.e("update", "failRequest");
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

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateAdapter(List<NewsObject> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
        Log.e("adapter", "update");

    }
}
