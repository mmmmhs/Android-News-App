package com.news.newsapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetCurTime {
    private Date dNow;
    private SimpleDateFormat ftime;
    public GetCurTime(){
        dNow = new Date( );
        ftime = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
    }
    public String get(){
        return ftime.format(dNow);
    }
}
