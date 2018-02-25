package nyc.c4q.newsfeed.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.newsfeed.R;
import nyc.c4q.newsfeed.model.TopNews;

/**
 * Created by C4Q on 2/10/18.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private List<TopNews> topNews = new ArrayList<>();


    private onClick onclickListener;



    public void setTopNews(List<TopNews> topNewsList1111) {
        topNews.addAll(topNewsList1111);
        notifyDataSetChanged();
    }

    public void setOnclickListener(onClick onclickListener) {
        this.onclickListener = onclickListener;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_items, parent,
                false);

        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        TopNews topNews2 = topNews.get(position);

        String author = topNews2.getAuthor();
        String description = topNews2.getDescription();
        String url = topNews2.getUrlToImage();

        if (author != null) {
            holder.textView.setText(author);
        } else {
            holder.textView.setText("");
        }
        if (description != null) {
            holder.textView1.setText(description);
        } else {
            holder.textView1.setText("");
        }
        if (url != null) {
            Glide.with(holder.imageView.getContext())
                    .load(url)
                    .into(holder.imageView);
        } else {
            Glide.with(holder.imageView.getContext())
                    .load("https://s3-us-west-2.amazonaws.com/internationalwomensday/home/iwd-news.png")
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return topNews.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       private ImageView imageView;
       private TextView textView;
       private TextView textView1;

        public DataViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image1);
            textView = itemView.findViewById(R.id.author);
            textView1 = itemView.findViewById(R.id.desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
            if (itemView.getId() == view.getId()) {
                onclickListener.onclickOnRecyclerView(p);
            }
        }
    }

    public interface onClick {
        void onclickOnRecyclerView(int p);
    }

//    public interface Animal {
//        void move(int numFeet);
//    }
//
//    public class Dog implements Animal {
//        @Override
//        public void move(int numFeet) {
//            // run spot run
//        }
//
//        public void bark() {
//
//        }
//    }

//    public class Cat implements Animal {
//        @Override
//        public void move(int numFeet) {
//            // jump
//        }
//
//        public void bark() {
//
//        }
//    }
//
//    public class Zoo {
//        private List<Animal> animals = new ArrayList<>();
//
//        public void moveANimals() {
//            for (Animal animal : animals) {
//                animal.move();
//            }
//        }
//    }


}
