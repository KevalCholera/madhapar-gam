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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/5/2016.
 */
public class GoingrecyclerView extends RecyclerView.Adapter<GoingrecyclerView.MyViewHolder> {
    JSONArray goingListArry;
    Context context;
    public GoingrecyclerView(Context context, JSONArray jsonArray) {
        this.goingListArry = jsonArray;
        Log.e("crete","list"+jsonArray.length());
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewGoing = LayoutInflater.from(context).inflate(R.layout.card_going,parent,false);
        ButterKnife.bind(this,viewGoing);
        return new MyViewHolder(viewGoing);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            JSONObject obj1 = goingListArry.getJSONObject(position);
            holder.tvGoingPersonName.setText(obj1.optString("GoingPersonName"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return goingListArry.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvGoingPersonName)
        TextView tvGoingPersonName;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
