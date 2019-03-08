package com.easyjob.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

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
        Bmob.initialize(this,Info.appid);
        setContentView(R.layout.activity_system_notifications);

        //获取控件
        mrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mrecyclerView.setHasFixedSize(true);




        BmobQuery<Notification> query = new BmobQuery<Notification>();
        query.order("-createdAt");
        query.findObjects(new FindListener<Notification>() {
            @Override
            public void done(List<Notification> list, BmobException e) {
                if(e==null){
                    List<Notification> notificationList = new ArrayList<>();
                    notificationList.addAll(list);
                    NotificationAdapter nofiticationAdapter = new NotificationAdapter(notificationList);
                    mrecyclerView.setAdapter(nofiticationAdapter);
                }
                else {
                    Log.i("bmob获取通知","获取通知失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });


        layoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(layoutManager);

    }
}
