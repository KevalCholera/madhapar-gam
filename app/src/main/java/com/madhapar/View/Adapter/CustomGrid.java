package com.madhapar.View.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ronak on 10/11/2016.
 */
public class CustomGrid extends BaseAdapter {
    List<Integer> list1;
    Context context;

    private static LayoutInflater inflter = null   ;
    @BindView(R.id.ivImageGrid)
    ImageView ivImageGrid;
    @BindView(R.id.tvPhotoDate)
    TextView tvPhotoDate;

    public CustomGrid(Context context, List<Integer> list1) {
        this.list1 = list1;
        this.context=context;
        inflter = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return list1.get(i);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.element_custom_eventphoto_grid,null);
        ButterKnife.bind(this,view);
        //Integer id= list1.get(position);
        ivImageGrid.setImageDrawable(context.getDrawable(R.drawable.ic_os_notification_fallback_white_24dp));
//        ivImageGrid.setImageResource(id);
        return view;
    }


}
