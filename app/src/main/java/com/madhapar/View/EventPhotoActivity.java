package com.madhapar.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.View.Adapter.CustomGrid;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventPhotoActivity extends AppCompatActivity implements EventPhotosInt {
    @BindView(R.id.grid)
    GridView grid;
    RequestPresenter presenterClass;
    private CustomGrid customGrid;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_photo);
        ButterKnife.bind(this);
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenterClass = new RequestPresenter();
        presenterClass.getEventPhoto(this);
        if (grid == null) {
            Log.e("Log ", "Here");
        } else Log.e("Log", "not null");
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
        finish();
    }

    @Override
    public void onSuccessEventPhotoList(JSONArray userList) {
      //  customGrid = new CustomGrid(this, userList);
        mLayoutManager = new LinearLayoutManager(this);
        grid.setAdapter(customGrid);

    }
}
