package com.madhapar.View.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 23/10/16.
 */

public class NewsCatagoryAdapter extends BaseAdapter implements Filterable {
    private AppCompatActivity activity;
    public JSONArray newsCatagoryList;
    @BindView(R.id.llNewsCatagory)
    LinearLayout llNewsCatagory;
    @BindView(R.id.tvNewsCatagoryName)
    TextView tvNewsCatagoryName;
    private JSONArray tempList;
    @BindView(R.id.ivSelectedCatagory)
    ImageView ivSelectedCatagory;
    private CatagoryFilter catagoryFilter;


    public NewsCatagoryAdapter(AppCompatActivity activity, JSONArray catagoryArray) {
        this.newsCatagoryList = catagoryArray;
        this.activity = activity;
        this.tempList = catagoryArray;
    }

    @Override
    public int getCount() {
        return newsCatagoryList.length();
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view1 = inflater.inflate(R.layout.element_news_filter, viewGroup, false);
        ButterKnife.bind(this, view1);
        final JSONObject catagoryObj = newsCatagoryList.optJSONObject(i);
        tvNewsCatagoryName.setText(catagoryObj.optString("categoryName"));
        if (SharedPreferenceUtil.getString(Constants.DifferentData.SelectedCatagory, "").equalsIgnoreCase(catagoryObj.optString("categoryName"))) {
            ivSelectedCatagory.setVisibility(View.VISIBLE);
        } else {
            ivSelectedCatagory.setVisibility(View.GONE);
        }
        llNewsCatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceUtil.putValue(Constants.DifferentData.SelectedCatagory, catagoryObj.optString("categoryName"));
                SharedPreferenceUtil.save();
                activity.setResult(Activity.RESULT_OK);
                activity.finish();
            }
        });
        return view1;
    }

    @Override
    public Filter getFilter() {
        if (catagoryFilter == null) {
            catagoryFilter = new CatagoryFilter(this);
        }
        return catagoryFilter;
    }

    public void updateCatagoryList(JSONArray catagoryArray) {
        this.newsCatagoryList = catagoryArray;
        this.tempList = catagoryArray;
        notifyDataSetChanged();
    }


    private class CatagoryFilter extends Filter {
        NewsCatagoryAdapter adapter;

        public CatagoryFilter(NewsCatagoryAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            JSONArray filterList = new JSONArray();
            for (int i = 0; i < tempList.length(); i++) {
                if (tempList.optJSONObject(i).optString("categoryName").toLowerCase().contains(charSequence.toString().toLowerCase())) {
                    filterList.put(tempList.optJSONObject(i));
                }
            }
            results.values = filterList;
            results.count = filterList.length();
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adapter.newsCatagoryList = (JSONArray) filterResults.values;
            adapter.notifyDataSetChanged();
        }
    }
}
