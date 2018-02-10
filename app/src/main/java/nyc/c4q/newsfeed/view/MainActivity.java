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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.newsfeed.Constants;
import nyc.c4q.newsfeed.R;
import nyc.c4q.newsfeed.RestApi.Util;
import nyc.c4q.newsfeed.model.DatabaseInitializer;
import nyc.c4q.newsfeed.model.TopNews;
import nyc.c4q.newsfeed.model.TopNewsDatabase;
import nyc.c4q.newsfeed.model.TopNewsViewModel;
import nyc.c4q.newsfeed.services.NewsJobService;


public class MainActivity extends AppCompatActivity implements DataAdapter.onClick {
    TopNewsViewModel viewModel;
    List<TopNews> topNewsList;
    List<TopNews> newsList;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DataAdapter dataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topNewsList = new ArrayList<>();
        newsList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final DataAdapter dataAdapter = new DataAdapter();
        recyclerView.setAdapter(dataAdapter);
        dataAdapter.setOnclick(MainActivity.this);

if(Util.isNetworkAvailable(getApplicationContext())) {


    JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
    jobScheduler.schedule(new JobInfo.Builder(Constants.LOAD_NEWS,
            new ComponentName(this, NewsJobService.class))
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            ///.setMinimumLatency(30000)
            .build());
}
else {
    DatabaseInitializer.getAllNewsAsync(TopNewsDatabase.getInstance(getApplicationContext()));
    DatabaseInitializer.setAsyncCallBack(new DatabaseInitializer.AsyncCallBack() {
        @Override
        public void getTopNewsFromDataBaseOffLine(List<TopNews> topNewsList) {
            newsList.addAll(topNewsList);
            dataAdapter.setTopNews(newsList);
        }

    });
}









        viewModel = ViewModelProviders.of(this).get(TopNewsViewModel.class);
        viewModel.getTopnewslist().observe(this, new Observer<List<TopNews>>() {
            @Override
            public void onChanged(@Nullable List<TopNews> topNews) {
                newsList.addAll(topNews);
                dataAdapter.setTopNews(newsList);

            }
        });
    }

    @Override
    public void onclicker(int p) {
        TopNews topNew4 = newsList.get(p);
        String url = topNew4.getUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
