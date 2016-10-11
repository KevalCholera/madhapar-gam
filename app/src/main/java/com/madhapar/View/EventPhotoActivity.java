package com.madhapar.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.PresenterClassSecond;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventPhotoActivity extends AppCompatActivity {
    @BindView(R.id.grid)
    GridView grid;
    private PresenterClassSecond presenterClassSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_photo);
        ButterKnife.bind(this);
        if(grid == null){
        Log.e("Log ","Here");}
        else Log.e("Log","not null");

    }
}
