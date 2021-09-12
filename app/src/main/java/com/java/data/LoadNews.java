package com.java.data;

import android.app.Application;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.java.miaohengshuo.*;



import org.w3c.dom.CDATASection;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;
import java.text.*;
import android.os.Handler;
import android.widget.ListView;

public class LoadNews {
    private NewsViewFragment curFrag;
    private String size;
    private String startDate;
    private String endDate;
    private String words;
    private String categories;
    private String url;

    public LoadNews(NewsViewFragment frag, int iSize, String sStart, String sEnd, String sWord, String sCate){
        curFrag = frag;
        this.size = "size=" + Integer.toString(iSize);
        this.startDate = "startDate=" + sStart;
        this.endDate = "endDate=" + sEnd;
        this.words = "words=" + sWord;
        this.categories = "categories=" + sCate;
        this.url = "https://api2.newsminer.net/svc/news/queryNewsList?"
                 + this.size + "&"
                 + this.startDate + "&"
                 + this.endDate + "&"
                 + this.words + "&"
                 + this.categories;
    }
    public String getUrl(){
        return url;
    }

    public void launch(){
        GetDataThread th = new GetDataThread(curFrag, url);
        th.start();
    }
    public void refresh(){
        RefreshDataThread th = new RefreshDataThread(curFrag, url);
        th.start();
    }
    public void load(){
        LoadDataThread th = new LoadDataThread(curFrag, url);
        th.start();
    }

    class DataThread extends Thread{
        protected String url;
        protected Handler mainHandler;
        protected NewsViewFragment frag;
        protected Response json;

        public DataThread(NewsViewFragment f, String s){
            frag = f;
            url = s;
            mainHandler = new Handler();
        }

        @Override
        public void run() {

            String content = new String();
            try {
                URL u = new URL(this.url);
                URLConnection uConnection = u.openConnection();
                HttpURLConnection connection = null;
                if (uConnection instanceof HttpURLConnection) {
                    connection = (HttpURLConnection) uConnection;
                }
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String current;
                while ((current = in.readLine()) != null) {
                    content += current;
                }
                Gson gson = new Gson();
                json = gson.fromJson(content, Response.class);
            } catch (IOException io) {
                io.printStackTrace();
                json = null;
            }
        }
    }

    class GetDataThread extends DataThread{
        public GetDataThread(NewsViewFragment f, String s){
            super(f, s);
        }

        @Override
        public void run() {
            super.run();
            mainHandler.post(new Runnable() {
                //更新fragment中的NewsAdapter
                @Override
                public void run() {
                    frag.setNewsAdapter(News.netNewsAL(json));
                }
            });
        }
    }

    class RefreshDataThread extends DataThread{

        public RefreshDataThread(NewsViewFragment f, String s) {
            super(f, s);
        }

        @Override
        public void run() {
            super.run();
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    frag.refresh(News.netNewsAL(json));
                    frag.getRefreshLayout().finishRefresh();
                }
            });
        }
    }
    class LoadDataThread extends DataThread{

        public LoadDataThread(NewsViewFragment f, String s) {
            super(f, s);
        }

        @Override
        public void run() {
            super.run();
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    frag.load(News.netNewsAL(json));
                    frag.getRefreshLayout().finishLoadMore();
                }
            });
        }
    }
}
