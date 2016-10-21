package com.madhapar.View.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.DifferentColorCircularBorder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 20/10/16.
 */

public class FundRaisingLessFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_fund_raising_less, container, false);
        ButterKnife.bind(this, view);
        if (getArguments().getString("projectDetail", "") != null) {
            String projectDetail = getArguments().getString("projectDetail", "");
            try {
                JSONObject projectDetailObj = new JSONObject(projectDetail);
                int goalValue = (int) projectDetailObj.optLong("projectTotalCost");
                Log.e("value", "goalValue" + goalValue);
                int raisedValue = (int) projectDetailObj.optLong("projectTotalRaised");
                Log.e("value", "raisedValue" + raisedValue);
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        float per = 50;
        RelativeLayout interiorLayout = (RelativeLayout) view.findViewById(R.id.interior);
        DifferentColorCircularBorder border = new DifferentColorCircularBorder(interiorLayout);
        border.addBorderPortion(getActivity(), Color.parseColor("#97DECF"), 100, 250);
        border.addBorderPortion(getActivity(), Color.parseColor("#27BB9B"), 290, 100);

        return view;
    }


}
