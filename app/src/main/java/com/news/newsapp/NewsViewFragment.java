package com.news.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.news.data.LoadNews;

import java.util.ArrayList;

public class NewsViewFragment extends Fragment {
    private RecyclerView rv;
    private NewsAdapter news_adapt;
    private String title;

    NewsViewFragment(String s){
        super();
        title = s;
        news_adapt = new NewsAdapter();
    }

    String getTitle(){
        return title;
    }

    public void setNewsAdapter(ArrayList<News> list)
    {
        news_adapt = new NewsAdapter(list);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_fragment, container, false);
        rv = v.findViewById(R.id.rv_news);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false));
        rv.setAdapter(news_adapt);
        rv.addItemDecoration(new NewsDeco(this.getActivity()));
        return v;
    }

    @Override
    public void onStart() {
        LoadNews ln;
        if(!title.equals("全部"))
        {
            ln = new LoadNews(this, 15, "", new GetCurTime().get(), "", title);
        }
        else
        {
            ln = new LoadNews(this, 15, "", new GetCurTime().get(), "", "");
        }
        ln.launch();
        super.onStart();
        rv.setAdapter(news_adapt);
    }
}
