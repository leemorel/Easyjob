package com.easyjob.android.view;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.easyjob.android.R;
import com.easyjob.android.bean.Enroll_Info;
import com.easyjob.android.adapter.MyJobAdapter;
import com.easyjob.android.bean.Info;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyJobActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipe_refresh_layout;
    private RecyclerView mrecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job);
        initView();
        showJobInfo();
    }

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    private void initView(){
        Bmob.initialize(this, Info.getAppid());
        getSupportActionBar().setTitle("我的兼职");
        swipe_refresh_layout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        mrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);   //获取控件
        mrecyclerView.setHasFixedSize(true);         //用来使RecyclerView保持固定的大小

        layoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(layoutManager);            //设置布局方式

    }

    private void refresh(){
        swipe_refresh_layout.setColorSchemeResources(R.color.colorAccent);


        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置刷新监听器
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {//模拟耗时操作
                    @Override
                    public void run() {
                        showJobInfo();
                        swipe_refresh_layout.setRefreshing(false);//取消刷新

                    }
                },1500);
            }
        });
    }
    private void showJobInfo(){
        BmobQuery<Enroll_Info> query = new BmobQuery<Enroll_Info>();
        query.addWhereEqualTo("partimer",Info.getP_id());
        query.order("-createdAt");
        query.findObjects(new FindListener<Enroll_Info>() {
            @Override
            public void done(List<Enroll_Info> list, BmobException e) {
                if(e==null){
                    List<Enroll_Info> enrollList = new ArrayList<>();
                    enrollList.addAll(list);
                    MyJobAdapter myJobAdapter = new MyJobAdapter(MyJobActivity.this,enrollList);
                    mrecyclerView.setAdapter(myJobAdapter);
                }
                else {
                    Log.i("bmob获取通知","获取通知失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }



}
