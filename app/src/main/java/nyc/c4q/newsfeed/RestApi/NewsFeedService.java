package nyc.c4q.newsfeed.RestApi;

import nyc.c4q.newsfeed.RetroPojos.AllNews;
import retrofit2.Call;
import retrofit2.http.GET;


public interface NewsFeedService {

    @GET("/v2/top-headlines?country=us&category=business&apiKey=101e17cefa6a44d09e098ad94f1a4acd")
    Call<AllNews> getArticles();
}
