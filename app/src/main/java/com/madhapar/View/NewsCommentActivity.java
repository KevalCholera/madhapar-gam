package com.madhapar.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.R;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.CommentsListAdapter;
import com.madhapar.View.Adapter.NewsLikeCommentUpdateCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class NewsCommentActivity extends AppCompatActivity implements CommentListCallback, CommentViewInt, NewsLikeCommentUpdateCallback {
    @BindView(R.id.rvCommentList)
    RecyclerView rvCommentList;
    @BindView(R.id.etCommentText)
    EditText etCommentText;
    @BindView(R.id.tvSendComment)
    TextView tvSendComment;
    PresenterClass presenter;
    @BindView(R.id.ivNoCommentsPlaceholder)
    ImageView ivNoCommentsPlaceholder;

    @OnFocusChange(R.id.etCommentText)
    void commentText() {
        etCommentText.setSelection(etCommentText.getText().length());
    }


    @OnClick(R.id.tvSendComment)
    public void sendComment() {
        if (!etCommentText.getText().toString().equalsIgnoreCase("") && etCommentText.getText().toString().length() < 200) {
            if (presenterClass == null) {
                presenterClass = new RequestPresenter();
            }
            if (((String) tvSendComment.getTag()).equalsIgnoreCase("add")) {
                presenterClass.updateLikeComment(getIntent().getStringExtra("newsId"), "1", etCommentText.getText().toString(), this);
                if (presenter == null) {
                    presenter = new PresenterClass();
                }
                presenter.commentCredential(etCommentText.getText().toString(), this);
            } else {
                presenterClass.updateComment(getIntent().getStringExtra("newsId"), (String) etCommentText.getTag(), etCommentText.getText().toString(), "1", this);
            }
        } else {
            if (TextUtils.isEmpty(etCommentText.getText().toString())) {
                UtilClass.displyMessage(getString(R.string.commentEmpty), this, 0);
            }
            if (etCommentText.getText().toString().length() > 200) {
                UtilClass.displyMessage(getString(R.string.commentValidationlessthan200), this, 0);
            }
        }
    }

    CommentsListAdapter commentAdapter;
    private LinearLayoutManager mLayoutManager;
    RequestPresenter presenterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        tvSendComment.setTag("add");
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenterClass = new RequestPresenter();
        if (getIntent() != null) {
            presenterClass.getNewsCommentList(getIntent().getStringExtra("newsId"), getIntent().getStringExtra("newsStatusId"), this);
        }
        mLayoutManager = new LinearLayoutManager(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent();
        backIntent.putExtra("newsId", getIntent().getStringExtra("newsId"));
        setResult(RESULT_OK, backIntent);
        UtilClass.closeKeyboard(this);
        finish();
    }

    @Override
    public void onSuccessCommentList(JSONArray commentList) {
        if (commentList.length() > 0) {
            updateView(true);
            if (commentAdapter == null) {
                commentAdapter = new CommentsListAdapter(this, commentList, getIntent().getStringExtra("newsId"), getIntent().getStringExtra("newsStatusId"), this);
                rvCommentList.setLayoutManager(mLayoutManager);
                commentAdapter.setMode(Attributes.Mode.Single);
                (commentAdapter).setMode(Attributes.Mode.Single);
                rvCommentList.setAdapter(commentAdapter);
            } else {
                commentAdapter.updateCommentList(commentList);
                if (commentList.length() > 0)
                    rvCommentList.smoothScrollToPosition(commentList.length() - 1);
            }
        } else {
            updateView(false);
        }
    }

    @Override
    public void onFailRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }

    @Override
    public void onFailResponse(String mesaage) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(mesaage, this, 0);
    }


    @Override
    public void successfulUpdateLike(JSONObject updateObj) {
        if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentAdded)) {
            UtilClass.displyMessage(getString(R.string.commnetAddad), this, 0);
        } else if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentEdited)) {
            UtilClass.displyMessage(getString(R.string.commnetUpdate), this, 0);
        } else if (updateObj.optString("message").equalsIgnoreCase(Constants.DifferentData.CommentDeleted)) {
            UtilClass.displyMessage(getString(R.string.commnetDelete), this, 0);
        }
        etCommentText.setText("");
        tvSendComment.setTag("add");
        if (presenterClass == null) {
            presenterClass = new RequestPresenter();
        }
        presenterClass.getNewsCommentList(getIntent().getStringExtra("newsId"), getIntent().getStringExtra("newsStatusId"), this);
    }

    @Override
    public void failUpdateResponse(String message) {
        tvSendComment.setTag("add");
        etCommentText.setText("");
        UtilClass.displyMessage(message, this, 0);
    }

    @Override
    public void failUpdateRequest() {
        tvSendComment.setTag("add");
        etCommentText.setText("");
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }


    public void updateComment(String newsComment, String newsStatus, String newsStatusId) {
        etCommentText.setTag(newsStatusId);
        tvSendComment.setTag("update");
        etCommentText.setText(newsComment + " ");

        etCommentText.setSelection(etCommentText.getText().length());

    }


    @Override
    public void onCommentresult(int checkComment) {
        if (checkComment == UtilClass.CommentBlankError) {
            UtilClass.displyMessage(getString(R.string.commentEmpty), NewsCommentActivity.this, Toast.LENGTH_SHORT);
        } else if (checkComment == UtilClass.CommentLenghtError) {
            UtilClass.displyMessage(getString(R.string.commentLength), NewsCommentActivity.this, Toast.LENGTH_SHORT);
        }
    }

    public void updateView(boolean listToDisplay) {
        if (listToDisplay) {
            ivNoCommentsPlaceholder.setVisibility(View.GONE);
            rvCommentList.setVisibility(View.VISIBLE);

        } else {
            ivNoCommentsPlaceholder.setVisibility(View.VISIBLE);
            rvCommentList.setVisibility(View.GONE);
        }
    }

}
