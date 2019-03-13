package com.easyjob.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SystemNotificationsActivity extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("   系统通知");
        Bmob.initialize(this,Info.appid);
        setContentView(R.layout.activity_system_notifications);


        mrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);   //获取控件
        mrecyclerView.setHasFixedSize(true);         //用来使RecyclerView保持固定的大小

        layoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(layoutManager);            //设置布局方式

        /*bmob获取，并添加到list中*/
        BmobQuery<Notification> query = new BmobQuery<Notification>();
        query.order("-createdAt");
        query.findObjects(new FindListener<Notification>() {
            @Override
            public void done(List<Notification> list, BmobException e) {
                if(e==null){
                    List<Notification> notificationList = new ArrayList<>();
                    notificationList.addAll(list);          //添加数据到notificationList中
                    NotificationAdapter nofiticationAdapter = new NotificationAdapter(notificationList);
                    mrecyclerView.setAdapter(nofiticationAdapter);      //设置适配器
                }
                else {
                    Log.i("bmob获取通知","获取通知失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });


    }


}
