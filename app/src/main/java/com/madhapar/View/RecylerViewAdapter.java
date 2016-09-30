package com.madhapar.View;

import android.content.Context;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartsense.newproject.R;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 9/29/2016.
 */
public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder> {
JSONArray eventArry;

    private List<EventCalender> eventCalenders;

    public RecylerViewAdapter(Context context, JSONArray jsonArray)
    {
        this.eventArry=jsonArray;
    }

    public RecylerViewAdapter()
    {}


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_event,parent,false);
        ButterKnife.bind(eventView);
        return new MyViewHolder(eventView);
    }
    public RecylerViewAdapter(List<EventCalender>eventCalenderList){
        this.eventCalenders = eventCalenderList;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventCalender eventCalender = eventCalenders.get(position);
        holder.tvEventName.setText(eventCalender.getEventName());
        holder.tvAddress.setText(eventCalender.getAddress());
        holder.tvNotInterest.setText(eventCalender.getCantgo());
        holder.tvTime.setText(eventCalender.getDateEvent());
        holder.tvGoing.setText(eventCalender.getGoing());
        holder.tvInterest.setText(eventCalender.getInterest());
    }
    @Override
    public int getItemCount() {
        return eventCalenders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.tvEventName)
//        TextView tvEventName;
//        @BindView(R.id.tvTime)
//        TextView tvTime;
//        @BindView(R.id.tvAddress)
//        TextView tvAddress;
//        @BindView(R.id.tvGoingCount)
//        TextView tvGoing;
//        @BindView(R.id.tvInterestCount)
//        TextView tvInterest;
//        @BindView(R.id.tvNotInterestCount)
//        TextView tvNotInterest;
        TextView tvEventName,tvTime,tvAddress,tvGoing,tvInterest,tvNotInterest;

        public MyViewHolder(View itemView) {
            super(itemView);
           tvEventName = (TextView)itemView.findViewById(R.id.tvEventName);
            tvTime = (TextView)itemView.findViewById(R.id.tvTime);
            tvAddress = (TextView)itemView.findViewById(R.id.tvAddress);
            tvGoing = (TextView)itemView.findViewById(R.id.tvGoingCount);
            tvInterest = (TextView)itemView.findViewById(R.id.tvInterestCount);
            tvNotInterest = (TextView)itemView.findViewById(R.id.tvNotInterestCount);
            if(tvEventName!=null){
                Log.e("Textview","Get");
            }

        }
    }

}
