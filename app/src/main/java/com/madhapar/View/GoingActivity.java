package com.madhapar.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClassSecond;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.GoingrecyclerView;
import com.madhapar.View.Adapter.InterestedRecylerviewAdapter;
import com.madhapar.View.Adapter.NotGoingRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoingActivity extends AppCompatActivity {
    @BindView(R.id.recycler_viewGoing)
    RecyclerView recyclerViewGoing;
    @BindView(R.id.tvToolbarTitleGoing)
    TextView tvToolbarTitleGoing;
    PresenterClassSecond presenterClass;
    private RecyclerView.LayoutManager mLayoutManager;
    public GoingrecyclerView goingrecyclerView;
    public InterestedRecylerviewAdapter interestedRecylerviewAdapter;
    public NotGoingRecyclerViewAdapter notGoingRecyclerViewAdapter;
    @BindView(R.id.toolbarGoing)
    Toolbar toolbarGoing;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going);
        ButterKnife.bind(this);
        super.setSupportActionBar(toolbarGoing);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenterClass = new PresenterClassSecond();
        intent = getIntent();
        int checkP = intent.getIntExtra(Constants.DifferentData.Check,1);
        if (checkP == Constants.DifferentData.GoingPersonName){
            goingrecyclerView = new GoingrecyclerView(this, presenterClass.getGoingList());
            mLayoutManager = new LinearLayoutManager(this);
            recyclerViewGoing.setLayoutManager(mLayoutManager);
            tvToolbarTitleGoing.setText("Going");
            recyclerViewGoing.setAdapter(goingrecyclerView);
        }
        else if(checkP == Constants.DifferentData.InterestedPersonName){
            interestedRecylerviewAdapter = new InterestedRecylerviewAdapter(this, presenterClass.getInterestList());
            mLayoutManager = new LinearLayoutManager(this);
            recyclerViewGoing.setLayoutManager(mLayoutManager);
            tvToolbarTitleGoing.setText("Interested");
            recyclerViewGoing.setAdapter(interestedRecylerviewAdapter);
        }
        else if(checkP == Constants.DifferentData.NotGoingPersonName){
            notGoingRecyclerViewAdapter = new NotGoingRecyclerViewAdapter(this, presenterClass.getNotGoingPersonName());
            mLayoutManager = new LinearLayoutManager(this);
            recyclerViewGoing.setLayoutManager(mLayoutManager);
            tvToolbarTitleGoing.setText("Not Going");
            recyclerViewGoing.setAdapter(notGoingRecyclerViewAdapter);
        }
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
