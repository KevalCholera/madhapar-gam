package com.madhapar.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartsense.newproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/10/2016.
 */
public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.MyViewHolder> {
    JSONArray commentListArry;
    Context context;
    public CommentsRecyclerViewAdapter(Context context, JSONArray jsonArray) {
        this.commentListArry = jsonArray;
        Log.e("crete","list"+jsonArray.length());
        this.context=context;
    }
    @Override
    public CommentsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewComment = LayoutInflater.from(context).inflate(R.layout.element_comment,parent,false);
        ButterKnife.bind(this,viewComment);
        return new MyViewHolder(viewComment);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            JSONObject obj1 = commentListArry.getJSONObject(position);
            holder.tvCommentPersonName.setText(obj1.optString("CommentPersonName"));
            holder.tvComment.setText(obj1.optString("Comment"));
            holder.tvCommentTime.setText(obj1.optString("CommentTime"));
        }
        catch (JSONException e) {
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
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
