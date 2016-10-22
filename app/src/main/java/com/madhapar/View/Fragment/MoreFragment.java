package com.madhapar.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
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

    @OnClick(R.id.tvIntroHistory)
    public void introHistory() {
        Intent intent = new Intent(getActivity(), AboutUsActivity.class);
        intent.putExtra("isAboutUs", false);
        startActivity(intent);
    }

    @OnClick(R.id.tvFeedback)
    public void tvFeedback() {
        presenter.changeActivity(getActivity(), FeedbackActivity.class, false);
    }

    @OnClick(R.id.tvAboutUs)
    public void tvAboutUs() {
        Intent intent = new Intent(getActivity(), AboutUsActivity.class);

        intent.putExtra("isAboutUs", true);
        startActivity(intent);
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
        getActivity().findViewById(R.id.moreFragment);

        return view;
    }
}
