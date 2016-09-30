package com.madhapar.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.smartsense.newproject.R;
import com.madhapar.View.Adapter.MainDrawerListAdapter;
import com.madhapar.View.Adapter.RecylerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventCalenderActivity extends AppCompatActivity {
    private List<EventCalender> eventList = new ArrayList<>();
    private RecylerViewAdapter recylerViewAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calender);
        ButterKnife.bind(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recylerViewAdapter = new RecylerViewAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.e("Recyler", "view");
        recyclerView.setAdapter(recylerViewAdapter);
        prepareEventDate();
    }

    private void prepareEventDate() {
        EventCalender event = new EventCalender("Karatasi excersise book launch", "2016-09-30T21:50:00Z", "KICC Main hall", "0", "0", "0");
        eventList.add(event);

        event = new EventCalender("Diwali Festival", "2016-11-02T13:47:12Z", "B-510, Titanium City Center", "6", "0", "0");
        eventList.add(event);

        event = new EventCalender("Navratri", "2016-11-30T12:00:00Z", "Racecource", "10", "1", "1");
        eventList.add(event);

        recylerViewAdapter.notifyDataSetChanged();
    }
}
