package com.madhapar.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smartsense.newproject.R;

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
    @OnTextChanged(R.id.etSearch)
    public void search(){
        if(etSearch.getText().length()>=1) {
            ic_search.setVisibility(View.GONE);
        }
        else {
            ic_search.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_network, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ic_search.setVisibility(View.VISIBLE);
    }
}
