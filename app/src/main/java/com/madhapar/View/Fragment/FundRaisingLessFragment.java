package com.madhapar.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madhapar.R;
import com.madhapar.Util.UtilClass;
import com.triggertrap.seekarc.SeekArc;

import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.seekArc)
    SeekArc seekArc;

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
                long goalValue = (int) projectDetailObj.optDouble("projectTotalCost");
                tvGoalAmount.setText("₹" + UtilClass.formatValue(projectDetailObj.optDouble("projectTotalCost")));
                long raisedValue = (int) projectDetailObj.optDouble("projectTotalRaised");
                tvRaisedAmount.setText(UtilClass.formatValue(projectDetailObj.optDouble("projectTotalRaised")) + " ");
                try {
                    per = (raisedValue * 100 / goalValue);
                    seekArc.setProgress((int) per);
                    seekArc.setEnabled(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return view;
    }


}
