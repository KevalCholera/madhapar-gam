package com.madhapar.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.PresenterClassSecond;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.GoingrecyclerView;
import com.madhapar.View.Adapter.MyNetworkAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoingActivity extends AppCompatActivity {
    @BindView(R.id.recycler_viewGoing)
    RecyclerView recyclerViewGoing;
    PresenterClassSecond presenterClass;
    private RecyclerView.LayoutManager mLayoutManager;
    public GoingrecyclerView goingrecyclerView;
    @BindView(R.id.toolbarGoing)
    Toolbar toolbarGoing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going);
        ButterKnife.bind(this);
        super.setSupportActionBar(toolbarGoing);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenterClass = new PresenterClassSecond();
        goingrecyclerView = new GoingrecyclerView(this, presenterClass.getGoingList());
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewGoing.setLayoutManager(mLayoutManager);
        recyclerViewGoing.setAdapter(goingrecyclerView);
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
        UtilClass.changeActivity(GoingActivity.this,MainActivity.class,true);    }
}
