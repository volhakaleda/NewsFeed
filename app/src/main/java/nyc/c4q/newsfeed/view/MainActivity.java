package nyc.c4q.newsfeed.view;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import nyc.c4q.newsfeed.Constants;
import nyc.c4q.newsfeed.Presenter.MainActivityPresenter;
import nyc.c4q.newsfeed.R;
import nyc.c4q.newsfeed.RestApi.Util;
import nyc.c4q.newsfeed.model.DatabaseInitializer;
import nyc.c4q.newsfeed.model.TopNews;
import nyc.c4q.newsfeed.model.TopNewsDatabase;
import nyc.c4q.newsfeed.model.TopNewsViewModel;
import nyc.c4q.newsfeed.services.NewsJobService;


public class MainActivity extends AppCompatActivity
        implements DataAdapter.onClick, MainActivityPresenter.MainActivityView {


    private final static String TAG = MainActivity.class.getName();
    TopNewsViewModel viewModel;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DataAdapter dataAdapter;
    MainActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /// DatabaseInitializer databaseInitializer= new DatabaseInitializer();
       /// DatabaseInitializer.populateAsync();


        presenter = new MainActivityPresenter(MainActivity.this,
                TopNewsDatabase.getInstance(this));

        Log.e(TAG, "onCreate: presenter created");


        recyclerView = findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setHasFixedSize(true);
        dataAdapter = new DataAdapter();
        dataAdapter.setOnclickListener(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(dataAdapter);


        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(new JobInfo.Builder(Constants.LOAD_NEWS,
                new ComponentName(this, NewsJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                ///.setMinimumLatency(30000)
                .build());

        if (Util.isNetworkAvailable(this)) {
            viewModel = ViewModelProviders.of(this).get(TopNewsViewModel.class);
            viewModel.getTopnewslist().observe(this, new Observer<List<TopNews>>() {
                @Override
                public void onChanged(List<TopNews> topNews) {
                    presenter.sendNewDatatoPresentertoSendBackToTheRecylcerView(topNews);
                }
            });
        } else {
            presenter.getDataBaseData();
        }
    }

    @Override
    public void onclickOnRecyclerView(int p) {
        presenter.getPositionofRecyclerView(p);
    }

    @Override
    public void addData(List<TopNews> topNewsList) {
        dataAdapter.setTopNews(topNewsList);
        dataAdapter.notifyDataSetChanged();
    }

    @Override
    public void sendDataToListner(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


    //lifecycle

    @Override
    protected void onPause() {
        presenter.Detach();
        // presenter=null;
        Log.e(TAG, "onPause: presenter Detached");
        super.onPause();
    }

    @Override
    protected void onResume() {
        //if(presenter==null) {
        presenter.Attach(MainActivity.this, TopNewsDatabase.getInstance(getApplicationContext()));
        Log.e(TAG, "onResume: prsenter attached");

        super.onResume();
    }

    @Override
    protected void onStop() {
        presenter.Detach();
        Log.e(TAG, "onStop: Detach called on presenter");

        super.onStop();
    }


    @Override
    protected void onDestroy() {
        presenter.Detach();
        //presenter=null;
        Log.e(TAG, "onDestroy: Detach called on presenter");
        super.onDestroy();
    }

    public void H() {

    }
}

