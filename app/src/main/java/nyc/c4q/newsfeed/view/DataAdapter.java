package nyc.c4q.newsfeed.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nyc.c4q.newsfeed.R;
import nyc.c4q.newsfeed.model.TopNews;

/**
 * Created by C4Q on 2/10/18.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder>{

    private List<TopNews> topNews=new ArrayList<>();
    private onClick onclick;
//
//    public DataAdapter(List<TopNews> topNews) {
//        this.topNews = topNews;
//        notifyDataSetChanged();
//
//    }


    public void setTopNews(List<TopNews> topNewsList1111){
///        topNews.clear();
        topNews.addAll(topNewsList1111);
        notifyDataSetChanged();

    }
    public void setOnclick(onClick onclick){
        this.onclick=onclick;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_items, parent, false);
        return new DataViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        TopNews topNews2= topNews.get(position);
        try{
            String author= topNews2.getAuthor();
            holder.textView.setText(author);

        }catch (NullPointerException n) {
        }
        try{
            String desp= topNews2.getDescription();
            holder.textView1.setText(desp);

        }catch (NullPointerException n) {
        }
        try{
            Glide.with(holder.imageView.getContext())
                    .load(topNews2.getUrlToImage())
                    .into(holder.imageView);

        }catch (NullPointerException n) {
        }





    }

    @Override
    public int getItemCount() {
        //notifyDataSetChanged();
        return topNews.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        TextView textView1;
        TextView textView2;
        public DataViewHolder(View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.image1);
            textView= itemView.findViewById(R.id.author);
            textView1= itemView.findViewById(R.id.desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p= getAdapterPosition();
            if(itemView.getId()== view.getId()){
                onclick.onclicker(p);
            }
        }
    }
    public interface onClick {
        void onclicker(int p);
    }


}
