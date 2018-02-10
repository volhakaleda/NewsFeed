package nyc.c4q.newsfeed.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by mohammadnaz on 2/5/18.
 */

public class TopNewsViewModel extends AndroidViewModel{

    private LiveData<List<TopNews>> topnewslist;

    public TopNewsViewModel(@NonNull Application application) {
        super(application);
        TopNewsDatabase database= TopNewsDatabase.getInstance(this.getApplication());
        topnewslist= database.getTopNewsDao().fetchAllData();
    }

    public LiveData<List<TopNews>> getTopnewslist() {
        return topnewslist;
    }
}
