package com.madhapar.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
public class InterestedRecylerviewAdapter extends RecyclerView.Adapter<InterestedRecylerviewAdapter.MyViewHolder>{
    JSONArray interestedListArry;
    Context context;
    public InterestedRecylerviewAdapter(Context context, JSONArray jsonArray) {
        this.interestedListArry = jsonArray;
        Log.e("crete","list"+jsonArray.length());
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewGoing = LayoutInflater.from(context).inflate(R.layout.cardview_interested,parent,false);
        ButterKnife.bind(this,viewGoing);
        return new MyViewHolder(viewGoing);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            JSONObject obj1 = interestedListArry.getJSONObject(position);
            holder.tvInterestedPersonName.setText(obj1.optString("InterestedPersonName"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return interestedListArry.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvInterestedPersonName)
        TextView tvInterestedPersonName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
