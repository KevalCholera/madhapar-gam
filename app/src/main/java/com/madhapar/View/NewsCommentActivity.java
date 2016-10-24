package com.madhapar.View;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.CommentsListAdapter;
import com.madhapar.View.Adapter.NewsLikeCommentUpdateCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsCommentActivity extends AppCompatActivity implements CommentListCallback,CommentViewInt, NewsLikeCommentUpdateCallback {
    @BindView(R.id.rvCommentList)
    RecyclerView rvCommentList;
    @BindView(R.id.etCommentText)
    EditText etCommentText;
    @BindView(R.id.tvSendComment)
    TextView tvSendComment;
    PresenterClass presenter;


    @OnClick(R.id.tvSendComment)
    public void sendComment() {
        if (!etCommentText.getText().toString().equalsIgnoreCase("")) {
            if (presenterClass == null)
                presenterClass = new RequestPresenter();
        }
        if (((String) tvSendComment.getTag()).equalsIgnoreCase("add")) {
            presenterClass.updateLikeComment(getIntent().getStringExtra("newsId"), "1", etCommentText.getText().toString(), this);
            presenter = new PresenterClass();
            presenter.commentCredential(etCommentText.getText().toString(),this);
        } else {
            presenterClass.updateComment(getIntent().getStringExtra("newsId"), (String) etCommentText.getTag(), etCommentText.getText().toString(), "1", this);
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
        finish();
    }

    @Override
    public void onSuccessCommentList(JSONArray commentList) {
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
    }

    @Override
    public void onFailRequest() {
        Log.e("commentListActivity", "response" + "failRequzsr");
    }

    @Override
    public void onFailResponse(String mesaage) {
        Log.e("commentListActivity", "response" + "failReuspdvnkl");
    }


    @Override
    public void successfulUpdateLike(JSONObject updateObj) {
        UtilClass.displyMessage(getString(R.string.msgCommentAdded), this, 0);
        etCommentText.setText("");
        tvSendComment.setTag("add");
        if (presenterClass == null) {
            presenterClass = new RequestPresenter();
        }
        presenterClass.getNewsCommentList(getIntent().getStringExtra("newsId"), getIntent().getStringExtra("newsStatusId"), this);
        // presenterClass.getNewsCommentList();
    }

    @Override
    public void failUpdateResponse(String message) {
        tvSendComment.setTag("add");
    }

    @Override
    public void failUpdateRequest() {
        tvSendComment.setTag("add");
    }


    public void updateComment(String newsComment, String newsStatus, String newsStatusId) {
        etCommentText.setTag(newsStatusId);
        tvSendComment.setTag("update");
        etCommentText.setText(newsComment);
    }


    @Override
    public void onCommentresult(int checkComment) {
        if(checkComment == UtilClass.CommentBlankError){
            UtilClass.displyMessage(getString(R.string.commentEmpty),NewsCommentActivity.this, Toast.LENGTH_SHORT);
        }
        else if(checkComment == UtilClass.CommentLenghtError){
            UtilClass.displyMessage(getString(R.string.commentLength),NewsCommentActivity.this,Toast.LENGTH_SHORT);
        }
    }
}
