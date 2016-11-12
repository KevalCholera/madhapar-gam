package com.madhapar.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madhapar.R;
import com.madhapar.View.Adapter.ImageAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by smartsense on 22/10/16.
 */

public class PhotoFragment extends BaseFragment {
    @BindView(R.id.vpPhotos)
    ViewPager vpPhotos;
    private ImageAdapter imageAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            String images = getArguments().getString("images");
            int selected = getArguments().getInt("position");
            if (images != null) {
                try {
                    JSONArray imageArray = new JSONArray(images);
                    if (imageArray != null && imageArray.length() > 0) {
                        imageAdapter = new ImageAdapter(getActivity(), imageArray, false);
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
        return view;
    }


}
