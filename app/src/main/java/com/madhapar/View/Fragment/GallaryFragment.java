package com.madhapar.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.EventPresenter;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.CustomGrid;
import com.madhapar.View.EventDetailCallback;


import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by smartsense on 24/09/16.
 */

public class GallaryFragment extends BaseFragment implements EventDetailCallback.EventListCallback {
    @BindView(R.id.radioAlbumSelector)
    RadioButton radioAlbumSelector;
    @BindView(R.id.radioImageSelector)
    RadioButton radioImageSelector;
    private JSONArray albumArray;
    private EventPresenter eventPresenter;


    private FragmentManager mFragmentManager;
    private AlbumFragment fragmentAlbum;
    private PhotoListFragment photoListFragment;


    @OnClick(R.id.radioImageSelector)
    void openImagePage() {
        if (photoListFragment == null) {
            photoListFragment = new PhotoListFragment();
        }
        if (albumArray != null) {
            if (photoListFragment.getArguments() == null) {
                Bundle bundle = new Bundle();
                JSONArray imageArray = albumArray.optJSONObject(0).optJSONArray("eventPhotos");
                bundle.putString("images", imageArray.toString());
                photoListFragment.setArguments(bundle);
            }
            mFragmentManager.beginTransaction().replace(R.id.flGallaryMain, photoListFragment).addToBackStack(null).commit();
        }
    }

    @OnClick(R.id.radioAlbumSelector)
    void openAlbumPage() {
        if (fragmentAlbum == null) {
            fragmentAlbum = new AlbumFragment();
        }
        if (albumArray != null) {
            if (fragmentAlbum.getArguments() == null) {
                Bundle bundle = new Bundle();
                bundle.putString("images", albumArray.toString());
                fragmentAlbum.setArguments(bundle);
            }
            mFragmentManager.beginTransaction().replace(R.id.flGallaryMain, fragmentAlbum).addToBackStack(null).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_photos, container, false);
        ButterKnife.bind(this, view);
        mFragmentManager = getChildFragmentManager();
        if (fragmentAlbum == null) {
            fragmentAlbum = new AlbumFragment();
        }
        if (eventPresenter == null) {
            eventPresenter = new EventPresenter();
        }
        eventPresenter.getEventList(this);
        return view;
    }

    @Override
    public void onSuccessEventList(JSONArray eventArray) {
        UtilClass.hideProgress();
        this.albumArray = eventArray;
        radioAlbumSelector.performClick();

//        customGrid = new CustomGrid(getActivity(), eventArray,fragmentManager);
//        gridAlbum.setAdapter(customGrid);

    }

    @Override
    public void onFailEventListRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), getActivity(), 0);
    }

    @Override
    public void onFailEventListResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, getActivity(), 0);
    }


}
