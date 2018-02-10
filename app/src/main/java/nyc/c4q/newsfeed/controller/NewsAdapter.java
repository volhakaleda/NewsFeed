package nyc.c4q.newsfeed.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.newsfeed.R;
import nyc.c4q.newsfeed.model.Article;
import nyc.c4q.newsfeed.view.NewsViewHolder;


public class NewsAdapter  extends RecyclerView.Adapter<NewsViewHolder>{

    private List<Article> listOfArticles = new ArrayList<>();

    public NewsAdapter() {
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_layout, parent, false);
        return new NewsViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        Article article = listOfArticles.get(position);
        holder.onBind(article);
    }

    @Override
    public int getItemCount() {
        return listOfArticles.size();
    }

    public void setData(List<Article> listOfArticles) {
        this.listOfArticles = listOfArticles;

    }
}
