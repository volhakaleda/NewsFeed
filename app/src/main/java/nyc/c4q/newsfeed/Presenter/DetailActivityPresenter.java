package nyc.c4q.newsfeed.Presenter;

/**
 * Created by mohammadnaz on 2/14/18.
 */

public class DetailActivityPresenter {

    DetailActivityPresenter.DAPView dapView;



    public DetailActivityPresenter(DetailActivityPresenter.DAPView dapView){
        this.dapView=dapView;
        dapView.setView(this);
    }

    public void DAPMethod(){
        dapView.helloworld();
    }






    public interface DAPView extends SetViews{
        void helloworld();
    }

    public interface SetViews{
        void setView(DetailActivityPresenter dapView);
    }
}
