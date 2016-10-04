package com.madhapar.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.View.Adapter.RecylerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 24/09/16.
 */

public class EventFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private PresenterClass presenterClass;
    private RecylerViewAdapter recylerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this,view);
        presenterClass = new PresenterClass();
        recylerViewAdapter = new RecylerViewAdapter(getActivity(), presenterClass.getEventList());
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        mContext = this.getActivity();
        recyclerView.setAdapter(recylerViewAdapter);
        if (recyclerView != null) {
            Log.e("recyclerview", "Get");
        }
        else {
            Log.e("TextView", "null");
        }
        if (recyclerView != null) {
            Log.e("view", "Get");
        }
        else {
            Log.e("TextView", "null");
        }
        return view;
    }
}
