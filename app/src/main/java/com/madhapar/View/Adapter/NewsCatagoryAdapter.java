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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 23/10/16.
 */

public class NewsCatagoryAdapter extends BaseAdapter implements Filterable {
    private AppCompatActivity activity;
    public List<String> newsCatagoryList;
    @BindView(R.id.llNewsCatagory)
    LinearLayout llNewsCatagory;
    @BindView(R.id.tvNewsCatagoryName)
    TextView tvNewsCatagoryName;
    private List<String> tempList;
    @BindView(R.id.ivSelectedCatagory)
    ImageView ivSelectedCatagory;
    private CatagoryFilter catagoryFilter;


    public NewsCatagoryAdapter(AppCompatActivity activity, List<String> newsCatagoryList1) {
        this.newsCatagoryList = newsCatagoryList1;
        this.activity = activity;
        this.tempList = newsCatagoryList1;
    }

    @Override
    public int getCount() {
        return newsCatagoryList.size();
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
        tvNewsCatagoryName.setText(newsCatagoryList.get(i));
        if (SharedPreferenceUtil.getInt(Constants.DifferentData.SelectedCatagory, -10) == i) {
            ivSelectedCatagory.setVisibility(View.VISIBLE);
        } else {
            ivSelectedCatagory.setVisibility(View.GONE);
        }
        llNewsCatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceUtil.putValue(Constants.DifferentData.SelectedCatagory, i);
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


    private class CatagoryFilter extends Filter {
        NewsCatagoryAdapter adapter;

        public CatagoryFilter(NewsCatagoryAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<String> filteredList = new ArrayList<>();
            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).toLowerCase().contains(charSequence.toString().toLowerCase())) {
                    filteredList.add(tempList.get(i));
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adapter.newsCatagoryList = (List<String>) filterResults.values;
            adapter.notifyDataSetChanged();
        }
    }
}
