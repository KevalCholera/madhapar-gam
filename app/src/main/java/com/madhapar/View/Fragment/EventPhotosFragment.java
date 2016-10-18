package com.madhapar.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.madhapar.Model.EventPhotosModel;
import com.madhapar.Model.EventPhotosModelInt;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.PresenterClassSecond;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.CustomGrid;
import com.madhapar.View.EventPhotoActivity;
import com.madhapar.View.EventPhotosInt;
import com.madhapar.View.FeedbackActivity;
import com.madhapar.View.LoginActivity;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by smartsense on 24/09/16.
 */

public class EventPhotosFragment extends BaseFragment implements EventPhotosInt {
    Fragment myFragment;
    @BindView(R.id.radioGridfragment)
    RadioButton radioGridFragment;
    @BindView(R.id.gridfragment)
    GridView gridFragment;
    @BindView(R.id.radioGridSmall)
    RadioButton radioButton;
    @BindView(R.id.ivPlaceHolder)
    ImageView ivPlaceHolder;
    Context context;
    private LinearLayoutManager mLayoutManager;
    @OnClick(R.id.radioGridfragment)
    public void gridClick(){
        gridFragment.setNumColumns(2);
    }
    RequestPresenter presenterClass;
    private CustomGrid customGrid;
    @OnClick(R.id.radioGridSmall)
    void clickGridSmall(){
        Fragment fv = new EventPhotosFragmentGrid();
        FragmentManager fm = getFragmentManager();
        FrameLayout contentView = (FrameLayout) getActivity().findViewById(R.id.flMain);
        fm.beginTransaction().replace(contentView.getId(),fv).addToBackStack(null).commit();
    }
    @OnItemClick(R.id.gridfragment)
    void onItemClick(){
        Log.e("Click","Item");
        Fragment fv = new EventPhotosFragmentGrid();
        FragmentManager fm = getFragmentManager();
        FrameLayout contentView = (FrameLayout) getActivity().findViewById(R.id.flMain);
        fm.beginTransaction().replace(contentView.getId(),fv).addToBackStack(null).commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_photos, container, false);
        ButterKnife.bind(this, view);
        presenterClass = new RequestPresenter();
        presenterClass.getEventPhoto(this);
        ivPlaceHolder.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onSuccessEventPhotoList(JSONArray userList) {
        customGrid = new CustomGrid(getActivity(),userList);
        mLayoutManager = new LinearLayoutManager(context);
        gridFragment.setAdapter(customGrid);
        if(gridFragment == null){
            ivPlaceHolder.setVisibility(View.VISIBLE);
        }
    }
}
