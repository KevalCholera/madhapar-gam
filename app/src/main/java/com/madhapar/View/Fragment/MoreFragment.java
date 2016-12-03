package com.madhapar.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madhapar.Presenter.PresenterClass;
import com.madhapar.R;
import com.madhapar.Util.Constants;
import com.madhapar.View.AboutUsActivity;
import com.madhapar.View.FeedbackActivity;
import com.madhapar.View.FundRaisingActivity;
import com.madhapar.View.LoginActivity;
import com.mpt.storage.SharedPreferenceUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smartsense on 24/09/16.
 */
public class MoreFragment extends BaseFragment {
    Context context;
    private PresenterClass presenter;
    Intent intent;

    @OnClick(R.id.llIntroHistory)
    public void introHistory() {
        Intent intent = new Intent(getActivity(), AboutUsActivity.class);
        intent.putExtra("isAboutUs", false);
        startActivity(intent);
    }

    @OnClick(R.id.llFeedback)
    public void tvFeedback() {
        presenter.changeActivity(getActivity(), FeedbackActivity.class, false);
    }

    @OnClick(R.id.llAboutUs)
    public void tvAboutUs() {
        Intent intent = new Intent(getActivity(), AboutUsActivity.class);

        intent.putExtra("isAboutUs", true);
        startActivity(intent);
    }

    @OnClick(R.id.llFundRaising)
    public void tvFundRaising() {
        presenter.changeActivity(getActivity(), FundRaisingActivity.class, false);
    }

    @OnClick(R.id.llSignOut)
    public void tvSignOut() {
        long registrationTime = SharedPreferenceUtil.getLong(Constants.UserData.UserRegistrationTime, 0);
        SharedPreferenceUtil.clear();
        SharedPreferenceUtil.putValue(Constants.UserData.UserRegistrationTime, registrationTime);
        SharedPreferenceUtil.save();
        presenter.changeActivity(getActivity(), LoginActivity.class, true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        presenter = new PresenterClass();
        getActivity().findViewById(R.id.moreFragment);

        return view;
    }
}
