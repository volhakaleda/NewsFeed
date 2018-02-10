package nyc.c4q.newsfeed;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.newsfeed.backend.AppDatabase;
import nyc.c4q.newsfeed.backend.NewsFeedService;
import nyc.c4q.newsfeed.controller.NewsAdapter;
import nyc.c4q.newsfeed.model.AllNews;
import nyc.c4q.newsfeed.model.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private List<Article> articles = new ArrayList<>();
    private static NewsAdapter newsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        db = AppDatabase.getAppDatabase(getBaseContext());
        newsAdapter = new NewsAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        AsyncTask<Void, Void, List<Article>> myAsyncTask = new AsyncTask<Void, Void, List<Article>>() {
            @Override
            protected List<Article> doInBackground(Void... voids) {

                articles = db.newsDao().getArticles();
                return articles;

            }

                @Override
                protected void onPostExecute (List<Article> articles) {

                if (articles == null || articles.isEmpty()) {


                    JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    jobScheduler.schedule(new JobInfo.Builder(Constants.LOAD_NEWS, new ComponentName(MainActivity.this, NewsJobService.class))
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                            .setPersisted(true)
                            .setMinimumLatency(30000)
                            .build());
                }
                else{
                    newsAdapter.setData(articles);
                    newsAdapter.notifyDataSetChanged();
                }

            }

        };
        myAsyncTask.execute();

    }

    public static class NewsJobService extends JobService {

        private List<Article> articles;
        private NewsFeedService newsFeedService;


        @Override
        public boolean onStartJob(final JobParameters params) {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            newsFeedService = retrofit.create(NewsFeedService.class);

            Call<AllNews> news = newsFeedService.getArticles();

            news.enqueue(new Callback<AllNews>() {
                @Override
                public void onResponse(Call<AllNews> call, Response<AllNews> response) {
                    articles = response.body().getArticles();

                    newsAdapter.setData(articles);
                    newsAdapter.notifyDataSetChanged();

                    jobFinished(params, true);


                    AsyncTask<Void, Void, Void> myAsyncTask = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {

                            AppDatabase db = AppDatabase.getAppDatabase(getBaseContext());
                            db.newsDao().addAll(articles);

                            return null;
                        }
                    };
                    myAsyncTask.execute();


                    String author1 =articles.get(3).getAuthor();
                    String description1 = articles.get(3).getDescription();
                    String author2 = articles.get(4).getAuthor();
                    String description2 = articles.get(4).getDescription();

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(author1 + " , " + description1);
                    stringBuilder.append(author2 + " , " + description2);

                    NewsNotification newsNotification = new NewsNotification();
                    newsNotification.startNotification(getBaseContext(), stringBuilder);

                }

                @Override
                public void onFailure(Call<AllNews> call, Throwable t) {
                    Log.d(TAG, "on Response: " + t.toString());

                }
            });

            return true;
        }

        @Override
        public boolean onStopJob(JobParameters params) {
            return false;
        }

    }

}
