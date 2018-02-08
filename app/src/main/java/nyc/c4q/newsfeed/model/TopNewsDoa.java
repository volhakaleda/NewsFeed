package nyc.c4q.newsfeed.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by mohammadnaz on 2/5/18.
 */
@Dao
public interface TopNewsDoa {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleRecord(TopNews... topNews);

    @Insert
    void insertMultipleListRecord(List<TopNews> topNewsList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOnlySingleRecord(TopNews topNews);

    @Query("SELECT * FROM TopNews")
    LiveData<List<TopNews>> fetchAllData();

    @Query("SELECT * FROM TopNews")
    List<TopNews> fetchAll();

    @Update
    void updateRecord(TopNews topNews);

    @Delete
    void deleteRecord(TopNews topNews);
}
