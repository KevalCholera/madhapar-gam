package com.madhapar.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.ExpandableGridView;
import com.madhapar.View.Adapter.CustomGrid;
import com.madhapar.View.Adapter.ImageAdapter;
import com.madhapar.View.Adapter.PhotoListCustomGridAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 22/10/16.
 */

public class PhotoListFragment extends BaseFragment {
    @BindView(R.id.gvPhotoList)
    ExpandableGridView gvPhotoList;
    private PhotoListCustomGridAdapter photoListAdapter;
    private FragmentManager fm;
    @BindView(R.id.event_photo_place_holder)
    ImageView event_photo_place_holder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
        ButterKnife.bind(this, view);
        String images = getArguments().getString("images");
        fm = getFragmentManager();
        if (images != null) {
            try {
                JSONArray imageArray = new JSONArray(images);
                if (imageArray != null && imageArray.length() > 0) {
                    event_photo_place_holder.setVisibility(View.GONE);
                    gvPhotoList.setVisibility(View.VISIBLE);
                    photoListAdapter = new PhotoListCustomGridAdapter(getActivity(), imageArray, fm);
                    gvPhotoList.setAdapter(photoListAdapter);
                } else {
                    event_photo_place_holder.setVisibility(View.VISIBLE);
                    gvPhotoList.setVisibility(View.GONE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;
    }
}
