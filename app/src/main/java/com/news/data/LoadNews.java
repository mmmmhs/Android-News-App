package com.news.data;

import com.google.gson.Gson;

import org.w3c.dom.CDATASection;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class LoadNews {
    private String size;
    private String startDate;
    private String endDate;
    private String words;
    private String categories;
    private String url;

    public LoadNews(int iSize, String sStart, String sEnd, String sWord, String sCate){
        this.size = "size=" + Integer.toString(iSize);
        this.startDate = "startDate=" + sStart;
        this.endDate = "endDate=" + sEnd;
        this.words = "words=" + sWord;
        this.categories = "categories=" + sCate;
        this.url = "https://api2.newsminer.net/svc/news/queryNewsList?" + this.size + this.startDate
                   + this.endDate + this.words + this.categories;
    }
    public Response load(){
        Response json;
        try{
            URL u = new URL(this.url);
            URLConnection uConnection = u.openConnection();
            HttpURLConnection connection = null;
            if(uConnection instanceof HttpURLConnection)
            {
                connection = (HttpURLConnection) uConnection;
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String content = "";
            String current;
            while((current = in.readLine()) != null)
            {
                content += current;
            }
            Gson gson = new Gson();
            json = gson.fromJson(content, Response.class);
            System.out.println(json);
        }catch(IOException e)
        {
            e.printStackTrace();
            json = null;
        }
        return json;
    }
}
