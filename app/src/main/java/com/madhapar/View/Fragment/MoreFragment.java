package com.madhapar.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartsense.newproject.R;
import com.madhapar.Presenter.PresenterClass;
import com.madhapar.Util.UtilClass;
import com.madhapar.View.FeedbackActivity;
import com.madhapar.View.GoingActivity;
import com.madhapar.View.InterestedActivity;
import com.madhapar.View.LoginActivity;
import com.madhapar.View.NotGoingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smartsense on 24/09/16.
 */

public class MoreFragment extends BaseFragment {
    Context context;
    private PresenterClass presenter;
    @OnClick(R.id.tvIntroHistory)
    public void introHistory(){
        presenter.changeActivity(getActivity(), InterestedActivity.class,true);
    }
    @OnClick(R.id.tvEventPhotos)
    public void eventPhotos(){
        presenter.changeActivity(getActivity(), GoingActivity.class,true);
    }
    @OnClick(R.id.tvFeedback)
    public void tvFeedback(){
        presenter.changeActivity(getActivity(), FeedbackActivity.class,true);
    }
    @OnClick(R.id.tvAboutUs)
    public void tvAboutUs(){
        presenter.changeActivity(getActivity(), NotGoingActivity.class,true);
    }
    @OnClick(R.id.tvFundRaising)
    public void tvFundRaising(){
        UtilClass.displyMessage("Fund Raising",getContext(),Toast.LENGTH_SHORT);
    }
    @OnClick(R.id.tvSignOut)
    public void tvSignOut(){
        presenter.changeActivity(getActivity(), LoginActivity.class,true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container,false);
        ButterKnife.bind(this,view);
        presenter = new PresenterClass();
        return view;
    }
}
