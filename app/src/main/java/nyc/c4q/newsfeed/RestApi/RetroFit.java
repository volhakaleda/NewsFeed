package nyc.c4q.newsfeed.RestApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mohammadnaz on 2/5/18.
 */

public class RetroFit {
    private NewsFeedService newService;
    private static RetroFit instance = null;

    private RetroFit() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newService = retrofit.create(NewsFeedService.class);
    }

    public static RetroFit getInstance() {
        if (instance == null) {
            instance = new RetroFit();
        }
        return instance;
    }

    public NewsFeedService getNewsService() {
        return  newService;
    }

}