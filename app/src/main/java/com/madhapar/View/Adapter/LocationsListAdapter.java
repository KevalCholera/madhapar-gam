package com.madhapar.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 21/10/16.
 */

public class LocationsListAdapter extends RecyclerView.Adapter<LocationsListAdapter.LocationViewHolder> implements Filterable {
    private JSONArray locationList;
    private AppCompatActivity activity;
    private LocationFilter filter;
    private JSONArray tempArray;

    public LocationsListAdapter(AppCompatActivity activity, JSONArray locations) {
        this.activity = activity;
        this.locationList = locations;
        this.tempArray = locations;

    }

    @Override
    public LocationsListAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.element_location_list, parent, false);
        ButterKnife.bind(this, view);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, final int position) {
        holder.tvLocationCity.setText(locationList.optJSONObject(position).optString("locationName"));
        holder.llLocationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent();
                backIntent.putExtra("isSelected", true);
                backIntent.putExtra("selectedCity", locationList.optJSONObject(position).toString());
                activity.setResult(Activity.RESULT_OK, backIntent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.length();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new LocationFilter(this);
        }
        return filter;
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvLocationCity)
        TextView tvLocationCity;
        @BindView(R.id.llLocationLayout)
        LinearLayout llLocationLayout;


        public LocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private class LocationFilter extends Filter {
        LocationsListAdapter locationAdapter;

        public LocationFilter(LocationsListAdapter locationAdapter) {
            this.locationAdapter = locationAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            JSONArray filteredArray = new JSONArray();
            for (int i = 0; i < tempArray.length(); i++) {
                if (tempArray.optJSONObject(i).optString("locationName").toLowerCase().contains(charSequence.toString().toLowerCase())) {
                    filteredArray.put(tempArray.optJSONObject(i));
                }
            }
            results.count = filteredArray.length();
            results.values = filteredArray;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            locationAdapter.locationList = (JSONArray) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public void updateLocationList(JSONArray locationList) {

        this.locationList = locationList;
        notifyDataSetChanged();
    }


}
