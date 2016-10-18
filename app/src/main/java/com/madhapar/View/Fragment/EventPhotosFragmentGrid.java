package com.madhapar.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.View.Adapter.CustomGrid;
import com.madhapar.View.EventPhotosInt;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by smartsense on 24/09/16.
 */

public class EventPhotosFragmentGrid extends BaseFragment {
    @BindView(R.id.radioGridPhtosBack)
    RadioButton radioGrid;
    @OnClick(R.id.radioGridPhtosBack)
    void gridBack(){
        Fragment fv = new EventPhotosFragment();
        FragmentManager fm = getFragmentManager();
        FrameLayout contentView = (FrameLayout) getActivity().findViewById(R.id.flMain);
        fm.beginTransaction().replace(contentView.getId(),fv).addToBackStack(null).commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pgotos_open, container, false);
        ButterKnife.bind(this, view);
        Log.e("fragment 2","open");
        return view;

    }
}
