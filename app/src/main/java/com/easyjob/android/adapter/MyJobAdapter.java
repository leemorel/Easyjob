package com.easyjob.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyjob.android.view.JobInfoView1Activity;
import com.easyjob.android.R;
import com.easyjob.android.bean.Enroll_Info;

import java.util.ArrayList;
import java.util.List;

public class MyJobAdapter extends RecyclerView.Adapter<MyJobAdapter.MyViewHolder> {
    List<Enroll_Info> enrollInfo = new ArrayList<>();
    private Context mcontext;
    public MyJobAdapter(Context context,List<Enroll_Info> enrollInfo){
        this.enrollInfo = enrollInfo;
        this.mcontext =  context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myjob,viewGroup,false);   //获取notifications.xml
        MyViewHolder myViewHolder = new MyViewHolder(item);
        return myViewHolder;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_job,tv_employment,tv_company;
        public MyViewHolder(View itemView){
            super(itemView);
            tv_company = itemView.findViewById(R.id.tv_company);
            tv_job = itemView.findViewById(R.id.tv_job);
            tv_employment = itemView.findViewById(R.id.tv_employment);
        }
    }
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final Enroll_Info enroll_info = getItem(position);
        holder.tv_job.setText(enroll_info.getJob());
        holder.tv_company.setText(enroll_info.getCompany());
        int s=enroll_info.getState();
        switch (s){
            case 0:
                holder.tv_employment.setText("还未处理");
                break;
            case 1:
                holder.tv_employment.setText("已被录用");
                holder.tv_employment.setTextColor(Color.parseColor("#FF4081"));
                break;
            case -1:
                holder.tv_employment.setText("未被录用");
                holder.tv_employment.setTextColor(Color.parseColor("#B7B7B7"));
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, JobInfoView1Activity.class);
                intent.putExtra("rid",getItem(position).getRecruiter().getObjectId());
                intent.putExtra("jid",getItem(position).getParttimejob().getObjectId());
                mcontext.startActivity(intent);
            }
        });
    }

    protected Enroll_Info getItem(int position){
        return enrollInfo.get(position);
    }
    @Override
    public int getItemCount() {
        return enrollInfo.size();
    }

}
