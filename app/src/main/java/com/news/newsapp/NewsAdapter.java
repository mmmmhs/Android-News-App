package com.news.newsapp;

import java.util.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //private Context con;
    private ArrayList<News> news;

    public NewsAdapter(){
        this.news = new ArrayList<>();
    }
    public NewsAdapter(ArrayList<News> list){
        //this.con = c;
        this.news = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0)
            return new TextNewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_text,
                    parent, false));
        else
            return new OneImageNewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_with_one_image,
                    parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(news.get(position).imageNum < 3)
            return news.get(position).imageNum;
        else
            return 3;
    }

    class TextNewsViewHolder extends RecyclerView.ViewHolder{
        private TextView title, cate, time;
        public TextNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tnews_title);
            cate = itemView.findViewById(R.id.tnews_cate);
            time = itemView.findViewById(R.id.tnews_time);
        }
    }

    class OneImageNewsViewHolder extends RecyclerView.ViewHolder{
        private TextView title, cate, time;
        private ImageView image;
        public OneImageNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.inews_title);
            cate = itemView.findViewById(R.id.inews_cate);
            time = itemView.findViewById(R.id.inews_time);
            image = itemView.findViewById(R.id.inews_image);
        }
    }
}

