package com.news.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.net.*;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private TextView textTitle, textTime, textSrc, textContent;
    private LinearLayout layoutImage, layoutVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textTitle = findViewById(R.id.details_title);
        textTime = findViewById(R.id.details_time);
        textSrc = findViewById(R.id.details_source);
        textContent = findViewById(R.id.details_content);
        layoutImage = findViewById(R.id.imageLayout);
        layoutVideo = findViewById(R.id.videoLayout);
        News n = getIntent().getParcelableExtra("news");
        textTitle.setText(n.title);
        textTime.setText(n.time);
        textSrc.setText(n.source);
        textContent.setText(n.text);
        int im = n.imageNum;
        for(int i = 0; i < im; i++){
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.image_width),
                                                                    getResources().getDimensionPixelSize(R.dimen.image_height));
            par.leftMargin = getResources().getDimensionPixelSize(R.dimen.margin);
            iv.setLayoutParams(par);
            Glide.with(this).load(n.image.get(i)).placeholder(R.drawable.default_pic).into(iv);
            layoutImage.addView(iv);
        }
        if(!n.video.isEmpty()){
            VideoView vv = new VideoView(this);
            LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.video_width),
                                                                    getResources().getDimensionPixelSize(R.dimen.video_height));
            vv.setLayoutParams(par);
            vv.setVideoPath(n.video);
            vv.setMediaController(new MediaController(this));
            layoutVideo.addView(vv);
        }
    }
}