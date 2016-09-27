package com.madhapar.View.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.smartsense.newproject.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by smartsense on 24/09/16.
 */

public class MainDrawerListAdapter extends BaseAdapter {
    private List<Integer> iconList;
    private Context context;

    public MainDrawerListAdapter(Context context, List<Integer> iconList) {
        this.iconList = iconList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return iconList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View convertView = view;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.element_drawer_main, viewGroup, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        if (viewHolder != null) {
            viewHolder.ivDrawerElement = (ImageView) convertView.findViewById(R.id.ivDrawerIcon);
            viewHolder.ivDrawerElement.setImageResource(iconList.get(position));
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView ivDrawerElement;
    }

}
