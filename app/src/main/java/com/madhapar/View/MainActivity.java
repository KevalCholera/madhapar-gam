package com.madhapar.View;

import android.content.Intent;
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
    @BindView(R.id.dlMain)
    DrawerLayout dlMain;
    @BindView(R.id.lvDrawerMain)
    ListView lvDrawerMain;
    private ActionBarDrawerToggle drawerToggle;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        presenter = new PresenterClass();
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, dlMain, 0, 0);
        getSupportActionBar().setElevation(0);
        //noinspection deprecation
        dlMain.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        //---Drawer List---//
        MainDrawerListAdapter mainDrawerListAdapter = new MainDrawerListAdapter(MainActivity.this, presenter.initMainDrawer());
        lvDrawerMain.setAdapter(mainDrawerListAdapter);
        //-----Fragment---//
        presenter.changeFragment(R.id.flMain, 0, MainActivity.this);

        lvDrawerMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                dlMain.closeDrawers();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        presenter.changeFragment(R.id.flMain, position, MainActivity.this);

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
                }, 300);
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
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (presenter == null) {
            presenter = new PresenterClass();
        }
    }

    public void endPage() {
        if (doubleBackToExitPressedOnce) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {

            UtilClass.displyMessage("Press again to exit", this, 0);
            doubleBackToExitPressedOnce = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 3000);
        }
    }

    @Override
    public void onBackPressed() {
        if (dlMain.isDrawerOpen(GravityCompat.START))
            dlMain.closeDrawers();
        else {
            endPage();
        }
    }
}