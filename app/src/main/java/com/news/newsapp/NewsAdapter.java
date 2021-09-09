package com.news.newsapp;

import java.util.*;
import java.io.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<News> news;

    public NewsAdapter(){
        this.news = new ArrayList<>();
    }
    public NewsAdapter(Context con, ArrayList<News> list){
        context = con;
        news = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0)
            return new TextNewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_text,
                    parent, false));
        else if(viewType == 1)
            return new OneImageNewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_with_one_image,
                    parent, false));
        else
            return new TwoImageNewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_with_two_images,
                    parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        News n = news.get(position);
        if(holder instanceof TextNewsViewHolder){
            TextNewsViewHolder hd = (TextNewsViewHolder) holder;
            hd.setView(n);
        }
        if(holder instanceof OneImageNewsViewHolder){
            OneImageNewsViewHolder hd = (OneImageNewsViewHolder) holder;
            hd.setView(n);
        }
        if(holder instanceof TwoImageNewsViewHolder){
            TwoImageNewsViewHolder hd = (TwoImageNewsViewHolder) holder;
            hd.setView(n);
        }
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(news.get(position).imageNum < 2)
            return news.get(position).imageNum;
        else
            return 2;
    }

    class TextNewsViewHolder extends RecyclerView.ViewHolder{
        private TextView title, cate, time;
        public TextNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tnews_title);
            cate = itemView.findViewById(R.id.tnews_cate);
            time = itemView.findViewById(R.id.tnews_time);
        }
        public void setView(News n){
            title.setText(n.title);
            cate.setText(n.category);
            time.setText(n.time);
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
        public void setView(News n){
            title.setText(n.title);
            cate.setText(n.category);
            time.setText(n.time);
            Glide.with(context).load(n.image.get(0)).placeholder(R.drawable.default_pic).into(image);
        }
    }

    class TwoImageNewsViewHolder extends RecyclerView.ViewHolder{
        private TextView title, cate;
        private ImageView image1, image2;
        public TwoImageNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tinews_title);
            cate = itemView.findViewById(R.id.tinews_cate);
            image1 = itemView.findViewById(R.id.tinews_image1);
            image2 = itemView.findViewById(R.id.tinews_image2);
        }
        public void setView(News n){
            title.setText(n.title);
            cate.setText(n.category);
            Glide.with(context).load(n.image.get(0)).placeholder(R.drawable.default_pic).into(image1);
            Glide.with(context).load(n.image.get(1)).placeholder(R.drawable.default_pic).into(image2);
        }
    }
}

