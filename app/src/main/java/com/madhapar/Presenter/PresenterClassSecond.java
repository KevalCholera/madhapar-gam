package com.madhapar.Presenter;

import com.madhapar.Model.CommentModel;
import com.madhapar.Model.EventInfoModel;
import com.madhapar.Model.EventInfoModelInt;
import com.madhapar.Model.GoinListModel;
import com.madhapar.Model.InterestedListModel;
import com.madhapar.Model.NewsFeedModel;
import com.madhapar.Model.NotGoinListModel;
import com.madhapar.Model.ProfileDataModel;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Ronak on 10/5/2016.
 */
public class PresenterClassSecond implements PresenterClassSecondInt,EventInfoModelInt {
    private GoinListModel goinListModel;
    private InterestedListModel interestedListModel;
    private NotGoinListModel notGoinListModel;
    private NewsFeedModel newsFeedModel;
    private ProfileDataModel profileDataModel;
    private CommentModel commentModel;
    private EventInfoModel eventInfoModel;

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

    @Override
    public JSONArray getProfileData() {
        profileDataModel = new ProfileDataModel();
        return profileDataModel.getProfileData();
    }

    @Override
    public JSONArray getComments() {
        commentModel = new CommentModel();
        return commentModel.GetComments();
    }

    @Override
    public JSONObject getEventInfo() {

        return eventInfoModel.getEventIndo();
    }


    @Override
    public JSONObject getEventIndo() {
        eventInfoModel = new EventInfoModel();
        return  eventInfoModel.getEventIndo();
            }
}
