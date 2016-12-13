package com.madhapar.View.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madhapar.Model.NewsObject;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.PushUtil.WakeLocker;
import com.madhapar.R;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.NewsListAdapter;
import com.madhapar.View.FilterActivity;
import com.madhapar.View.HomeViewInt;
import com.madhapar.View.NewsDetailViewInt;
import com.mpt.storage.SharedPreferenceUtil;

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
    @BindView(R.id.llNewsListPlaceholder)
    LinearLayout llNewsListPlaceholder;
    private NewsListAdapter newsDataAdapter;
    private LinearLayoutManager mLayoutManager;
    public Context mContext;
    private Activity activity;
    private static final int CATAGORY_REQUEST_CODE = 120;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_home, container, false);
        ButterKnife.bind(this, view);
        if (UtilClass.isInternetAvailabel(getActivity())) {
            UtilClass.showProgress(getActivity(), getString(R.string.msgPleaseWait));
            if (UtilClass.checkForRatingDialog()) {
                openAletDialogForRating();
            }
            requestPresenter = new RequestPresenter();
            requestPresenter.getNewsList(this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), getActivity(), 0);
        }


        this.activity = getActivity();
        SharedPreferenceUtil.putValue(Constants.DifferentData.SelectedCatagory, "clear");
        SharedPreferenceUtil.save();
        hasOptionsMenu();

        srlNewsList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isAdded() && activity != null) {
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
            }
        });

        if (UtilClass.isInternetAvailabel(getActivity())) {
            UtilClass.showProgress(getActivity(), getString(R.string.msgPleaseWait));
            requestPresenter = new RequestPresenter();
            requestPresenter.getNewsList(this);
        } else {
            UtilClass.displyMessage(getString(R.string.msgCheckInternet), getActivity(), 0);
        }
        setHasOptionsMenu(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mContext = this.getActivity();
        getActivity().registerReceiver(pushReceiver, new IntentFilter(Constants.PushConstant.PushActionNews));
        rvNewsList.setNestedScrollingEnabled(true);
        return view;
    }

    private void openAletDialogForRating() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = LayoutInflater.from(getActivity());
        View view = inflator.inflate(R.layout.alert_version, null, false);
        dialogBuilder.setView(view);
        TextView tvDialogOk = (TextView) view.findViewById(R.id.tvVersionDialogUpdate);
        TextView tvDialogTitle = (TextView) view.findViewById(R.id.tvVersionDialgTitle);
        tvDialogTitle.setVisibility(View.GONE);
        TextView tvDialogMessage = (TextView) view.findViewById(R.id.tvVersionDialogMessage);
        tvDialogMessage.setText("Would you like to rate us to app on play store ?");
        TextView tvDialogCancel = (TextView) view.findViewById(R.id.tvVersionDialgCancel);
        tvDialogOk.setText("Ok");
        tvDialogCancel.setText("Cancel");
        tvDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
                UtilClass.updateRegistrationTime(10);
            }
        });
        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilClass.updateRegistrationTime(5);
                dialog.dismiss();
            }
        });
        dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.alert_dialog_white_border);
        dialog.setCancelable(false);
        dialog.show();


    }

    @Override
    public void onStart() {
        getActivity().invalidateOptionsMenu();
        if (isAdded() && activity != null) {
            if (UtilClass.isInternetAvailabel(activity)) {
                requestPresenter = new RequestPresenter();
                requestPresenter.getNewsList(this);
            } else {
                UtilClass.displyMessage(getString(R.string.msgCheckInternet), activity, 0);
            }
            super.onStart();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter || item.getItemId() == R.id.action_filter_filled) {
            if (isAdded() && activity != null)
                startActivityForResult(new Intent(activity, FilterActivity.class), CATAGORY_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_feed, menu);
        MenuItem filter = menu.findItem(R.id.action_filter);
        MenuItem filterFilled = menu.findItem(R.id.action_filter_filled);
        if (SharedPreferenceUtil.getString(Constants.DifferentData.SelectedCatagory, "clear").equalsIgnoreCase("clear")) {
            filter.setVisible(true);
            filterFilled.setVisible(false);
        } else {
            filterFilled.setVisible(true);
            filter.setVisible(false);
        }

        return;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onSuccessNewsList(List<NewsObject> newsList) {
        if (srlNewsList.isRefreshing()) {
            srlNewsList.setRefreshing(false);
        }
        requestPresenter.getNewsDetail("1", new NewsDetailViewInt() {
            @Override
            public void onSuccessNewsDetail(NewsObject newsObject) {

            }

            @Override
            public void onFailRequest() {

            }

            @Override
            public void onFailResponse(String message) {

            }
        });
        UtilClass.hideProgress();
        if (newsList.size() > 0) {
            if (isAdded() && activity != null) {
                if (newsDataAdapter == null) {
                    srlNewsList.setVisibility(View.VISIBLE);
                    llNewsListPlaceholder.setVisibility(View.GONE);
                    newsDataAdapter = new NewsListAdapter(activity, newsList, rvNewsList, mLayoutManager, this, llNewsListPlaceholder);
                    rvNewsList.setLayoutManager(mLayoutManager);
                    rvNewsList.setAdapter(newsDataAdapter);
                } else {
                    newsDataAdapter.updateAdapter(newsList);
                    applyFilter();
                }
            }
        } else {

            updateViews(false);
//            srlNewsList.setVisibility(View.GONE);
//            llNewsListPlaceholder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailRequest() {
        if (isAdded() && activity != null) {

            if (srlNewsList.isRefreshing()) {
                srlNewsList.setRefreshing(false);
            }
            UtilClass.hideProgress();
            UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), activity, 0);
        }
    }

    @Override
    public void onFailResponse(String message) {
        if (isAdded() && activity != null) {
            if (srlNewsList.isRefreshing()) {
                srlNewsList.setRefreshing(false);
            }
            UtilClass.hideProgress();
            UtilClass.displyMessage(message, activity, 0);
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CATAGORY_REQUEST_CODE) {
                if (newsDataAdapter != null && newsDataAdapter.getFilter() != null) {
                    applyFilter();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void applyFilter() {
        String selectedValue = SharedPreferenceUtil.getString(Constants.DifferentData.SelectedCatagory, "clear");
        if (selectedValue.equalsIgnoreCase("clear")) {
            newsDataAdapter.updateAdapter();
        } else {
            newsDataAdapter.getFilter().filter(selectedValue);
        }
    }

    public void updateViews(final boolean listVisible) {
        if (listVisible) {
            llNewsListPlaceholder.setVisibility(View.GONE);
            rvNewsList.setVisibility(View.VISIBLE);
        } else {
            llNewsListPlaceholder.setVisibility(View.VISIBLE);
            rvNewsList.setVisibility(View.GONE);
        }
    }
}
