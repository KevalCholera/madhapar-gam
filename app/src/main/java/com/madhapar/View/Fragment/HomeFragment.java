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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartsense.newproject.R;
import com.madhapar.Model.NewsObject;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.PushUtil.WakeLocker;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.NewsListAdapter;
import com.madhapar.View.HomeViewInt;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 22/09/16.
 */

public class HomeFragment extends BaseFragment implements HomeViewInt {
    private RequestPresenter requestPresenter;
    @BindView(R.id.rvNewsList)
    RecyclerView rvNewsList;
    @BindView(R.id.srlNewsList)
    SwipeRefreshLayout srlNewsList;
    private NewsListAdapter newsDataAdapter;
    private LinearLayoutManager mLayoutManager;
    public Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_home, container, false);
        ButterKnife.bind(this, view);
        if (UtilClass.isInternetAvailabel(getActivity())) {
            UtilClass.showProgress(getActivity(), getString(R.string.msgPleaseWait));
            requestPresenter = new RequestPresenter();
            requestPresenter.getNewsList(this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), getActivity(), 0);
        }
        hasOptionsMenu();
        srlNewsList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (UtilClass.isInternetAvailabel(getActivity())) {
                    UtilClass.showProgress(getActivity(), getString(R.string.msgPleaseWait));
                    if (requestPresenter == null) {
                        requestPresenter = new RequestPresenter();
                    }
                    requestPresenter.getNewsList(HomeFragment.this);
                    srlNewsList.setRefreshing(true);
                } else {
                    if (srlNewsList.isRefreshing()) {
                        srlNewsList.setRefreshing(false);
                    }
                    UtilClass.displyMessage(getString(R.string.msgCheckInternet), getActivity(), 0);
                }
            }
        });
        setHasOptionsMenu(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mContext = this.getActivity();
        getActivity().registerReceiver(pushReceiver, new IntentFilter(Constants.PushConstant.PushActionNews));
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_feed, menu);
        MenuItem filter = menu.findItem(R.id.action_filter);
        filter.setVisible(true);
        return;
    }

    @Override
    public void onResume() {
        if (UtilClass.isInternetAvailabel(getActivity())) {
            UtilClass.showProgress(getActivity(), getString(R.string.msgPleaseWait));
            requestPresenter = new RequestPresenter();
            requestPresenter.getNewsList(this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), getActivity(), 0);
        }
        super.onResume();
    }

    @Override
    public void onSuccessNewsList(List<NewsObject> newsList) {
        if (srlNewsList.isRefreshing()) {
            srlNewsList.setRefreshing(false);
        }
        UtilClass.hideProgress();
        if (newsDataAdapter == null) {
            newsDataAdapter = new NewsListAdapter(getActivity(), newsList, rvNewsList, mLayoutManager);
            rvNewsList.setLayoutManager(mLayoutManager);
            rvNewsList.setAdapter(newsDataAdapter);
        } else {
            newsDataAdapter.updateAdapter(newsList);
        }
    }

    @Override
    public void onFailRequest() {
        Log.e("onFailRequest", "newsList");
        if (srlNewsList.isRefreshing()) {
            srlNewsList.setRefreshing(false);
        }
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), getActivity(), 0);
    }

    @Override
    public void onFailResponse(String message) {
        Log.e("onFailResponse", "newsList");
        if (srlNewsList.isRefreshing()) {
            srlNewsList.setRefreshing(false);
        }
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, getActivity(), 0);
    }

    public BroadcastReceiver pushReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (srlNewsList.isRefreshing()) {
                srlNewsList.setRefreshing(false);
            }
            WakeLocker.acquire(context);
            if (requestPresenter == null) {
                requestPresenter = new RequestPresenter();
            }
            requestPresenter.getNewsList(HomeFragment.this);
            WakeLocker.release();
        }
    };

    @Override
    public void onDestroy() {
        if (srlNewsList.isRefreshing()) {
            srlNewsList.setRefreshing(false);
        }
        try {
            UtilClass.hideProgress();
            getActivity().unregisterReceiver(pushReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
