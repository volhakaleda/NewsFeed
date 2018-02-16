package nyc.c4q.newsfeed.model;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.newsfeed.Presenter.MainActivityPresenter;
import nyc.c4q.newsfeed.RetroPojos.Article;


/**
 * Created by mohammadnaz on 2/5/18.
 */

public class DatabaseInitializer{

    static AsyncCallBack asyncCallBack;

    public static void setAsyncCallBack(AsyncCallBack asyncCallBack)  {
        DatabaseInitializer.asyncCallBack = asyncCallBack;
    }

    //static List<TopNews> topNewsList= new ArrayList<>();

    public static void populateAsync(@NonNull final TopNewsDatabase dataBase, @NonNull final List<Article> resultsList) {
        PopulateDatabase task = new PopulateDatabase(dataBase, resultsList);
        task.execute();
    }

    public static void removeSpecificNews(@NonNull final TopNewsDatabase dataBase, @NonNull final TopNews topNews) {
        RemoveNews task = new RemoveNews(dataBase, topNews);
        task.execute();
    }

    public static List<TopNews> getAllNewsAsync(@NonNull final TopNewsDatabase dataBase) {
        getAllNews task = new getAllNews(dataBase);
        task.execute();
        return null;
    }

    private static void NewsResultsInput(TopNewsDatabase dataBase, List<Article> topnewsPojoList) {
        for (Article article : topnewsPojoList) {
            String author = article.getAuthor();
            String description = article.getDescription();
            String url = article.getUrl();
            String urlToImage = article.getUrlToImage();
            String publishedAt = article.getPublishedAt();
            addNews(dataBase, new TopNews(author, description, url, urlToImage, publishedAt));
        }
    }

    private static TopNews addNews(final TopNewsDatabase dataBase, TopNews topNews) {
        dataBase.getTopNewsDao().insertMultipleRecord(topNews);
        return topNews;
    }

    private static class PopulateDatabase extends AsyncTask<Void, Void, Void> {

        private final TopNewsDatabase database;
        private final List<Article> topnewsPojoList;

        PopulateDatabase(TopNewsDatabase dataBase, List<Article> topnewsPojoList) {
            this.database = dataBase;
            this.topnewsPojoList = topnewsPojoList;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            NewsResultsInput(database, topnewsPojoList);
            return null;
        }



    }

    private static class RemoveNews extends AsyncTask<Void, Void, Void> {

        private final TopNewsDatabase database;
        private final TopNews topNews;
        //Boolean done=false;

        RemoveNews(TopNewsDatabase dataBase, TopNews topNews) {
            this.database = dataBase;
            this.topNews = topNews;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            database.getTopNewsDao().deleteRecord(topNews);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            ///asyncCallBack.getTopNewsFromDataBaseOffLine(topNewsList);
        }

    }

    private static class getAllNews extends AsyncTask<Void, Void, Void> {

        final TopNewsDatabase database;
        List<TopNews> topNewsList= new ArrayList<>();

        getAllNews(TopNewsDatabase dataBase) {
            this.database = dataBase;
        }

        @Override
        protected Void doInBackground(Void... voids) {
           topNewsList= database.getTopNewsDao().fetchAll();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            database.close();
            asyncCallBack.getTopNewsFromDataBaseOffLine(topNewsList);
        }

    }

    public interface AsyncCallBack {
        void getTopNewsFromDataBaseOffLine(List<TopNews> topNewsList);
    }



}






