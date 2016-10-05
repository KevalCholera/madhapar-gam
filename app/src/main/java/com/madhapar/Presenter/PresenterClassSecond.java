package com.madhapar.Presenter;

import com.madhapar.Model.GoinListModel;
import com.madhapar.Model.InterestedListModel;
import com.madhapar.Model.NotGoinListModel;

import org.json.JSONArray;

/**
 * Created by Ronak on 10/5/2016.
 */
public class PresenterClassSecond implements PresenterClassSecondInt {
    private GoinListModel goinListModel;
    private InterestedListModel interestedListModel;
    private NotGoinListModel notGoinListModel;
    @Override
    public JSONArray getGoingList() {
        goinListModel = new GoinListModel();
        return goinListModel.getGoingList();
    }

    @Override
    public JSONArray getInterestList() {
        interestedListModel = new InterestedListModel();
        return interestedListModel.getInterestedList();
    }

    @Override
    public JSONArray getNotGoingPersonName() {
        notGoinListModel = new NotGoinListModel();
        return notGoinListModel.getNotGoingList();
    }
}
