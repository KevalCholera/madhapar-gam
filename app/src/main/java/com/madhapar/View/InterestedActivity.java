package com.madhapar.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClassSecond;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.GoingrecyclerView;
import com.madhapar.View.Adapter.InterestedRecylerviewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InterestedActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewInterested)
    RecyclerView recyclerViewInterest;
    @BindView(R.id.toolbarInterested)
    Toolbar toolbarInterested;
    PresenterClassSecond presenterClassSecond;
    private RecyclerView.LayoutManager mLayoutManager;
    private InterestedRecylerviewAdapter interestedRecylerviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested);
        ButterKnife.bind(this);
        super.setSupportActionBar(toolbarInterested);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenterClassSecond = new PresenterClassSecond();
        interestedRecylerviewAdapter = new InterestedRecylerviewAdapter(this, presenterClassSecond.getInterestList());
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewInterest.setLayoutManager(mLayoutManager);
        recyclerViewInterest.setAdapter(interestedRecylerviewAdapter);
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
        UtilClass.changeActivity(InterestedActivity.this,MainActivity.class,true);    }
}
