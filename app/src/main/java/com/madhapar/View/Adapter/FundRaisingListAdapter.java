package com.madhapar.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.FundRaisingDetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by smartsense on 20/10/16.
 */

public class FundRaisingListAdapter extends RecyclerView.Adapter<FundRaisingListAdapter.FundRaisingViewHolder> {
    private JSONArray projectList;
    private Context context;

    public FundRaisingListAdapter(Context context, JSONArray projectsList) {
        this.context = context;
        this.projectList = projectsList;
    }

    @Override
    public FundRaisingListAdapter.FundRaisingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_fund_raising_cardview, parent, false);
        ButterKnife.bind(this, view);
        return new FundRaisingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FundRaisingViewHolder holder, int position) {
        final JSONObject projectObj = projectList.optJSONObject(position);
        if (projectObj != null) {
            Log.e("project", "obj" + projectObj);
            holder.tvFundRaisingProjectName.setText(projectObj.optString("projectName"));
            JSONObject location = projectObj.optJSONObject("newsLocation");
            if (location != null) {
                holder.tvFundRaisingProjectAddress.setText(location.optString("locationName"));
            }
            String projectDate = projectObj.optString("projectFromDate") + " - " + projectObj.optString("projectToDate");
            holder.tvFundRaisingProjectDate.setText(projectDate);
            try {
                holder.tvFundRaisingProjectGoal.setText("Goal : " + "₹ " + UtilClass.formatValue(projectObj.optDouble("projectTotalCost")));
                holder.tvFundRaisingProjectRaised.setText("Raised : " + "₹ " + UtilClass.formatValue(projectObj.optDouble("projectTotalRaised")));

            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.llFundRaisingProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FundRaisingDetailActivity.class);
                    intent.putExtra("projectDetail", projectObj.toString());
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return projectList.length();
    }

    public void updateAdapter(JSONArray projectList) {
        this.projectList = projectList;
        notifyDataSetChanged();
    }

    public class FundRaisingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFundRaisingProjectName)
        TextView tvFundRaisingProjectName;
        @BindView(R.id.tvFundRaisingProjectAddress)
        TextView tvFundRaisingProjectAddress;
        @BindView(R.id.tvFundRaisingProjectDate)
        TextView tvFundRaisingProjectDate;
        @BindView(R.id.tvFundRaisingProjectGoal)
        TextView tvFundRaisingProjectGoal;
        @BindView(R.id.tvFundRaisingProjectRaised)
        TextView tvFundRaisingProjectRaised;
        @BindView(R.id.llFundRaisingProject)
        LinearLayout llFundRaisingProject;


        public FundRaisingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
