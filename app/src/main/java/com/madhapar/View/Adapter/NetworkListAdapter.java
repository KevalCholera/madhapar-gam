package com.madhapar.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Model.NewsObject;
import com.madhapar.Util.Constants;
import com.madhapar.View.NetworkViewInt;
import com.madhapar.View.ProfileEditActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/5/2016.
 */
public class NetworkListAdapter extends RecyclerView.Adapter<NetworkListAdapter.MyViewHolder> implements Filterable {
    public JSONArray profileArry;
    private Context context;
    private UserFilter filter;
    private JSONArray tempArray;


    public NetworkListAdapter(Context context, JSONArray jsonArray) {
        this.profileArry = jsonArray;
        this.tempArray = jsonArray;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewProfilePerson = LayoutInflater.from(context).inflate(R.layout.element_network_list, parent, false);
        ButterKnife.bind(this, viewProfilePerson);
        return new MyViewHolder(viewProfilePerson);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            final JSONObject userObj = profileArry.optJSONObject(position);
            String userFirstname = userObj.optString("userFirstName");
            String userLastname = userObj.optString("userLastName");
            String userProfessrion = userObj.optString("userProfession");
            holder.tvUserName.setText(userFirstname + " " + userLastname);
            holder.tvUserProfession.setText(userProfessrion.trim().equalsIgnoreCase("") ? "N/A" : userProfessrion);
            JSONObject locationObj = userObj.optJSONObject("userLocation");
            String userCity = "";
            if (locationObj != null && locationObj.has("locationName")) {
                userCity = locationObj.optString("locationName");
            }
            holder.llUserMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, ProfileEditActivity.class);
                    intent.putExtra("profileObj", userObj.toString());
                    context.startActivity(intent);
                }
            });
            holder.tvUserCity.setText(userCity.trim().equalsIgnoreCase("") ? "N/A" : userCity);
            Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + profileArry.optJSONObject(position).optString("userProfilePic")).placeholder(R.drawable.ic_network_place_holder).error(R.drawable.ic_network_place_holder).into(holder.ivUserPic);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.profileArry.length();
    }

    @Override
    public Filter getFilter() {
        if (this.filter == null)
            this.filter = new UserFilter(this);
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUserName)
        TextView tvUserName;
        @BindView(R.id.tvUserProfession)
        TextView tvUserProfession;
        @BindView(R.id.tvUserCity)
        TextView tvUserCity;
        @BindView(R.id.civUserPic)
        com.madhapar.Util.CircleImageView ivUserPic;
        @BindView(R.id.llUserMain)
        LinearLayout llUserMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class UserFilter extends Filter {
        NetworkListAdapter adapter;

        public UserFilter(NetworkListAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            JSONArray filterdArray = new JSONArray();
            FilterResults result = new FilterResults();
            if (tempArray != null) {
                for (int i = 0; i < tempArray.length(); i++) {
                    String name = tempArray.optJSONObject(i).optString("userFirstName").toLowerCase() + " " + tempArray.optJSONObject(i).optString("userLastName").toLowerCase();
                    if (name.contains(charSequence.toString().toLowerCase())) {
                        filterdArray.put(tempArray.optJSONObject(i));
                    }
                }
            }
            result.values = filterdArray;
            result.count = filterdArray.length();
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            JSONArray filterdArray = (JSONArray) filterResults.values;
            if (adapter != null && filterdArray != null) {
                adapter.profileArry = filterdArray;
                adapter.notifyDataSetChanged();
            }
        }
    }

}
