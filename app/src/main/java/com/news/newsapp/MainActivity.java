package com.news.newsapp;

import java.util.*;
import java.text.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.*;
import androidx.viewpager.widget.*;
import com.google.android.material.tabs.*;

import com.google.gson.Gson;
import com.google.gson.reflect.*;
import com.andy.library.*;
import com.news.data.LoadNews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    ArrayList<ChannelBean> channelBeanList = new ArrayList<>();
    ArrayList<NewsViewFragment> fraglist = new ArrayList<>();

    private TabAdapter tab_adapt;
    private ViewPager viewPager;
    private TabLayout tab_channel;
    private Button set_channelBtn;

    private Button searchframe;
    private Button searchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchframe = findViewById(R.id.search_frame);
        searchframe.getPaint().setAntiAlias(true);
        searchbtn = findViewById(R.id.searchbtn);
        searchframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });
        initView();
        initData();
    }

    private void initView() {
        //找控件
        viewPager = findViewById(R.id.vp_news);
        tab_channel = findViewById(R.id.tab_cate);
        set_channelBtn = findViewById(R.id.btn_setChannel);
        tab_channel.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置适配器
        tab_adapt = new TabAdapter(getSupportFragmentManager(), fraglist);
        viewPager.setAdapter(tab_adapt);
        //关联
        tab_channel.setupWithViewPager(viewPager);
        //点击事件
        set_channelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelActivity.startChannelForResult(MainActivity.this, channelBeanList);
            }
        });
    }

    private void initData() {
        channelBeanList.add(new ChannelBean("全部",true));//true为显示当前title
        channelBeanList.add(new ChannelBean("娱乐",true));
        channelBeanList.add(new ChannelBean("军事",true));
        channelBeanList.add(new ChannelBean("教育",true));
        channelBeanList.add(new ChannelBean("文化",true));
        channelBeanList.add(new ChannelBean("健康",false));//false为不显示当前title
        channelBeanList.add(new ChannelBean("财经",false));
        channelBeanList.add(new ChannelBean("体育",false));
        channelBeanList.add(new ChannelBean("汽车",false));
        channelBeanList.add(new ChannelBean("科技",false));
        channelBeanList.add(new ChannelBean("社会",false));
        for (int i = 0; i < channelBeanList.size(); i++) {//循环添加进集合
            if(channelBeanList.get(i).isSelect()){
                //把isSelect为 true的设置到title上
                tab_channel.addTab(tab_channel.newTab().setText(channelBeanList.get(i).getName()));
                fraglist.add(new NewsViewFragment(channelBeanList.get(i).getName()));
            }
        }
        //刷新适配器
        tab_adapt.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //返回来的频道管理 封装在json中
        String jsonString = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
        //解析
        Gson gson = new Gson();
        //把数组转为集合  用TypeToken转换
        channelBeanList = gson.fromJson(jsonString,
                                        new TypeToken<ArrayList<ChannelBean>>() {}.getType());
        //清空tablayout
        tab_channel.removeAllTabs();
        //清空fragment
        fraglist.clear();
        //重新添加tablayout和fragment
        for (int i = 0; i < channelBeanList.size(); i++) {
            if(channelBeanList.get(i).isSelect()){
                tab_channel.addTab(tab_channel.newTab().setText(channelBeanList.get(i).getName()));
                fraglist.add(new NewsViewFragment(channelBeanList.get(i).getName()));
            }
        }
        //刷新适配器
        tab_adapt.notifyDataSetChanged();
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


