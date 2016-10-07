package com.madhapar.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
public class NetworkListAdapter extends RecyclerView.Adapter<NetworkListAdapter.MyViewHolder> {
    JSONArray profileArry;
    Context context;

    public NetworkListAdapter(Context context, JSONArray jsonArray) {
        this.profileArry = jsonArray;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewProfilePerson = LayoutInflater.from(context).inflate(R.layout.element_network_list, parent, false);
        ButterKnife.bind(this, viewProfilePerson);
        return new MyViewHolder(viewProfilePerson);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            JSONObject obj1 = profileArry.getJSONObject(position);
            holder.tvName.setText(obj1.optString("personName"));
            holder.tvQualification.setText(obj1.optString("qualification"));
            holder.tvCity.setText(obj1.optString("personCity"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return profileArry.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvQualification)
        TextView tvQualification;
        @BindView(R.id.tvCity)
        TextView tvCity;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
