package nyc.c4q.newsfeed.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by mohammadnaz on 2/5/18.
 */
@Database(entities = {TopNews.class}, version = 1, exportSchema = false)
public abstract class TopNewsDatabase extends RoomDatabase{

    private static final String DB_NAME = "TopNews.db";
    private static volatile TopNewsDatabase instance;

    public static synchronized TopNewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static TopNewsDatabase create(final Context context) {
        return Room.databaseBuilder(context, TopNewsDatabase.class, DB_NAME)
                .build();

    }

    public abstract TopNewsDoa getTopNewsDao();

}