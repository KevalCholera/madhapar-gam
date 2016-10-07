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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.NetworkListAdapter;
import com.madhapar.View.NetworkViewInt;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * Created by smartsense on 24/09/16.
 */

public class NetworkFragment extends BaseFragment implements NetworkViewInt {
    @BindView(R.id.ic_search)
    ImageView iVsearch;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.recyclerViewMyNetwork)
    RecyclerView rvNetworkList;
    @BindView(R.id.llEmptyUserList)
    LinearLayout llEmptyList;


    @OnTextChanged(R.id.etSearch)
    public void search() {
        if (networkListAdapter != null && networkListAdapter.getFilter() != null) {
            networkListAdapter.getFilter().filter(etSearch.getText().toString());
        }
        if (etSearch.getText().toString().length() >= 1) {
            iVsearch.setVisibility(View.GONE);
        } else {
            iVsearch.setVisibility(View.VISIBLE);
        }


    }


    RequestPresenter presenterClass;
    private NetworkListAdapter networkListAdapter;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_network, container, false);
        ButterKnife.bind(this, view);
        presenterClass = new RequestPresenter();
        presenterClass.getNetworkList(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        iVsearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessNetworkList(JSONArray userList) {
        networkListAdapter = new NetworkListAdapter(getActivity(), userList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvNetworkList.setLayoutManager(mLayoutManager);
        rvNetworkList.setAdapter(networkListAdapter);
    }

    @Override
    public void onFailNetworkListResponse(String message) {
        UtilClass.displyMessage(message, getActivity(), 0);
    }

    @Override
    public void onFailNetworkListRequest() {
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), getActivity(), 0);
    }
}
