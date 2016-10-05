package com.madhapar.View;

import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
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
import com.madhapar.View.Adapter.NotGoingRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotGoingActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewNotGoing)
    RecyclerView recyclerViewNotGoong;
    @BindView(R.id.toolbarCantgo)
    Toolbar toolbarNotGoing;
    PresenterClassSecond presenterClassSecond;
    private RecyclerView.LayoutManager mLayoutManager;
    NotGoingRecyclerViewAdapter notGoingRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_going);
        ButterKnife.bind(this);
        super.setSupportActionBar(toolbarNotGoing);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenterClassSecond = new PresenterClassSecond();
        notGoingRecyclerViewAdapter = new NotGoingRecyclerViewAdapter(this, presenterClassSecond.getNotGoingPersonName());
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewNotGoong.setLayoutManager(mLayoutManager);
        recyclerViewNotGoong.setAdapter(notGoingRecyclerViewAdapter);
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
        UtilClass.changeActivity(NotGoingActivity.this,MainActivity.class,true);    }
}
