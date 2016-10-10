package com.madhapar.View.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.toolbox.ImageLoader;
import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;

/**
 * Created by smartsense on 08/10/16.
 */

public class NewsImagePagerAdapter extends PagerAdapter {
    private JSONArray imageArray;
    private Context context;
    Target target;
    private ImageView imageView;


    public NewsImagePagerAdapter(Context context, JSONArray imageArray) {
        this.context = context;
        this.imageArray = imageArray;
    }

    @Override
    public int getCount() {
        return imageArray.length();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.element_news_pager_image, container, false);
        imageView = (ImageView) view.findViewById(R.id.ivNewsImagePager);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pbImageLoading);

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    imageView.setBackground(drawable);
                } else {
                    imageView.setBackgroundDrawable(drawable);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("onBitmap", "fail");
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                progressBar.setVisibility(View.VISIBLE);

            }
        };
        Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + imageArray.optJSONObject(position).optString("newsImg")).into(target);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }

}
