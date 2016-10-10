package com.madhapar.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClassSecond;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.CommentsRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsActivity extends AppCompatActivity {
    @BindView(R.id.recycler_veiwComment)
    RecyclerView recyclerViewComment;
    @BindView(R.id.toolbarComment)
    Toolbar toolbarComment;
    CommentsRecyclerViewAdapter commentAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    PresenterClassSecond presenterClassSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        super.setSupportActionBar(toolbarComment);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenterClassSecond = new PresenterClassSecond();
        commentAdapter = new CommentsRecyclerViewAdapter(this,presenterClassSecond.getComments());
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewComment.setLayoutManager(mLayoutManager);
        recyclerViewComment.setAdapter(commentAdapter);
        if(commentAdapter == null){
            Log.e("adapter","set");
        }else {Log.e("Adapter","Null");}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        UtilClass.changeActivity(CommentsActivity.this,MainActivity.class,true);    }
}
