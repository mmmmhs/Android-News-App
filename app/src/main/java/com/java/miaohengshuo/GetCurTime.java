package com.java.miaohengshuo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetCurTime {
    private boolean def;
    private Date dNow;
    private SimpleDateFormat ftime;
    public GetCurTime(){
        def = false;
        dNow = new Date( );
        ftime = new SimpleDateFormat ("yyyy-MM-dd");
    }

    public GetCurTime(Long l){
        if(l == 0)
            def = true;
        else
            def = false;
        dNow = new Date();
        ftime = new SimpleDateFormat ("yyyy-MM-dd");
        dNow.setTime(l);
    }

    public String get(){
        if(def)
            return new String();
        else
            return ftime.format(dNow);
    }

    public long toLongForDate(String str){
        try{
            return ftime.parse(str).getTime();
        }catch (ParseException e){}
        return -1;
    }
}
