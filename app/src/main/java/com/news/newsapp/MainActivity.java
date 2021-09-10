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

import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ArrayList<ChannelBean> channelBeanList = new ArrayList<>();
    ArrayList<NewsViewFragment> fraglist = new ArrayList<>();
    private String startTime, endTime;

    public static ArrayList<News> hisList = new ArrayList<>();
    public static ArrayList<News> favList = new ArrayList<>();

    private Dialog dialog;
    private TabAdapter tab_adapt;
    private ViewPager viewPager;
    private TabLayout tab_channel;
    private Button set_channelBtn;

    private EditText searchframe;
    private Button searchbtn;
    private Button settimebtn;

    private Button favBtn, hisBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchframe = findViewById(R.id.search_frame);
        searchframe.getPaint().setAntiAlias(true);
        searchbtn = findViewById(R.id.searchBtn);
        settimebtn = findViewById(R.id.timeBtn);
        favBtn = findViewById(R.id.favoriteBtn);
        hisBtn = findViewById(R.id.historyBtn);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });
        hisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        settimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater=LayoutInflater.from(settimebtn.getContext());
                View myview = inflater.inflate(R.layout.time_set_dialog,null);//引用自定义布局
                AlertDialog.Builder builder = new AlertDialog.Builder(settimebtn.getContext());
                builder.setView(myview);
                dialog = builder.create();//创建对话框
                dialog.show();//显示对话框
                myview.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener(){//获取布局里面按钮
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();//点击按钮对话框消失
                        startTime = new String();
                        endTime = new GetCurTime().get();
                    }
                });
                myview.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, SetTimeActivity.class);
                        if((startTime != null) && (!startTime.equals("")))
                            intent.putExtra("start", startTime);
                        else
                            intent.putExtra("start", new GetCurTime().get());
                        if((endTime != null) && (!endTime.equals("")))
                            intent.putExtra("end", endTime);
                        else
                            intent.putExtra("end", new GetCurTime().get());
                        startActivityForResult(intent, 0);
                    }
                });
            }
        });
        initView();
        initData();
    }

    private void search(){
        NewsViewFragment frag = (NewsViewFragment) tab_adapt.getItem(viewPager.getCurrentItem());
        String cate = frag.getTitle();
        if(startTime == null)
            startTime = new String();
        if(endTime == null)
            endTime = new GetCurTime().get();
        String word = searchframe.getText().toString();
        LoadNews ln;
        if(!cate.equals("全部"))
        {
            ln = new LoadNews(frag, 15, startTime, endTime, word, cate);
        }
        else
        {
            ln = new LoadNews(frag, 15, startTime, endTime, word, "");
        }
        ln.launch();
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
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        searchframe.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if((keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                    && (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyEvent != null)
                    && (!searchframe.getText().toString().isEmpty())) {

                    search();
                    return true;
                }
                return false;
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
        if(resultCode == 101)
        {
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
        else if(resultCode == 1)
        {
            GetCurTime s = new GetCurTime(data.getLongExtra("start", 0));
            GetCurTime e = new GetCurTime(data.getLongExtra("end", new Date().getTime()));
            startTime = s.get();
            endTime = e.get();
        }
    }

    public static void updateHis(News n){
        int l = hisList.size();
        for(int i = 0; i < l; i++){
            if(hisList.get(i).url.equals(n.url))
            {
                hisList.remove(i);
                break;
            }
        }
        hisList.add(n);
    }

    public static void deleteFav(News n){
        int l = favList.size();
        for(int i = 0; i < l; i++){
            if(favList.get(i).url.equals(n.url))
            {
                favList.remove(i);
                break;
            }
        }
    }

    public static boolean isHistory(News n){
        int l = hisList.size();
        for(int i = 0; i < l; i++){
            if(hisList.get(i).url.equals(n.url))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean isFavorite(News n){
        int l = favList.size();
        for(int i = 0; i < l; i++){
            if(favList.get(i).url.equals(n.url))
            {
                return true;
            }
        }
        return false;
    }
}




