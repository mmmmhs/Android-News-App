package com.news.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.net.*;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;

public class DetailActivity extends AppCompatActivity {
    private TextView textTitle, textTime, textSrc, textContent;
    private LinearLayout layoutImage, layoutVideo;
    private Button favBtn;

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
        favBtn = findViewById(R.id.favBtn);
        News n = getIntent().getParcelableExtra("news");
        textTitle.setText(n.title);
        textTime.setText(n.time);
        textSrc.setText(n.source);
        textContent.setText(n.text);
        if(!MainActivity.isFavorite(n))
        {
            favBtn.setBackground(getDrawable(R.drawable.empty_star));
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favBtn.setBackground(getDrawable(R.drawable.star));
                    MainActivity.favList.add(n);
                }
            });
        }
        else{
            favBtn.setBackground(getDrawable(R.drawable.star));
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favBtn.setBackground(getDrawable(R.drawable.empty_star));
                    MainActivity.deleteFav(n);
                }
            });
        }
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
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if((!n.video.isEmpty()) && (info != null) && (info.isConnected())){
            VideoView vv = new VideoView(this);
            LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.video_width),
                    getResources().getDimensionPixelSize(R.dimen.video_height));
            vv.setLayoutParams(par);
            vv.setVideoURI(Uri.parse(n.video));
            vv.setMediaController(new MediaController(this));
            layoutVideo.addView(vv);
        }
    }
}