package com.madhapar.View.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/10/2016.
 */
public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.MyViewHolder> {
    JSONArray commentListArry;
    Context context;

    public CommentsListAdapter(Context context, JSONArray jsonArray) {
        this.commentListArry = jsonArray;
        this.context = context;
    }

    @Override
    public CommentsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewComment = LayoutInflater.from(context).inflate(R.layout.element_comment, parent, false);
        ButterKnife.bind(this, viewComment);
        return new MyViewHolder(viewComment);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            JSONObject commentObj = commentListArry.getJSONObject(position);
            JSONObject userObj = commentObj.optJSONObject("user");
            holder.tvCommentPersonName.setText(userObj.optString("userFirstName") + " " + userObj.optString("userLastName"));
            holder.tvComment.setText(commentObj.optString("newsComment"));
            holder.tvCommentTime.setText(commentObj.optString("newsStatusDate"));
            String imagePath = commentObj.optString("userProfilePic");
            if (imagePath != null)
                Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + userObj.optString("userProfilePic")).into(holder.civUserPic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return commentListArry.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCommentName)
        TextView tvCommentPersonName;
        @BindView(R.id.tvComment)
        TextView tvComment;
        @BindView(R.id.tvCommentTime)
        TextView tvCommentTime;
        @BindView(R.id.civUserPic)
        ImageView civUserPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void remove(int position) {
        commentListArry.remove(position);
        notifyItemRemoved(position);
    }


}
