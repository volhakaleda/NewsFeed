package nyc.c4q.newsfeed.view;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nyc.c4q.newsfeed.Presenter.DetailActivityPresenter;

/**
 * Created by mohammadnaz on 2/14/18.
 */

public class FragmentDAP extends Fragment implements DetailActivityPresenter.DAPView {

    DetailActivityPresenter dapView;///this is just an instance when the activity is created of the presenter
    //esstially i don't call DetailActivityPresenter DAP= new DetaiActivityPresenter DAP i first create the instance of the presenter in
    //in the Activity and then just set the Instane of the presenter here.

    public FragmentDAP(){

    }
    @Override
    public void setView(DetailActivityPresenter dapView) {
        this.dapView=dapView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///dapView.DAPMethod();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void helloworld() {

    }
}



