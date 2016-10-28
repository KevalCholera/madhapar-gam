package com.madhapar.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.RequestPresenter;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
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

public class FilterActivity extends BaseActivity implements CatagoryCallback {
    @BindView(R.id.lvNewsFilter)
    ListView lvNewsFilter;
    @BindView(R.id.etNewsFilterSearch)
    EditText etNewsFilterSearch;
    private NewsCatagoryAdapter newsCatagoryAdapter;
    @BindView(R.id.tvClearCatagory)
    TextView tvClearCatagory;
    private RequestPresenter requestPresenter;
    @BindView(R.id.ic_search)
    ImageView ic_search;

    @OnClick(R.id.tvClearCatagory)
    void clearCatagory() {
        SharedPreferenceUtil.putValue(Constants.DifferentData.SelectedCatagory, "clear");
        SharedPreferenceUtil.save();
        setResult(RESULT_OK);
        finish();
    }


    @OnTextChanged(R.id.etNewsFilterSearch)
    void filterNews() {
        if (etNewsFilterSearch.getText().toString().length() > 0) {
            ic_search.setVisibility(View.GONE);
        } else {
            ic_search.setVisibility(View.VISIBLE);
        }
        if (newsCatagoryAdapter != null && newsCatagoryAdapter.getFilter() != null) {
            newsCatagoryAdapter.getFilter().filter(etNewsFilterSearch.getText().toString());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);
        ButterKnife.bind(this);
        if (UtilClass.isInternetAvailabel(this)) {
            if (requestPresenter == null) {
                requestPresenter = new RequestPresenter();
            }
            requestPresenter.getCatagoryList(this);
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


    @Override
    public void onSuccessCatagoryList(JSONArray catagories) {
        UtilClass.hideProgress();
        if (catagories != null) {
            if (catagories.length() > 0) {
                if (newsCatagoryAdapter == null) {
                    newsCatagoryAdapter = new NewsCatagoryAdapter(this, catagories);
                    lvNewsFilter.setAdapter(newsCatagoryAdapter);
                } else {
                    newsCatagoryAdapter.updateCatagoryList(catagories);
                }
            } else {

            }
        }
    }

    @Override
    public void onFailCatagoryListRequest() {
        UtilClass.hideProgress();
        UtilClass.displyMessage(getString(R.string.msgSomethigWentWrong), this, 0);
    }

    @Override
    public void onFailCatagoryResponse(String message) {
        UtilClass.hideProgress();
        UtilClass.displyMessage(message, this, 0);
    }
}
