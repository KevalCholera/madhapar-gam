package com.madhapar.View.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.EventPresenter;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.EventDetailCallback;
import com.madhapar.View.UpdatefragmetnView;


import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    public static GallaryFragment parentFragment;

    @OnClick(R.id.radioImageSelector)
    void openImagePage() {
        if (photoListFragment == null) {
            photoListFragment = new PhotoListFragment();
        }
        updateView(false);
        if (albumArray != null) {
            if (photoListFragment.getArguments() == null) {
                Bundle bundle = new Bundle();
                JSONObject firstAlbum = albumArray.optJSONObject(0);
                if (firstAlbum != null) {
                    JSONArray imageArray = firstAlbum.optJSONArray("eventPhotos");
                    bundle.putString("images", imageArray.toString());
                    bundle.putString("albumName", firstAlbum.optString("eventTitle"));
                    photoListFragment.setArguments(bundle);
                }
            }
            mFragmentManager.beginTransaction().replace(R.id.flGallaryMain, photoListFragment).addToBackStack(null).commit();
        }
    }

    @OnClick(R.id.radioAlbumSelector)
    void openAlbumPage() {
        if (fragmentAlbum == null) {
            fragmentAlbum = new AlbumFragment();
        }
        updateView(true);
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_photos, container, false);
        ButterKnife.bind(this, view);
        parentFragment = this;
        IntentFilter filter = new IntentFilter();
        filter.addAction("updateView");
        getActivity().registerReceiver(receiver, filter);
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
    public void onDestroy() {
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onSuccessEventList(JSONArray eventArray) {
        if (isAdded() && getActivity() != null) {
            UtilClass.hideProgress();
            this.albumArray = eventArray;
            radioAlbumSelector.performClick();
        }
//        customGrid = new CustomGrid(getActivity(), eventArray,fragmentManager);
//        gridAlbum.setAdapter(customGrid);

    }

    @Override
    public void onFailEventListRequest() {
        if (isAdded() && getActivity() != null) {
            UtilClass.hideProgress();
            UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), getActivity(), 0);
        }
    }

    @Override
    public void onFailEventListResponse(String message) {
        if (isAdded() && getActivity() != null) {
            UtilClass.hideProgress();
            UtilClass.displyMessage(message, getActivity(), 0);
        }
    }

    public void updateView(boolean isAlbumSelected) {
        if (isAlbumSelected) {
            radioAlbumSelector.setBackgroundResource(R.drawable.ic_grid_selected);
            radioImageSelector.setBackgroundResource(R.drawable.ic_tiled_selected);
        } else {
            radioAlbumSelector.setBackgroundResource(R.drawable.ic_grid);
            radioImageSelector.setBackgroundResource(R.drawable.ic_tiled);
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                intent.getAction().equalsIgnoreCase("updateView");
                updateView(intent.getBooleanExtra("isAlbumSelected", false));
            }
        }
    };


}
