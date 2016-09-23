package com.madhapar.View;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;


/**
 * Created by smartsense on 21/09/16.
 */

public class SubActivity extends BaseActivity {
    private Button btnSubMainActivity;
    private PresenterClass presenterClass;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_sub);

    }

}
