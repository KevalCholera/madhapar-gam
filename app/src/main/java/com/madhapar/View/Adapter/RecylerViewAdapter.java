package com.madhapar.View.Adapter;

import android.content.Context;
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

import butterknife.ButterKnife;

/**
 * Created by smartsense on 30/09/16.
 */

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder> {
    JSONArray eventArry;
    Context context;
    public RecylerViewAdapter(Context context, JSONArray jsonArray) {
        this.eventArry = jsonArray;
        Log.e("crete","list"+jsonArray.length());
       this.context=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(context).inflate(R.layout.alert_card_event, parent, false);
        ButterKnife.bind(this,eventView);
        if(eventView != null){Log.e("Log Here","eventview not null");}
        return new MyViewHolder(eventView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            JSONObject obj1 =  eventArry.getJSONObject(position);
            holder.tvEventName.setText(obj1.optString("eventTitle"));
            holder.tvAddress.setText(obj1.optString("eventAddress"));
            holder.tvNotInterest.setText(obj1.optString("cantGo "));
            holder.tvTime.setText(obj1.optString("eventFromDate "));
            holder.tvGoing.setText(obj1.optString("going "));
            holder.tvInterest.setText(obj1.optString("interested "));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return eventArry.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvEventName, tvTime, tvAddress, tvGoing, tvInterest, tvNotInterest;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvEventName = (TextView) itemView.findViewById(R.id.tvEventName);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvGoing = (TextView) itemView.findViewById(R.id.tvGoingCount);
            tvInterest = (TextView) itemView.findViewById(R.id.tvInterestCount);
            tvNotInterest = (TextView) itemView.findViewById(R.id.tvNotInterestCount);

        }
    }

}
