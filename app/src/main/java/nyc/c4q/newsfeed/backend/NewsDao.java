package nyc.c4q.newsfeed.backend;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nyc.c4q.newsfeed.model.Article;

/**
 * Created by olgakoleda on 2/7/18.
 */

@Dao
public interface NewsDao {

    @Query("SELECT * FROM Article")
    List<Article> getArticles();

    @Insert
    void addAll (List<Article> articles);

}
