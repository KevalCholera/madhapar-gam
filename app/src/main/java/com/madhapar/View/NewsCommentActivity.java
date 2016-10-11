package com.madhapar.View;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;
import com.madhapar.Presenter.PresenterClassSecond;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.CommentsListAdapter;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsCommentActivity extends AppCompatActivity implements CommentListCallback {
    @BindView(R.id.rvCommentList)
    RecyclerView rvCommentList;
    @BindView(R.id.toolbarComment)
    Toolbar toolbarComment;
    @BindView(R.id.etCommentText)
    EditText etCommentText;
    @BindView(R.id.tvSendComment)
    TextView tvSendComment;

    @OnClick(R.id.tvSendComment)
    public void sendComment() {

    }

    CommentsListAdapter commentAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RequestPresenter presenterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        super.setSupportActionBar(toolbarComment);
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
        UtilClass.changeActivity(NewsCommentActivity.this, MainActivity.class, true);
    }

    @Override
    public void onSuccessCommentList(JSONArray commentList) {
        commentAdapter = new CommentsListAdapter(this, commentList);
        rvCommentList.setLayoutManager(mLayoutManager);
        rvCommentList.setAdapter(commentAdapter);

    }

    @Override
    public void onFailRequest() {
        Log.e("commentListActivity", "response" + "failRequzsr");
    }

    @Override
    public void onFailResponse(String mesaage) {
        Log.e("commentListActivity", "response" + "failReuspdvnkl");
    }


}
