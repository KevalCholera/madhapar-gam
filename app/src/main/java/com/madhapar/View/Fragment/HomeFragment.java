package com.madhapar.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClassSecond;
import com.madhapar.View.Adapter.NewsDataRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 22/09/16.
 */

public class HomeFragment extends BaseFragment {
    private PresenterClassSecond presenterClassSecond;
    @BindView(R.id.recyclerViewMyHome)
    RecyclerView recyclerViewHome;
    private NewsDataRecyclerView newsDataRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_home, container, false);
        ButterKnife.bind(this,view);
        presenterClassSecond = new PresenterClassSecond();
        newsDataRecyclerView = new NewsDataRecyclerView(getActivity(), presenterClassSecond.getNewsData());
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewHome.setLayoutManager(mLayoutManager);
        mContext = this.getActivity();
        recyclerViewHome.setAdapter(newsDataRecyclerView);
        return view;

    }
}
