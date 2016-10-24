package com.madhapar.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.smartsense.newproject.R;
import com.madhapar.View.Adapter.ImageAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 24/10/16.
 */

public class PhotoActivity extends BaseActivity {
    @BindView(R.id.vpPhotos)
    ViewPager vpPhotos;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_photos);
        ButterKnife.bind(this);
        String images = getIntent().getStringExtra("images");
        int selected = getIntent().getIntExtra("position", 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (images != null) {
            try {
                JSONArray imageArray = new JSONArray(images);
                if (imageArray != null && imageArray.length() > 0) {
                    imageAdapter = new ImageAdapter(this, imageArray);
                    vpPhotos.setAdapter(imageAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        vpPhotos.setCurrentItem(selected);
        vpPhotos.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
