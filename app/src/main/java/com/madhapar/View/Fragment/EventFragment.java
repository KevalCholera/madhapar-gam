package com.madhapar.View.Fragment;

import android.app.Activity;
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
import android.widget.LinearLayout;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.EventPresenter;
import com.madhapar.PushUtil.WakeLocker;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.EventListAdapter;
import com.madhapar.View.EventDetailCallback;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 24/09/16.
 */

public class EventFragment extends BaseFragment implements EventDetailCallback.EventListCallback {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.rlEventList)
    SwipeRefreshLayout rlEventList;
    @BindView(R.id.llNewsListPlaceholder)
    LinearLayout llNewsListPlaceholder;
    private EventPresenter presenterClass;
    private EventListAdapter recylerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Context mContext;
    private static final int REQUEST_CODE_FOR_EVENT_STATUS = 102;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, view);
        presenterClass = new EventPresenter();
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
    public void onResume() {
        if (UtilClass.isInternetAvailabel(mContext)) {
            if (presenterClass == null) {
                presenterClass = new EventPresenter();
            }
            presenterClass.getEventList(EventFragment.this);
        }
        super.onResume();
    }

    public BroadcastReceiver pushReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            WakeLocker.acquire(context);
            if (presenterClass == null) {
                presenterClass = new EventPresenter();
            }
            presenterClass.getEventList(EventFragment.this);

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


    @Override
    public void onSuccessEventList(JSONArray eventArray) {
        UtilClass.hideProgress();
        if (rlEventList.isRefreshing()) {
            rlEventList.setRefreshing(false);
        }
        if (eventArray.length() > 0) {
            if (recylerViewAdapter == null) {
                recylerViewAdapter = new EventListAdapter(getActivity(), eventArray, getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(recylerViewAdapter);

            } else {
                recylerViewAdapter.updateAdapter(eventArray);
            }
        } else {
            updateViews(false);
        }
    }

    @Override
    public void onFailEventListRequest() {
        if (isAdded() && getActivity() != null) {
            UtilClass.hideProgress();
            if (rlEventList.isRefreshing()) {
                rlEventList.setRefreshing(false);
            }
            UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), getActivity(), 0);
        }
    }


    @Override
    public void onFailEventListResponse(String message) {
        if (isAdded() && getActivity() != null) {
            UtilClass.hideProgress();
            if (rlEventList.isRefreshing()) {
                rlEventList.setRefreshing(false);
            }
            UtilClass.displyMessage(message, getActivity(), 0);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CODE_FOR_EVENT_STATUS) {
                if (UtilClass.isInternetAvailabel(mContext)) {
                    if (presenterClass == null) {
                        presenterClass = new EventPresenter();
                    }
                    presenterClass.getEventList(EventFragment.this);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void updateViews(final boolean listVisible) {
        if (listVisible) {
            llNewsListPlaceholder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            llNewsListPlaceholder.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
