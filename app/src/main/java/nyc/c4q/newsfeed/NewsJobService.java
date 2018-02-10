package nyc.c4q.newsfeed;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import nyc.c4q.newsfeed.backend.AppDatabase;
import nyc.c4q.newsfeed.backend.NewsFeedService;
import nyc.c4q.newsfeed.model.AllNews;
import nyc.c4q.newsfeed.model.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class NewsJobService extends JobService {

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

                            String author0 = articles.get(0).getAuthor();
                            String description0 = articles.get(0).getDescription();
                            String author1 =articles.get(1).getAuthor();
                            String description1 = articles.get(1).getDescription();
                            String author2 = articles.get(2).getAuthor();
                            String description2 = articles.get(2).getDescription();

                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(author0);
                            stringBuilder.append(",");
                            stringBuilder.append(description0);
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
