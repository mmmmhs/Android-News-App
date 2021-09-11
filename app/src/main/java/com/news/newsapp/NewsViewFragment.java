package com.news.newsapp;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.scwang.smart.refresh.*;

import com.news.data.LoadNews;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class NewsViewFragment extends Fragment {
    private RecyclerView rv;
    private NewsAdapter news_adapt;
    private SmartRefreshLayout refreshLayout;
    private String title;
    private int refTime = 0;

    NewsViewFragment(String s){
        super();
        title = s;
        news_adapt = new NewsAdapter();
    }

    String getTitle(){
        return title;
    }
    public SmartRefreshLayout getRefreshLayout(){
        return refreshLayout;
    }

    public void setNewsAdapter(ArrayList<News> list)
    {
        news_adapt = new NewsAdapter(getActivity(), list);
        rv.setAdapter(news_adapt);
        if(list.size() == 0)
            Toast.makeText(getActivity(), "无结果", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_fragment, container, false);
        rv = v.findViewById(R.id.rv_news);
        refreshLayout = v.findViewById(R.id.refLayout);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false));
        rv.setAdapter(news_adapt);
        rv.addItemDecoration(new NewsDeco(this.getActivity()));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                LoadNews ln;
                if(!title.equals("全部"))
                {
                    ln = new LoadNews(NewsViewFragment.this, 15 * (refTime + 2),
                            "", new GetCurTime().get(), "", title);
                }
                else
                {
                    ln = new LoadNews(NewsViewFragment.this, 15 * (refTime + 2),
                            "", new GetCurTime().get(), "", "");
                }
                ln.load();
                refTime++;
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LoadNews ln;
                if(!title.equals("全部"))
                {
                    ln = new LoadNews(NewsViewFragment.this, 15 * (refTime + 2),
                            "", new GetCurTime().get(), "", title);
                }
                else
                {
                    ln = new LoadNews(NewsViewFragment.this, 15 * (refTime + 2),
                            "", new GetCurTime().get(), "", "");
                }
                ln.refresh();
                refTime++;
            }
        });
        refreshLayout.setEnableAutoLoadMore(false);
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
    }

    public void refresh(ArrayList<News> al){
        ArrayList<News> origin = news_adapt.getNews();
        ArrayList<News> list = new ArrayList<>();
        int l = al.size() - 15;
        for(int i = 0; i < 15; i++)
            list.add(al.get(l + i));
        int ol = origin.size();
        for(int i = 0; i < ol; i++)
            list.add(origin.get(i));
        news_adapt = new NewsAdapter(getActivity(), list);
        rv.setAdapter(news_adapt);
    }


    public void load(ArrayList<News> al){
        ArrayList<News> origin = news_adapt.getNews();
        int l = al.size() - 15;
        for(int i = 0; i < 15; i++)
            origin.add(al.get(l + i));
        news_adapt = new NewsAdapter(getActivity(), origin);
        LinearLayoutManager lm = (LinearLayoutManager)rv.getLayoutManager();
        int pos = lm.findFirstCompletelyVisibleItemPosition();
        rv.setAdapter(news_adapt);
        lm.scrollToPosition(pos - 1);
    }
}

class NewsDeco extends RecyclerView.ItemDecoration{

    private Context context;

    public NewsDeco(Context con){
        context = con;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0,
                context.getResources().getDimensionPixelOffset(R.dimen.divider_width));
    }
}

