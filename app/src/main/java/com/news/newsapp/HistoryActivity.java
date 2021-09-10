package com.news.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private Button homeBtn, favoriteBtn;
    private RecyclerView rv;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        homeBtn = findViewById(R.id.homeBtn_h);
        favoriteBtn = findViewById(R.id.favoriteBtn_h);
        rv = findViewById(R.id.history_rv);
        rv.addItemDecoration(new NewsDeco(this));
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, FavoriteActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new NewsAdapter(this, MainActivity.hisList);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        rv.setAdapter(adapter);
    }
}