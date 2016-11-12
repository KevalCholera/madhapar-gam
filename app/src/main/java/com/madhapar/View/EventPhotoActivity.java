package com.madhapar.View;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.madhapar.R;
import com.madhapar.Util.ExpandableGridView;
import com.madhapar.View.Adapter.PhotoListCustomGridAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
public class EventPhotoActivity extends AppCompatActivity {
    private PhotoListCustomGridAdapter adapter;
    private FragmentManager fm;
    @BindView(R.id.grid)
    ExpandableGridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_photo);
        ButterKnife.bind(this);
        String eventPotos = getIntent().getStringExtra("eventPhotos");

        fm = getSupportFragmentManager();
        if (eventPotos != null) {
            try {
                JSONArray eventPhotoArray = new JSONArray(eventPotos);
                if (eventPhotoArray != null) {
                    adapter = new PhotoListCustomGridAdapter(this, getIntent().getStringExtra("albumName"), eventPhotoArray, fm, false);
                    grid.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


}
