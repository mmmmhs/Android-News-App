package com.news.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Date;

public class SetTimeActivity extends AppCompatActivity {
    private CalendarView start, end;
    private Button confirmBtn;
    private String start_sel, end_sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        start = findViewById(R.id.calendar_start);
        end = findViewById(R.id.calendar_end);
        confirmBtn = findViewById(R.id.time_confirm);
        String startTime = getIntent().getStringExtra("start");
        String endTime = getIntent().getStringExtra("end");
        start.setDate(new GetCurTime().toLongForDate(startTime));
        end.setDate(new GetCurTime().toLongForDate(endTime));
        start.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                String yy = Integer.toString(y);
                String mm;
                m++;
                if(m < 10)
                    mm = "0" + Integer.toString(m);
                else
                    mm = Integer.toString(m);
                String dd;
                if(d < 10)
                    dd = "0" + Integer.toString(d);
                else
                    dd = Integer.toString(d);
                start_sel = yy + "-" + mm + "-" + dd;
            }
        });
        end.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                String yy = Integer.toString(y);
                String mm;
                m++;
                if(m < 10)
                    mm = "0" + Integer.toString(m);
                else
                    mm = Integer.toString(m);
                String dd;
                if(d < 10)
                    dd = "0" + Integer.toString(d);
                else
                    dd = Integer.toString(d);
                end_sel = yy + "-" + mm + "-" + dd;
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent();
                if(start_sel == null)
                    intent.putExtra("start", start.getDate());
                else
                    intent.putExtra("start", new GetCurTime().toLongForDate(start_sel));
                if(end_sel == null)
                    intent.putExtra("end", end.getDate());
                else
                    intent.putExtra("end", new GetCurTime().toLongForDate(end_sel));
                setResult(1, intent);
                finish();
            }
        });
    }
}