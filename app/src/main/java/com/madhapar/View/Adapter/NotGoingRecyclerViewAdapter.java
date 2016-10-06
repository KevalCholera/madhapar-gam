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
public class NotGoingRecyclerViewAdapter extends RecyclerView.Adapter<NotGoingRecyclerViewAdapter.MyViewHolder> {
    JSONArray notGoingListArry;
    Context context;
    public NotGoingRecyclerViewAdapter(Context context, JSONArray jsonArray) {
        this.notGoingListArry = jsonArray;
        Log.e("crete","list"+jsonArray.length());
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewGoing = LayoutInflater.from(context).inflate(R.layout.cardview_notgoing,parent,false);
        ButterKnife.bind(this,viewGoing);
        return new MyViewHolder(viewGoing);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            JSONObject obj1 = notGoingListArry.getJSONObject(position);
            holder.tvpersonNotGoingName.setText(obj1.optString("NotGoingPersonName"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return notGoingListArry.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvPersonNotGoingName)
        TextView tvpersonNotGoingName;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
