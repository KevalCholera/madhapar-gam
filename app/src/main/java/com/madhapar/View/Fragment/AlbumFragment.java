package com.madhapar.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.EventPresenter;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Util.ExpandableGridView;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.CustomGrid;
import com.madhapar.View.Adapter.ImageAdapter;
import com.madhapar.View.EventDetailCallback;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smartsense on 24/09/16.
 */

public class AlbumFragment extends BaseFragment {
    @BindView(R.id.gridAlbum)
    ExpandableGridView gridAlbum;
    private CustomGrid customGrid;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this, view);
        String images = getArguments().getString("images");
        fragmentManager = getFragmentManager();
        if (images != null) {
            try {
                JSONArray imageArray = new JSONArray(images);
                if (imageArray != null && imageArray.length() > 0) {
                    customGrid = new CustomGrid(getActivity(), imageArray, fragmentManager);
                    gridAlbum.setAdapter(customGrid);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;

    }


}
