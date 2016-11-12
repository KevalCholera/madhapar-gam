package com.madhapar.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.madhapar.R;
import com.madhapar.Util.ExpandableGridView;
import com.madhapar.View.Adapter.CustomGrid;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 24/09/16.
 */
public class AlbumFragment extends BaseFragment {
    @BindView(R.id.gridAlbum)
    ExpandableGridView gridAlbum;
    private CustomGrid customGrid;
    private FragmentManager fragmentManager;
    @BindView(R.id.ivEventPhotoPlaceholder)
    ImageView ivEventPhotoPlaceholder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            String images = getArguments().getString("images");
            fragmentManager = getFragmentManager();
            if (images != null) {
                try {
                    JSONArray imageArray = new JSONArray(images);
                    if (imageArray != null && imageArray.length() > 0) {
                        gridAlbum.setVisibility(View.VISIBLE);
                        ivEventPhotoPlaceholder.setVisibility(View.GONE);
                        customGrid = new CustomGrid(getActivity(), imageArray, fragmentManager);
                        gridAlbum.setAdapter(customGrid);
                    } else {
                        gridAlbum.setVisibility(View.GONE);
                        ivEventPhotoPlaceholder.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return view;

    }



}
