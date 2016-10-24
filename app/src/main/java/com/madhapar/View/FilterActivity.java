package com.madhapar.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Util.Constants;
import com.madhapar.View.Adapter.NewsCatagoryAdapter;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by smartsense on 22/10/16.
 */

public class FilterActivity extends BaseActivity {
    @BindView(R.id.lvNewsFilter)
    ListView lvNewsFilter;
    @BindView(R.id.etNewsFilterSearch)
    EditText etNewsFilterSearch;
    private List newsCatagoryList;
    private NewsCatagoryAdapter newsCatagoryAdapter;
    @BindView(R.id.tvClearCatagory)
    TextView tvClearCatagory;

    @OnClick(R.id.tvClearCatagory)
    void clearCatagory() {
        SharedPreferenceUtil.putValue(Constants.DifferentData.SelectedCatagory, -11);
        SharedPreferenceUtil.save();
        setResult(RESULT_OK);
        finish();
    }


    @OnTextChanged(R.id.etNewsFilterSearch)
    void filterNews() {
        if (newsCatagoryAdapter != null && newsCatagoryAdapter.getFilter() != null) {
            newsCatagoryAdapter.getFilter().filter(etNewsFilterSearch.getText().toString());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);
        ButterKnife.bind(this);
        newsCatagoryList = Arrays.asList(getResources().getStringArray(R.array.newsCatagory));
        if (newsCatagoryList != null) {
            newsCatagoryAdapter = new NewsCatagoryAdapter(this, newsCatagoryList);
            lvNewsFilter.setAdapter(newsCatagoryAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


}
