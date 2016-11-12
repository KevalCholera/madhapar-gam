package com.madhapar.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.madhapar.R;
import com.madhapar.Util.Constants;
import com.madhapar.View.Fragment.PhotoFragment;
import com.madhapar.View.PhotoActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by smartsense on 22/10/16.
 */

public class PhotoListCustomGridAdapter extends BaseAdapter {
    private JSONArray imageArray;
    private Context context;


    private PhotoFragment photoFragment;
    private FragmentManager fragmentManager;
    private Boolean isFromGallary;
    private String albumName;
    private DisplayMetrics displayMetrics;
    final List<Target> targets = new ArrayList<Target>();

    public PhotoListCustomGridAdapter(Context context, String albumName, JSONArray imageArray, FragmentManager fm, boolean isFromGallary) {
        this.context = context;
        this.imageArray = imageArray;
        this.fragmentManager = fm;
        this.albumName = albumName;
        this.isFromGallary = isFromGallary;
        displayMetrics = context.getResources().getDisplayMetrics();
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
        final ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.element_image_list, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvListAlbumName = (TextView) view.findViewById(R.id.tvListAlbumName);
            viewHolder.rlListAlbum = (RelativeLayout) view.findViewById(R.id.rlListAlbum);
            viewHolder.ivImageListGrid = (ImageView) view.findViewById(R.id.ivImageListGrid);
            viewHolder.llImageMain = (LinearLayout) view.findViewById(R.id.llImageMain);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.llImageMain.getLayoutParams().width = (displayMetrics.widthPixels - 40) / 2;
        viewHolder.llImageMain.getLayoutParams().height = (displayMetrics.widthPixels - 40) / 2;
        viewHolder.tvListAlbumName.setText(albumName);
        viewHolder.rlListAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFromGallary) {
                    Intent intent = new Intent(context, PhotoActivity.class);
                    intent.putExtra("images", imageArray.toString());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
//                    if (photoFragment == null) {
//                        photoFragment = new PhotoFragment();
//                    }
//                    if (photoFragment.getArguments() == null) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("images", imageArray.toString());
//                        bundle.putInt("position", position);
//                        photoFragment.setArguments(bundle);
//                    }
//                    fragmentManager.beginTransaction().replace(R.id.flGallaryMain, photoFragment).commit();
                } else {
                    Intent intent = new Intent(context, PhotoActivity.class);
                    intent.putExtra("images", imageArray.toString());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            }
        });
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                targets.remove(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.ivImageListGrid.setBackground(drawable);
                } else {
                    viewHolder.ivImageListGrid.setBackgroundDrawable(drawable);
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
        Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + imageArray.optJSONObject(position).optString("eventImage")).placeholder(R.mipmap.img_event_photo_place_holder).error(R.mipmap.img_event_photo_place_holder).into(target);
        return view;
    }

    public class ViewHolder {
        ImageView ivImageListGrid;
        TextView tvListAlbumName;
        RelativeLayout rlListAlbum;
        LinearLayout llImageMain;

    }
}


