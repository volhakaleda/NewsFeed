package nyc.c4q.newsfeed.backend;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import nyc.c4q.newsfeed.model.Article;


@Database(entities = {Article.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "news-databse")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract NewsDao newsDao();
}

