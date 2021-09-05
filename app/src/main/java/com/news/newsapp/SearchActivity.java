package com.news.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private TextView T_search;
    private Button confirm;
    private EditText keywords_text;
    private EditText startTime_text;
    private EditText endTime_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        T_search = findViewById(R.id.text_search);
        T_search.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        confirm = findViewById(R.id.btn_confirm);
        keywords_text = findViewById(R.id.keyword_input);
        startTime_text = findViewById(R.id.start_time_input);
        endTime_text = findViewById(R.id.end_time_input);
    }
}