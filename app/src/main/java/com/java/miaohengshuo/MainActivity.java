package com.java.miaohengshuo;

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
import com.java.data.LoadNews;

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
import android.widget.Toast;

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
                View myview = inflater.inflate(R.layout.time_set_dialog,null);//?????????????????????
                AlertDialog.Builder builder = new AlertDialog.Builder(settimebtn.getContext());
                builder.setView(myview);
                dialog = builder.create();//???????????????
                dialog.show();//???????????????
                myview.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener(){//????????????????????????
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();//???????????????????????????
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
        Toast.makeText(this, "?????????", Toast.LENGTH_SHORT).show();
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
        if(!cate.equals("??????"))
        {
            ln = new LoadNews(frag, 15, startTime, endTime, word, cate);
        }
        else
        {
            ln = new LoadNews(frag, 15, startTime, endTime, word, "");
        }
        ln.launch();
        Toast.makeText(this, "?????????", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        //?????????
        viewPager = findViewById(R.id.vp_news);
        tab_channel = findViewById(R.id.tab_cate);
        set_channelBtn = findViewById(R.id.btn_setChannel);
        tab_channel.setTabMode(TabLayout.MODE_SCROLLABLE);
        //???????????????
        tab_adapt = new TabAdapter(getSupportFragmentManager(), fraglist);
        viewPager.setAdapter(tab_adapt);
        //??????
        tab_channel.setupWithViewPager(viewPager);
        //????????????
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
        channelBeanList.add(new ChannelBean("??????",true));//true???????????????title
        channelBeanList.add(new ChannelBean("??????",true));
        channelBeanList.add(new ChannelBean("??????",true));
        channelBeanList.add(new ChannelBean("??????",true));
        channelBeanList.add(new ChannelBean("??????",true));
        channelBeanList.add(new ChannelBean("??????",false));//false??????????????????title
        channelBeanList.add(new ChannelBean("??????",false));
        channelBeanList.add(new ChannelBean("??????",false));
        channelBeanList.add(new ChannelBean("??????",false));
        channelBeanList.add(new ChannelBean("??????",false));
        channelBeanList.add(new ChannelBean("??????",false));
        for (int i = 0; i < channelBeanList.size(); i++) {//?????????????????????
            if(channelBeanList.get(i).isSelect()){
                //???isSelect??? true????????????title???
                tab_channel.addTab(tab_channel.newTab().setText(channelBeanList.get(i).getName()));
                fraglist.add(new NewsViewFragment(channelBeanList.get(i).getName()));
            }
        }
        //???????????????
        tab_adapt.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 101)
        {
            //???????????????????????? ?????????json???
            String jsonString = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
            //??????
            Gson gson = new Gson();
            //?????????????????????  ???TypeToken??????
            channelBeanList = gson.fromJson(jsonString,
                    new TypeToken<ArrayList<ChannelBean>>() {}.getType());
            //??????tablayout
            tab_channel.removeAllTabs();
            //??????fragment
            fraglist.clear();
            //????????????tablayout???fragment
            for (int i = 0; i < channelBeanList.size(); i++) {
                if(channelBeanList.get(i).isSelect()){
                    tab_channel.addTab(tab_channel.newTab().setText(channelBeanList.get(i).getName()));
                    fraglist.add(new NewsViewFragment(channelBeanList.get(i).getName()));
                }
            }
            //???????????????
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




