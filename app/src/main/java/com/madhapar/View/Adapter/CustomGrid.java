package com.madhapar.View.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.madhapar.View.Fragment.PhotoFragment;
import com.madhapar.View.Fragment.PhotoListFragment;
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
    JSONArray photoArray;
    Context context;
    @BindView(R.id.rlAlbum)
    RelativeLayout rlAlbum;
    @BindView(R.id.tvAlbumName)
    TextView tvAlbumName;
    @BindView(R.id.tvAlbumDate)
    TextView tvAlbumDate;
    private FragmentManager fm;
    private PhotoListFragment photoListFragment;

    @BindView(R.id.ivImageGrid)
    ImageView ivImageGrid;


    public CustomGrid(Context context, JSONArray photoArray, FragmentManager fm) {
        this.photoArray = photoArray;
        this.context = context;
        this.fm = fm;

    }

    @Override
    public int getCount() {
        return photoArray.length();
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.element_custom_eventphoto_grid, null);
        ButterKnife.bind(this, view);
        rlAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("images", photoArray.optJSONObject(position).optJSONArray("eventPhotos").toString());
                if (photoListFragment == null) {
                    photoListFragment = new PhotoListFragment();
                }
                photoListFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.flGallaryMain, photoListFragment).commit();
            }
        });
        tvAlbumName.setText(photoArray.optJSONObject(position).optString("eventTitle"));
        String date = photoArray.optJSONObject(position).optString("eventFromDate").substring(0, 12) + "-" + photoArray.optJSONObject(position).optString("eventToDate").substring(0, 12);
        tvAlbumDate.setText(date);
        Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + photoArray.optJSONObject(position).optString("coverImage")).placeholder(R.mipmap.ic_user_placeholder).error(R.mipmap.ic_user_placeholder).into(ivImageGrid);
        return view;
    }


}
