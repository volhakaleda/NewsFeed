package nyc.c4q.newsfeed.services;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.newsfeed.R;
import nyc.c4q.newsfeed.RestApi.NewsFeedService;
import nyc.c4q.newsfeed.RestApi.RetroFit;
import nyc.c4q.newsfeed.RetroPojos.AllNews;
import nyc.c4q.newsfeed.RetroPojos.Article;
import nyc.c4q.newsfeed.model.DatabaseInitializer;
import nyc.c4q.newsfeed.model.TopNewsDatabase;
import nyc.c4q.newsfeed.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class NewsJobService extends JobService {
    List<Article> articlesList = new ArrayList<>();
    private NewsFeedService newsFeedService;
    //DatabaseInitializer databaseInitializer= new DatabaseInitializer();

    @Override
    public boolean onStartJob(final JobParameters params) {
        RetroFit retrofit = RetroFit.getInstance();
        newsFeedService = retrofit.getNewsService();
        Call<AllNews> news = newsFeedService.getArticles();
        news.enqueue(new Callback<AllNews>() {
            @Override
            public void onResponse(Call<AllNews> call, Response<AllNews> response) {
                articlesList = response.body().getArticles();
                DatabaseInitializer.populateAsync(TopNewsDatabase.getInstance(getApplicationContext()), articlesList);
                notification(articlesList.get(articlesList.size() - 1));
            }

            @Override
            public void onFailure(Call<AllNews> call, Throwable t) {

            }
        });
        jobFinished(params, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    public void notification(Article article) {


        int NOTIFICATION_ID = 555;

// Define an intent to trigger when notification is selected (in this case to open an activity)
        Intent intent = new Intent(this, MainActivity.class);

// Turn this into a PendingIntent
        int requestID = (int) System.currentTimeMillis(); // Unique requestID to differentiate between various notification with same notification ID
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // Cancel old intent and create new one
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, intent, flags);

// Attach the pendingIntent to a new notification using setContentIntent
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(article.getDescription())
                .setContentText(article.getPublishedAt())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true) // Hides the notification after its been selected
                .build();

// Get the notification manager system service
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// Setting a notification ID allows you to update the notification later on.
        notificationManager.notify(NOTIFICATION_ID, notification);


    }
}




