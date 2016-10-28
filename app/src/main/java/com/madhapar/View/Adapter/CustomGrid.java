package com.madhapar.View.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Application.MadhaparGamApp;
import com.madhapar.Util.Constants;
import com.madhapar.View.Fragment.PhotoListFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;

import java.util.ArrayList;
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
    @BindView(R.id.llAlbumMain)
    LinearLayout llAlbumMain;
    final List<Target> targets = new ArrayList<Target>();

    private FragmentManager fm;
    private PhotoListFragment photoListFragment;
    @BindView(R.id.ivImageGrid)
    ImageView ivImageGrid;
    private DisplayMetrics displayMetrics;

    public CustomGrid(Context context, JSONArray photoArray, FragmentManager fm) {
        this.photoArray = photoArray;
        this.context = context;
        this.fm = fm;
        displayMetrics = context.getResources().getDisplayMetrics();

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
        Log.e("photoArray", photoArray.toString());
        ButterKnife.bind(this, view);
        // llAlbumMain.getLayoutParams().height = (displayMetrics.widthPixels - 10) / 2;
        llAlbumMain.getLayoutParams().width = (displayMetrics.widthPixels - 40) / 2;
        llAlbumMain.getLayoutParams().height = (displayMetrics.widthPixels - 40) / 2;
        rlAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                intent.setAction("updateView");
                intent.putExtra("isAlbumSelected", false);
                MadhaparGamApp.getAppInstance().sendBroadcast(intent);
                // albumFragment.updateView(true);
                bundle.putString("images", photoArray.optJSONObject(position).optJSONArray("eventPhotos").toString());
                bundle.putString("albumName", photoArray.optJSONObject(position).optString("eventTitle"));
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
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                targets.remove(this);
                Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ivImageGrid.setBackground(drawable);
                } else {
                    ivImageGrid.setBackgroundDrawable(drawable);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                targets.remove(this);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        targets.add(target);
        Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + photoArray.optJSONObject(position).optString("coverImage")).placeholder(R.mipmap.ic_user_placeholder).error(R.mipmap.ic_user_place_holder).into(target);
        return view;
    }
}
