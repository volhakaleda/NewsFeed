package nyc.c4q.newsfeed.Presenter;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.newsfeed.RetroPojos.Article;
import nyc.c4q.newsfeed.model.DatabaseInitializer;
import nyc.c4q.newsfeed.model.TopNews;
import nyc.c4q.newsfeed.model.TopNewsDatabase;
import nyc.c4q.newsfeed.view.MainActivity;

/**
 * Created by mohammadnaz on 2/12/18.
 */

public class MainActivityPresenter {
    private MainActivityView mainActivityView;
    private List<TopNews> topNewsListPresenter;
    private List<Article> topNewsListPresenter1;


    private TopNewsDatabase topNewsDatabase;




    //INJECT
    public MainActivityPresenter(MainActivityView mainActivityView, TopNewsDatabase topNewsDatabase) {
        this.mainActivityView = mainActivityView;
        this.topNewsDatabase = topNewsDatabase;


//        ((MainActivity)mainActivityView).finish();
       /// presenter = BasePresenter<MainActivityView>
    }

    public void sendNewDatatoPresentertoSendBackToTheRecylcerView(List<TopNews> topNews) {
       // DatabaseInitializer.populateAsync(topNewsDatabase,topNewsListPresenter1);

        topNewsListPresenter = new ArrayList<>();
        for (int i = topNews.size() - 1; i > 0; i--) {
            TopNews topNews1 = topNews.get(i);
            topNewsListPresenter.add(topNews1);
        }

        mainActivityView.addData(topNewsListPresenter);
    }

    public void getPositionofRecyclerView(int position) {
        TopNews topNews = topNewsListPresenter.get(position);
        String url = topNews.getUrl();
        mainActivityView.sendDataToListner(url);
    }

    public void getDataBaseData() {
        topNewsListPresenter = new ArrayList<>();
        DatabaseInitializer.getAllNewsAsync(topNewsDatabase);
        DatabaseInitializer.setAsyncCallBack(new DatabaseInitializer.AsyncCallBack() {
            @Override
            public void getTopNewsFromDataBaseOffLine(List<TopNews> topNewsList) {
                for (int i = topNewsList.size() - 1; i > 0; i--) {
                    TopNews topNews1 = topNewsList.get(i);
                    topNewsListPresenter.add(topNews1);
                }
                mainActivityView.addData(topNewsListPresenter);
            }
        });

    }

    public interface MainActivityView {
        void addData(List<TopNews> topNewsList);

        void sendDataToListner(String url);
    }

    public interface DatabaseInterface {
        void insertNews(TopNews topNews);

        List<TopNews> getAll();

    }
}
