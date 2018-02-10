package nyc.c4q.newsfeed.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nyc.c4q.newsfeed.R;
import nyc.c4q.newsfeed.model.Article;

/**
 * Created by olgakoleda on 2/10/18.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder{

    private TextView author;
    private TextView publisheAt;
    private TextView description;

    public NewsViewHolder(View itemView) {
        super(itemView);

        author = itemView.findViewById(R.id.author);
        publisheAt = itemView.findViewById(R.id.name);
        description = itemView.findViewById(R.id.description);
    }

    public void onBind(Article article){
        author.setText(article.getAuthor());
        publisheAt.setText(article.getPublishedAt());
        description.setText(article.getDescription());
    }
}
