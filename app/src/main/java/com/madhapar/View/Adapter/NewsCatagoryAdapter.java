package com.madhapar.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madhapar.R;
import com.madhapar.View.FilterActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartsense on 23/10/16.
 */

public class NewsCatagoryAdapter extends BaseAdapter implements Filterable {
    private FilterActivity activity;
    public JSONArray newsCatagoryList;
    private JSONArray tempList;
    private CatagoryFilter catagoryFilter;
    private List<String> selectedCatagorList;
    LayoutInflater inflater;


    public NewsCatagoryAdapter(FilterActivity activity, JSONArray catagoryArray) {
        this.newsCatagoryList = catagoryArray;
        this.activity = activity;
        this.tempList = catagoryArray;
        selectedCatagorList = new ArrayList<>();
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return newsCatagoryList.length();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.element_news_filter, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.ivSelectedCatagory = (ImageView) view.findViewById(R.id.ivSelectedCatagory);
            viewHolder.tvNewsCatagoryName = (TextView) view.findViewById(R.id.tvNewsCatagoryName);
            viewHolder.llNewsCatagory = (LinearLayout) view.findViewById(R.id.llNewsCatagory);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final JSONObject catagoryObj = newsCatagoryList.optJSONObject(i);
        viewHolder.tvNewsCatagoryName.setText(catagoryObj.optString("categoryName"));

        if (activity.filterList.contains(newsCatagoryList.optJSONObject(i).optString("categoryName"))) {
            viewHolder.ivSelectedCatagory.setVisibility(View.VISIBLE);

        } else {
            viewHolder.ivSelectedCatagory.setVisibility(View.GONE);

        }
//        if (SharedPreferenceUtil.getString(Constants.DifferentData.SelectedCatagory, "").equalsIgnoreCase(catagoryObj.optString("categoryName"))) {
//            ivSelectedCatagory.setVisibility(View.VISIBLE);
//        } else {
//            ivSelectedCatagory.setVisibility(View.GONE);
//        }
        viewHolder.llNewsCatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.filterList.contains(newsCatagoryList.optJSONObject(i).optString("categoryName"))) {
                    activity.filterList.remove(newsCatagoryList.optJSONObject(i).optString("categoryName"));
                    viewHolder.ivSelectedCatagory.setVisibility(View.GONE);
                } else {
                    activity.filterList.add(newsCatagoryList.optJSONObject(i).optString("categoryName"));
                    viewHolder.ivSelectedCatagory.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    private class ViewHolder {
        LinearLayout llNewsCatagory;
        TextView tvNewsCatagoryName;
        ImageView ivSelectedCatagory;

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
