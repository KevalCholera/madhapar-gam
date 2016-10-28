package com.madhapar.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/5/2016.
 */
public class EventStatusListAdapter extends RecyclerView.Adapter<EventStatusListAdapter.MyViewHolder> {
    JSONArray goingListArry;
    Context context;

    public EventStatusListAdapter(Context context, JSONArray jsonArray) {
        this.goingListArry = jsonArray;
        Log.e("crete", "list" + jsonArray.length());
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewGoing = LayoutInflater.from(context).inflate(R.layout.element_status_list, parent, false);
        ButterKnife.bind(this, viewGoing);
        return new MyViewHolder(viewGoing);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            JSONObject statusObj = goingListArry.getJSONObject(position);
            JSONObject userObj = statusObj.optJSONObject("user");
            if (userObj != null) {
                String userName = userObj.optString("userFirstName") + userObj.optString("userLastName");
                holder.tvStatusPersonName.setText(userName);
                String picUrl = userObj.optString("userProfilePic");
                Log.e("picUrl","picture"+picUrl);
                if (picUrl != null) {
                    Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + picUrl).error(R.drawable.ic_network_place_holder).placeholder(R.drawable.ic_network_place_holder).into(holder.ivStautsUserImage);
                }
                if (statusObj.optString("eventStatusType").equalsIgnoreCase(Constants.DifferentData.GoingStatus)) {
                    holder.tvGoingPersonCount.setVisibility(View.VISIBLE);
                    holder.tvGoingPersonCount.setText(Integer.valueOf(statusObj.optString("eventUserCount")) > 1 ? statusObj.optString("eventUserCount") + " Members" : statusObj.optString("eventUserCount") + " Member");
                } else {
                    holder.tvGoingPersonCount.setVisibility(View.GONE);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return goingListArry.length();
    }

    public void updateStatusAdaptet(JSONArray statusArray) {
        this.goingListArry = statusArray;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvStatusPersonName)
        TextView tvStatusPersonName;
        @BindView(R.id.ivStautsUserImage)
        ImageView ivStautsUserImage;
        @BindView(R.id.tvGoingPersonCount)
        TextView tvGoingPersonCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
