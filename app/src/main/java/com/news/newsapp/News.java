package com.news.newsapp;

import com.news.data.*;
import java.util.*;
import android.os.*;

public class News implements Parcelable{
    public int imageNum;
    public String category;
    public String time;
    public String title;
    public String text;
    public ArrayList<String> image;
    public String video;
    public String source;

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel parcel) {
            News n = new News();
            n.imageNum = parcel.readInt();
            n.category = parcel.readString();
            n.time = parcel.readString();
            n.title = parcel.readString();
            n.text = parcel.readString();
            n.image = new ArrayList<>();
            for(int k = 0; k < n.imageNum; k++)
                n.image.add(parcel.readString());
            n.video = parcel.readString();
            n.source = parcel.readString();
            return n;
        }

        @Override
        public News[] newArray(int i) {
            return new News[i];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(imageNum);
        parcel.writeString(category);
        parcel.writeString(time);
        parcel.writeString(title);
        parcel.writeString(text);
        for(int k = 0; k < imageNum; k++)
            parcel.writeString(image.get(k));
        parcel.writeString(video);
        parcel.writeString(source);
    }

    News(){}
    News(DataItem di){
        category = di.getCategory();
        time = di.getPublishTime();
        int kwl = di.getKeywords().size();
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
        source = di.getPublisher();
    }

    public static ArrayList<News> netNewsAL(Response r){
        int len = r.getData().size();
        ArrayList<News> n = new ArrayList<>();
        for(int i = 0; i < len; i++)
            n.add(new News(r.getData().get(i)));
        return n;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

