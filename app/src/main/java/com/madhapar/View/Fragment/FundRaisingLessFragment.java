package com.madhapar.View.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    @BindView(R.id.tvGoalAmount)
    TextView tvGoalAmount;
    @BindView(R.id.tvRaisedAmount)
    TextView tvRaisedAmount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_fund_raising_less, container, false);
        ButterKnife.bind(this, view);
        float per = 0;
        if (getArguments().getString("projectDetail", "") != null) {
            String projectDetail = getArguments().getString("projectDetail", "");
            try {
                JSONObject projectDetailObj = new JSONObject(projectDetail);
                int goalValue = (int) projectDetailObj.optLong("projectTotalCost");
                tvGoalAmount.setText("₹" + goalValue);
                Log.e("value", "goalValue" + goalValue);
                int raisedValue = (int) projectDetailObj.optLong("projectTotalRaised");
                tvRaisedAmount.setText("₹" + raisedValue);
                Log.e("value", "raisedValue" + raisedValue);
                try {
                    per = (raisedValue * 100 / goalValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.e("per", "perValue" + per);
        RelativeLayout interiorLayout = (RelativeLayout) view.findViewById(R.id.interior);
        interiorLayout.setRotation(-90);
        int degree = (int) (per * 3.6);
        if (degree < 30) {
            degree = 30 + degree;
        }
        DifferentColorCircularBorder border = new DifferentColorCircularBorder(interiorLayout);
        border.addBorderPortion(getActivity(), Color.parseColor("#FFFFFF"), 0, 30);
        border.addBorderPortion(getActivity(), Color.parseColor("#27BB9B"), 30, degree);
        border.addBorderPortion(getActivity(), Color.parseColor("#97DECF"), degree, 330);
        border.addBorderPortion(getActivity(), Color.parseColor("#FFFFFF"), 330, 360);

        return view;
    }


}
