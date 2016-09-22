package com.madhapar.View;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.smartsense.newproject.R;

/**
 * Created by smartsense on 22/09/16.
 */

public class LoginActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        super.setContentView(R.layout.activity_login);
    }
}
