package com.madhapar.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartsense.newproject.R;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 20/10/16.
 */

public class FundRaisingMoreFragment extends BaseFragment {
    @BindView(R.id.tvFundRaisingDescription)
    TextView tvFundRaisingDescription;
    private JSONObject detailObj;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_fund_raising_more, container, false);
        ButterKnife.bind(this, view);
        String description = getArguments().getString("projectDescription", "");
        tvFundRaisingDescription.setText(description);
        return view;
    }
}
