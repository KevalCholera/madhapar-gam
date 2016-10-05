package com.madhapar.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.View.Adapter.MyNetworkAdapter;
import com.madhapar.View.Adapter.RecylerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by smartsense on 24/09/16.
 */

public class NetworkFragment extends BaseFragment {
    @BindView(R.id.ic_search)
    ImageView ic_search;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.recyclerViewMyNetwork)
    RecyclerView recyclerViewMyNetwork;
    @OnTextChanged(R.id.etSearch)
    public void search(){
        if(etSearch.getText().length()>=1) {
            ic_search.setVisibility(View.GONE);
        }
        else {
            ic_search.setVisibility(View.VISIBLE);
        }
    }
    PresenterClass presenterClass;
    private MyNetworkAdapter recylerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_network, container, false);
        ButterKnife.bind(this,view);
        presenterClass = new PresenterClass();
        recylerViewAdapter = new MyNetworkAdapter(getActivity(), presenterClass.getProfile());
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewMyNetwork.setLayoutManager(mLayoutManager);
        mContext = this.getActivity();
        recyclerViewMyNetwork.setAdapter(recylerViewAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ic_search.setVisibility(View.VISIBLE);
    }
}
