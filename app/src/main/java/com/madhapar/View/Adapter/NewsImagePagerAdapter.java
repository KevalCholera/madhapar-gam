package com.madhapar.View.Adapter;

import android.content.Context;
import android.content.Intent;
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

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.madhapar.View.NewsDetailActivity;
import com.madhapar.View.PhotoActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartsense on 08/10/16.
 */

public class NewsImagePagerAdapter extends PagerAdapter {
    private JSONArray imageArray;
    private Context context;
//    Target target;
    private ImageView imageView;
    final List<Target> targets = new ArrayList<Target>();

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
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Log.e("imageArray", "called" + imageArray);
        View view = inflater.inflate(R.layout.element_news_pager_image, container, false);
        if (context instanceof NewsDetailActivity) {
            imageView = (ImageView) view.findViewById(R.id.ivNewsDetailImagePager);
        } else {
            imageView = (ImageView) view.findViewById(R.id.ivNewsImagePager);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putExtra("images", imageArray.toString());
                intent.putExtra("position", position);
                intent.putExtra("isNews", true);
                context.startActivity(intent);
            }

        });


        Target target = new
                Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        targets.remove(this);
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
                        targets.remove(this);
                        Log.e("onBitmap", "fail");

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {


                    }
                };
        targets.add(target);
        Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + imageArray.optJSONObject(position).optString("newsImg")).placeholder(R.mipmap.img_news_detail_place_holder).error(R.mipmap.img_news_detail_place_holder).into(target);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }

}
