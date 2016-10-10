package com.madhapar.View.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartsense.newproject.R;
import com.madhapar.Model.EventCalenderModelInt;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.PushUtil.WakeLocker;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.RecylerViewAdapter;
import com.madhapar.View.EventListInt;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 24/09/16.
 */

public class EventFragment extends BaseFragment implements EventListInt {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.rlEventList)
    SwipeRefreshLayout rlEventList;
    private RequestPresenter presenterClass;
    private RecylerViewAdapter recylerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, view);
        presenterClass = new RequestPresenter();
        if (UtilClass.isInternetAvailabel(getActivity())) {
            UtilClass.showProgress(getActivity(), getString(R.string.msgPleaseWait));
            presenterClass.getEventList(EventFragment.this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), getActivity(), 0);
        }
        mContext = this.getActivity();
        mLayoutManager = new LinearLayoutManager(mContext);
        rlEventList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (UtilClass.isInternetAvailabel(getActivity())) {
                    rlEventList.setRefreshing(true);
                    presenterClass.getEventList(EventFragment.this);
                } else {
                    rlEventList.setRefreshing(false);
                    UtilClass.displyMessage(getString(R.string.msgCheckInternet), getActivity(), 0);
                }
            }
        });
        getActivity().registerReceiver(pushReceiver, new IntentFilter(Constants.PushConstant.PushActionEvent));
        return view;
    }

    @Override
    public void onSuccessEventList(JSONArray eventArray) {
        UtilClass.hideProgress();
        if (rlEventList.isRefreshing()) {
            rlEventList.setRefreshing(false);
        }
        if (recylerViewAdapter == null) {
            recylerViewAdapter = new RecylerViewAdapter(getActivity(), eventArray);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(recylerViewAdapter);

        } else {
            recylerViewAdapter.updateAdapter(eventArray);
        }

    }

    @Override
    public void onFailEventList(String errorMessage) {
        UtilClass.hideProgress();
        if (rlEventList.isRefreshing()) {
            rlEventList.setRefreshing(false);
        }
        UtilClass.displyMessage(errorMessage, getActivity(), 0);

    }

    public BroadcastReceiver pushReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            WakeLocker.acquire(context);
            if (presenterClass == null) {
                presenterClass = new RequestPresenter();
                presenterClass.getEventList(EventFragment.this);
            }
            WakeLocker.release();

        }
    };

    @Override
    public void onDestroy() {
        try {
            getActivity().unregisterReceiver(pushReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
