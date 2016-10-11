package com.madhapar.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.example.smartsense.newproject.R;
import com.madhapar.Util.UtilClass;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.View.Adapter.MainDrawerListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;


public class MainActivity extends BaseActivity implements ViewInt, View.OnClickListener {
    private PresenterClass presenter;
    private ViewInt mainViewInt;
    @BindView(R.id.dlMain)
    DrawerLayout dlMain;
    @BindView(R.id.lvDrawerMain)
    ListView lvDrawerMain;
    private MainDrawerListAdapter mainDrawerListAdapter;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        presenter = new PresenterClass();
        mainViewInt = this;
        ButterKnife.bind(this);
        //----drawer layout---//
        //  dlMain = (DrawerLayout) findViewById(R.id.dlMain);
        // lvDrawerMain = (ListView) findViewById(R.id.lvDrawerMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, dlMain, 0, 0);
        getSupportActionBar().setElevation(0);
        dlMain.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //---Drawer List---//
        mainDrawerListAdapter = new MainDrawerListAdapter(MainActivity.this, presenter.initMainDrawer());
        lvDrawerMain.setAdapter(mainDrawerListAdapter);


        //-----Fragment---//
        presenter.changeFragment(R.id.flMain, 0, MainActivity.this);
        lvDrawerMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                presenter.changeFragment(R.id.flMain, position, MainActivity.this);
                dlMain.closeDrawers();
                //startActivity(new Intent(MainActivity.this, LoginActivity.class));
                //finish();
            }
        });


    }

    @Override
    public void validationResult(int checkCode) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        if (presenter == null) {
            presenter = new PresenterClass();
        }

    }
}
