package com.news.newsapp;

import java.util.*;
import java.io.*;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
        NewsViewHolder hd = (NewsViewHolder) holder;
        hd.setView(n);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("news", n);
                ((AppCompatActivity)context).startActivityForResult(intent, 1);
                hd.setAfterRead();
            }
        });
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

    abstract class NewsViewHolder extends RecyclerView.ViewHolder{

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void setView(News n){}
        public void setAfterRead(){}
    }

    class TextNewsViewHolder extends NewsViewHolder{
        private TextView title, cate, time;
        public TextNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tnews_title);
            cate = itemView.findViewById(R.id.tnews_cate);
            time = itemView.findViewById(R.id.tnews_time);
        }
        @Override
        public void setView(News n){
            title.setText(n.title);
            cate.setText(n.category);
            time.setText(n.time);
        }

        @Override
        public void setAfterRead() {
            title.setTextColor(context.getColor(R.color.grey));
        }
    }

    class OneImageNewsViewHolder extends NewsViewHolder{
        private TextView title, cate, time;
        private ImageView image;
        public OneImageNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.inews_title);
            cate = itemView.findViewById(R.id.inews_cate);
            time = itemView.findViewById(R.id.inews_time);
            image = itemView.findViewById(R.id.inews_image);
        }
        @Override
        public void setView(News n){
            title.setText(n.title);
            cate.setText(n.category);
            time.setText(n.time);
            Glide.with(context).load(n.image.get(0)).placeholder(R.drawable.default_pic).into(image);
        }
        @Override
        public void setAfterRead() {
            title.setTextColor(context.getColor(R.color.grey));
        }
    }

    class TwoImageNewsViewHolder extends NewsViewHolder{
        private TextView title, cate;
        private ImageView image1, image2;
        public TwoImageNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tinews_title);
            cate = itemView.findViewById(R.id.tinews_cate);
            image1 = itemView.findViewById(R.id.tinews_image1);
            image2 = itemView.findViewById(R.id.tinews_image2);
        }
        @Override
        public void setView(News n){
            title.setText(n.title);
            cate.setText(n.category);
            Glide.with(context).load(n.image.get(0)).placeholder(R.drawable.default_pic).into(image1);
            Glide.with(context).load(n.image.get(1)).placeholder(R.drawable.default_pic).into(image2);
        }
        @Override
        public void setAfterRead() {
            title.setTextColor(context.getColor(R.color.grey));
        }
    }
}

