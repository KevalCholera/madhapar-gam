package com.madhapar.View;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.madhapar.Presenter.PresenterClass;
import com.madhapar.R;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.Adapter.MainDrawerListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
public class MainActivity extends BaseActivity implements ViewInt, View.OnClickListener {
    private PresenterClass presenter;
    private ViewInt mainViewInt;
    @BindView(R.id.dlMain)
    DrawerLayout dlMain;
    @BindView(R.id.lvDrawerMain)
    ListView lvDrawerMain;
    private MainDrawerListAdapter mainDrawerListAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private boolean doubleBackToExitPressedOnce;

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
                                                    if (position == 0) {
                                                        getSupportActionBar().setTitle(getString(R.string.titlenews));
                                                    } else if (position == 1) {
                                                        getSupportActionBar().setTitle(getString(R.string.titleEvent));
                                                    } else if (position == 2) {
                                                        getSupportActionBar().setTitle(getString(R.string.titleProfile));
                                                    } else if (position == 3) {
                                                        getSupportActionBar().setTitle(getString(R.string.titleNetwork));
                                                    } else if (position == 4) {
                                                        getSupportActionBar().setTitle(getString(R.string.titlePhotos));
                                                    } else {
                                                        getSupportActionBar().setTitle(getString(R.string.titleMore));
                                                    }
                                                }
                                            }

        );

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

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (dlMain.isDrawerOpen(GravityCompat.START)) {
            dlMain.closeDrawer(GravityCompat.START);
        } else {
            dlMain.openDrawer(GravityCompat.START);
//            super.onBackPressed();
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        UtilClass.displyMessage("Please click BACK again to exit", this, 0);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
