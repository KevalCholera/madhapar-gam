package com.madhapar.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.madhapar.View.Fragment.PhotoFragment;
import com.madhapar.View.PhotoActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 22/10/16.
 */

public class PhotoListCustomGridAdapter extends BaseAdapter {
    private JSONArray imageArray;
    private Context context;
    @BindView(R.id.ivImageListGrid)
    ImageView ivImageListGrid;
    @BindView(R.id.tvListAlbumName)
    TextView tvListAlbumName;
    @BindView(R.id.rlListAlbum)
    RelativeLayout rlListAlbum;
    private PhotoFragment photoFragment;
    private FragmentManager fragmentManager;
    private Boolean isFromGallary;
    private String albumName;

    public PhotoListCustomGridAdapter(Context context, String albumName, JSONArray imageArray, FragmentManager fm, boolean isFromGallary) {
        this.context = context;
        this.imageArray = imageArray;
        this.fragmentManager = fm;
        this.albumName = albumName;
        this.isFromGallary = isFromGallary;
    }

    @Override
    public int getCount() {
        return imageArray.length();
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.element_image_list, viewGroup, false);
        ButterKnife.bind(this, view);
        tvListAlbumName.setText(albumName);
        rlListAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFromGallary) {
                    if (photoFragment == null) {
                        photoFragment = new PhotoFragment();
                    }
                    if (photoFragment.getArguments() == null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("images", imageArray.toString());
                        bundle.putInt("imageArray", position);
                        photoFragment.setArguments(bundle);
                    }
                    fragmentManager.beginTransaction().replace(R.id.flGallaryMain, photoFragment).commit();
                } else {
                    Intent intent = new Intent(context, PhotoActivity.class);
                    intent.putExtra("images", imageArray.toString());
                    intent.putExtra("imageArray", position);
                    context.startActivity(intent);
                }
            }
        });
        Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + imageArray.optJSONObject(position).optString("eventImage")).placeholder(R.mipmap.ic_user_placeholder).error(R.mipmap.ic_user_placeholder).into(ivImageListGrid);
        return view;
    }
}
