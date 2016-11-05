package com.madhapar.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.CommentListCallback;
import com.madhapar.View.NewsCommentActivity;
import com.mpt.storage.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/10/2016.
 */
public class CommentsListAdapter extends RecyclerSwipeAdapter<CommentsListAdapter.MyViewHolder> implements NewsLikeCommentUpdateCallback, CommentListCallback {
    private JSONArray commentListArry;
    private Context context;
    private RequestPresenter presenter;
    protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);
    String newsId;
    String newsStatusId;
    private NewsCommentActivity commentActivity;


    public CommentsListAdapter(Context context, JSONArray jsonArray, String newsId, final String newsStatusId, NewsCommentActivity activity) {
        this.commentListArry = jsonArray;
        this.context = context;
        this.newsStatusId = newsStatusId;
        this.newsId = newsId;
        this.commentActivity = activity;

    }

    @Override
    public CommentsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewComment = LayoutInflater.from(context).inflate(R.layout.element_comment, parent, false);
        ButterKnife.bind(this, viewComment);
        return new MyViewHolder(viewComment);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            final JSONObject commentObj = commentListArry.getJSONObject(position);
            JSONObject userObj = commentObj.optJSONObject("user");
            holder.tvCommentPersonName.setText(userObj.optString("userFirstName") + " " + userObj.optString("userLastName"));
            holder.tvComment.setText(commentObj.optString("newsComment"));
            if (!commentObj.optJSONObject("user").optString("userId").equalsIgnoreCase((SharedPreferenceUtil.getString(Constants.UserData.UserId, "")))) {
                holder.swipeLayout.setRightSwipeEnabled(false);
            }
            holder.tvCommentTime.setText(commentObj.optString("newsStatusDate"));
            holder.tvDeleteComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (UtilClass.isInternetAvailabel(context)) {
                        UtilClass.showProgress(context, context.getString(R.string.msgPleaseWait));
                        removeComment(commentObj.optString("newsStatusId"));
                        mItemManger.closeAllItems();
                    } else {
                        UtilClass.hideProgress();
                        UtilClass.displyMessage(context.getString(R.string.msgCheckInternet), context, 0);
                    }

                }

            });
            holder.tvEditComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.swipeLayout.close();
                    if (context instanceof NewsCommentActivity) {
                        commentActivity.updateComment(commentObj.optString("newsComment"), "2", commentObj.optString("newsStatusId"));
                    }
                    //   UtilClass.showProgress(context, context.getString(R.string.msgPleaseWait));

                }
            });

            mItemManger.bindView(holder.itemView, position);
            String imagePath = commentObj.optString("userProfilePic");
            if (imagePath != null)
                Picasso.with(context).load(Constants.RequestConstants.BaseUrlForImage + userObj.optString("userProfilePic")).error(R.drawable.ic_network_place_holder).placeholder(R.drawable.ic_network_place_holder).into(holder.civUserPic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return commentListArry.length();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public void successfulUpdateLike(JSONObject updateObj) {
        if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentAdded)) {
            UtilClass.displyMessage(context.getString(R.string.commnetAddad), context, 0);
        } else if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentEdited)) {
            UtilClass.displyMessage(context.getString(R.string.commnetUpdate), context, 0);
        } else if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentDeleted)) {
            UtilClass.displyMessage(context.getString(R.string.commnetDelete), context, 0);
        }

        if (UtilClass.isInternetAvailabel(context)) {
            if (presenter == null) {
                presenter = new RequestPresenter();
            }
            presenter.getNewsCommentList(newsId, newsStatusId, this);
        } else {
            UtilClass.hideProgress();
            UtilClass.displyMessage(context.getString(R.string.msgCheckInternet), context, 0);
        }
    }

    @Override
    public void failUpdateResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, context, 0);
    }

    @Override
    public void failUpdateRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), context, 0);

    }

    @Override
    public void onSuccessCommentList(JSONArray commentList) {
        UtilClass.hideProgress();
        if (commentList.length() > 0) {
            commentActivity.updateView(true);
            this.commentListArry = commentList;
            notifyDataSetChanged();
        } else {
            commentActivity.updateView(false);

        }

    }

    @Override
    public void onFailRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(context.getString(R.string.msgSomethigWentWrong), context, 0);
    }

    @Override
    public void onFailResponse(String mesaage) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(mesaage, context, 0);
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
        @BindView(R.id.tvDelete)
        TextView tvDeleteComment;
        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;
        @BindView(R.id.tvEditComment)
        TextView tvEditComment;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    private void removeComment(String newsStatusId) {
        if (presenter == null) {
            presenter = new RequestPresenter();
        }
        presenter.removeLike(newsStatusId, this);
    }

    public void updateCommentList(JSONArray updatedCommentArray) {
        this.commentListArry = updatedCommentArray;
        notifyDataSetChanged();
    }


}
