package com.madhapar.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Presenter.PresenterClassSecond;
import com.madhapar.Util.Constants;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.AboutUsActivity;
import com.madhapar.View.EventPhotoActivity;
import com.madhapar.View.FeedbackActivity;
import com.madhapar.View.FundRaisingActivity;
import com.madhapar.View.LoginActivity;
import com.madhapar.View.StatusListActivity;
import com.madhapar.View.NewsCommentActivity;
import com.mpt.storage.SharedPreferenceUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smartsense on 24/09/16.
 */

public class MoreFragment extends BaseFragment {
    Context context;
    private PresenterClass presenter;
    private PresenterClassSecond presenterClassSecond;
    Intent intent;

    @OnClick(R.id.tvIntroHistory)
    public void introHistory() {
        presenter.changeActivity(getActivity(), EventPhotoActivity.class, true);
        intent = new Intent();

    }
    @OnClick(R.id.tvFeedback)
    public void tvFeedback() {
        presenter.changeActivity(getActivity(), FeedbackActivity.class, false);
    }

    @OnClick(R.id.tvAboutUs)
    public void tvAboutUs() {
        presenter.changeActivity(getActivity(), AboutUsActivity.class, false);
    }
    @OnClick(R.id.tvFundRaising)
    public void tvFundRaising() {
        presenter.changeActivity(getActivity(), FundRaisingActivity.class, false);
    }

    @OnClick(R.id.tvSignOut)
    public void tvSignOut() {
        SharedPreferenceUtil.clear();
        SharedPreferenceUtil.save();
        presenter.changeActivity(getActivity(), LoginActivity.class, true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        presenter = new PresenterClass();
        return view;
    }
}
