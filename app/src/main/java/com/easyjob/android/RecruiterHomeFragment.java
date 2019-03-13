package com.easyjob.android;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecruiterHomeFragment extends Fragment {
    private SwipeRefreshLayout swipe_refresh_layout;
    private View mView;
    private RecyclerView mrecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton bt_job_add;
    public RecruiterHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bmob.initialize(getActivity(),Info.appid);

        mView=inflater.inflate(R.layout.fragment_recruiter_home, container, false);
        mrecyclerView = (RecyclerView) mView.findViewById(R.id.my_rhome_view);
        swipe_refresh_layout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        mrecyclerView.setHasFixedSize(true);         //用来使RecyclerView保持固定的大小
        bt_job_add=(ImageButton) mView.findViewById(R.id.bt_job_add);
        layoutManager = new LinearLayoutManager(getActivity());

        mrecyclerView.setLayoutManager(layoutManager);            //设置布局方式
        //下拉刷新
        swipe_refresh_layout.setColorSchemeResources(R.color.colorAccent);


        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置刷新监听器
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {//模拟耗时操作
                    @Override
                    public void run() {
                        getdata();
                        swipe_refresh_layout.setRefreshing(false);//取消刷新

                    }
                },1500);
            }
        });


        getdata();

        bt_job_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddjobActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }


    private void getdata(){
        BmobQuery<ParttimeJob> query = new BmobQuery<ParttimeJob>();
        Recruiter_Info recruiter_info = new Recruiter_Info();
        recruiter_info.setObjectId(Info.recruiter_id);
        query.addWhereEqualTo("company",new BmobPointer(recruiter_info));
        query.order("-createdAt");
        query.findObjects(new FindListener<ParttimeJob>() {
            @Override
            public void done(List<ParttimeJob> list, BmobException e) {
                if(e==null){
                    List<ParttimeJob> parttimejobList = new ArrayList<>();
                    parttimejobList.addAll(list)     ;     //添加数据到notificationList中
                    ParttimejobAdapter parttimejobAdapter = new ParttimejobAdapter(getContext(),parttimejobList);
                    mrecyclerView.setAdapter(parttimejobAdapter);     //设置适配器
                }
                else {
                    Log.i("bmob获取通知","获取通知失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

}
