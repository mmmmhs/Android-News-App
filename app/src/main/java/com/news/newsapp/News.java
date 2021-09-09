package com.news.newsapp;

import com.news.data.*;
import java.util.*;

public class News {
    public int imageNum;//0 = text, 1 = image, 2 = video
    public String category;
    public String time;
    public String[] words;
    public String title;
    public String text;
    public ArrayList<String> image;
    public String video;

    News(){}
    News(DataItem di){
        category = di.getCategory();
        time = di.getPublishTime();
        int kwl = di.getKeywords().size();
        words = new String[kwl];
        for(int i = 0; i < kwl; i++){
            words[i] = di.getKeywords().get(i).getWord();
        }
        title = di.getTitle();
        text = di.getContent();
        image = new ArrayList<>();
        int i = 1;
        int len = di.getImage().length();
        String str = new String();
        while (i < len - 1)
        {
            while ((di.getImage().charAt(i) != ',') && (i < len - 1) && (di.getImage().charAt(i) != ']'))
            {
                str += di.getImage().charAt(i);
                i++;
            }
            if(!((str.equals("")) || (str.equals("] ["))))
            {
                image.add(str);
                str = "";
            }
            if(di.getImage().charAt(i) == ',')
                i += 2;
            else
                i += 3;
        }
        imageNum = image.size();
        video = di.getVideo();
    }

    public static ArrayList<News> netNewsAL(Response r){
        int len = r.getData().size();
        ArrayList<News> n = new ArrayList<News>();
        for(int i = 0; i < len; i++)
            n.add(new News(r.getData().get(i)));
        return n;
    }
}

