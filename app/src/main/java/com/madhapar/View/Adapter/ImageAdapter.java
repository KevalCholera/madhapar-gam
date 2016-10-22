package com.madhapar.View.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.madhapar.Util.TouchImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

/**
 * Created by smartsense on 22/10/16.
 */

public class ImageAdapter extends PagerAdapter {
    Context context;
    JSONArray imageArray;

    public ImageAdapter(Context context, JSONArray imageArray) {
        this.imageArray = imageArray;
        Log.e("adapterCreated", "imageArraty" + imageArray);
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageArray.length();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RelativeLayout photoLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams photoLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        photoLayout.setLayoutParams(photoLayoutParams);
        photoLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        photoLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        TouchImageView touchImageView = new TouchImageView(context);
        touchImageView.setLayoutParams(photoLayoutParams);
        Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + imageArray.optJSONObject(position).optString("eventImage")).placeholder(R.mipmap.ic_user_placeholder).error(R.mipmap.ic_user_placeholder).into(touchImageView);
        photoLayout.addView(touchImageView);
        container.addView(photoLayout);
        return photoLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
