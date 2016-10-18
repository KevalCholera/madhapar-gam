package com.madhapar.View.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/11/2016.
 */
public class CustomGrid extends BaseAdapter {
    JSONArray eventPhotos ;
    Context context;

    private static LayoutInflater inflter = null   ;
    @BindView(R.id.ivImageGrid)
    ImageView ivImageGrid;
    @BindView(R.id.tvPhotoDate)
    TextView tvPhotoDate;

    public CustomGrid(Context context, JSONArray list1) {
        this.eventPhotos =  list1;
        this.context=context;
        inflter = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return eventPhotos.length();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.element_custom_eventphoto_grid,null);
        ButterKnife.bind(this,view);
        Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + eventPhotos.optJSONObject(position).optString("userProfilePic")).placeholder(R.mipmap.ic_user_placeholder).error(R.mipmap.ic_user_placeholder).into(ivImageGrid);
        return view;
    }


}
