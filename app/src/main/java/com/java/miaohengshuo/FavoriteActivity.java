package com.java.miaohengshuo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FavoriteActivity extends AppCompatActivity {
    private Button homeBtn, historyBtn;
    private RecyclerView rv;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        homeBtn = findViewById(R.id.homeBtn_f);
        historyBtn = findViewById(R.id.historyBtn_f);
        rv = findViewById(R.id.favorite_rv);
        rv.addItemDecoration(new NewsDeco(this));
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoriteActivity.this, HistoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new NewsAdapter(this, MainActivity.favList);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        rv.setAdapter(adapter);
    }
}