package com.madhapar.View.Adapter;

import android.content.Context;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartsense.newproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/7/2016.
 */
public class NewsDataRecyclerView extends RecyclerView.Adapter<NewsDataRecyclerView.MyViewHolder> {
    JSONArray newsDataArry;
    Context context;
    public NewsDataRecyclerView(Context context, JSONArray jsonArray) {
        this.newsDataArry = jsonArray;
        Log.e("crete","list"+jsonArray.length());
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewNewsData = LayoutInflater.from(context).inflate(R.layout.home_cardview,parent,false);
        ButterKnife.bind(this,viewNewsData);
        return new MyViewHolder(viewNewsData);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            JSONObject obj1 = newsDataArry.getJSONObject(position);
            holder.tvNewsTitle.setText(obj1.optString("newsTitle"));
            holder.tvDateTime.setText(obj1.optString("dateTime"));
            holder.tvCityNews.setText(obj1.optString("newsCity"));
            holder.tvDescription.setText(obj1.optString("newDescription"));
            holder.tvLikeCount.setText(obj1.optString("likeCount"));
            holder.tvCommentCount.setText(obj1.optString("commentCount"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return newsDataArry.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNewTitle)
        TextView tvNewsTitle;
        @BindView(R.id.tvDateTime)
        TextView tvDateTime;
        @BindView(R.id.tvCityNews)
        TextView tvCityNews;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvLikeCount)
        TextView tvLikeCount;
        @BindView(R.id.tvCommentCount)
        TextView tvCommentCount;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
