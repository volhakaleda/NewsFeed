package nyc.c4q.newsfeed.view;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.newsfeed.Constants;
import nyc.c4q.newsfeed.R;
import nyc.c4q.newsfeed.model.DatabaseInitializer;
import nyc.c4q.newsfeed.model.TopNews;
import nyc.c4q.newsfeed.model.TopNewsDatabase;
import nyc.c4q.newsfeed.model.TopNewsViewModel;
import nyc.c4q.newsfeed.services.NewsJobService;


public class MainActivity extends AppCompatActivity {
    TopNewsViewModel viewModel;
    List<TopNews> topNewsList;
    List<TopNews> newsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topNewsList = new ArrayList<>();
        newsList = new ArrayList<>();
        DatabaseInitializer.getAllNewsAsync(TopNewsDatabase.getInstance(getApplicationContext()));


        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(new JobInfo.Builder(Constants.LOAD_NEWS,
                new ComponentName(this, NewsJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                ///.setRequiredNetworkType(JobInfo.)
                .setPersisted(true)
                .build());




        viewModel = ViewModelProviders.of(this).get(TopNewsViewModel.class);
        viewModel.getTopnewslist().observe(this, new Observer<List<TopNews>>() {
            @Override
            public void onChanged(@Nullable List<TopNews> topNews) {
                topNewsList.addAll(topNews);
                //log(topNewsList);
            }

        });


        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        DatabaseInitializer.setAsyncCallBack(new DatabaseInitializer.AsyncCallBack() {
            @Override
            public void getTopNewsFromDataBaseOffLine(List<TopNews> topNewsList) {
                newsList.addAll(topNewsList);
                log(newsList);
            }

        });


    }

    public void log(List<TopNews> topNews) {
        for (int i = 0; i < topNews.size(); i++) {
            Log.e("so", topNews.get(i).getUrl());
        }
    }


}
