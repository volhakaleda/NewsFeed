package nyc.c4q.newsfeed;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import nyc.c4q.newsfeed.backend.NewsFeedService;
import nyc.c4q.newsfeed.model.AllNews;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class NewsJobService extends JobService {

    private NewsFeedService newsFeedService;

    @Override
    public boolean onStartJob(JobParameters params) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newsFeedService = retrofit.create(NewsFeedService.class);

        Call<AllNews> news = newsFeedService.getArticles();
                    news.enqueue(new Callback<AllNews>() {
                        @Override
                        public void onResponse(Call<AllNews> call, Response<AllNews> response) {
                            Log.d(TAG,"onResponse: " + response.body().getArticles().get(9).getDescription());

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
