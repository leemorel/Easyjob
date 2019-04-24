package com.easyjob.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyjob.android.view.JobInfoActivity;
import com.easyjob.android.bean.ParttimeJob;
import com.easyjob.android.R;

import java.util.ArrayList;
import java.util.List;


public class ParttimejobAdapter extends RecyclerView.Adapter<ParttimejobAdapter.MyViewHolder> {
    private Context context;
    List<ParttimeJob> parttimeJobList = new ArrayList<>();

    public ParttimejobAdapter(Context context,List<ParttimeJob> parttimeJobList){
        this.parttimeJobList=parttimeJobList;
        this.context = context;
    }
    //  创建MyViewHolder,初始化控件
    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_j_title;
        public TextView tv_j_salary;

        public MyViewHolder(View itemView) {

            super(itemView);
            //获取子布局的控件实例
            tv_j_title = (TextView) itemView.findViewById(R.id.tv_j_title);
            tv_j_salary = (TextView) itemView.findViewById(R.id.tv_j_salary);
        }
    }
        //ViewHolder就是一个容器，它放你要展示的item里面的控件内容
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View item =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.partimejob,viewGroup,false);   //获取notifications.xml
            MyViewHolder myViewHolder = new MyViewHolder(item);
            return myViewHolder;

        }

        //操作item，为item填充数据了
        public void onBindViewHolder(ParttimejobAdapter.MyViewHolder holder, final int position) {
            final ParttimeJob parttimejob = getItem(position);
            holder.tv_j_title.setText(parttimejob.getJ_title());
            holder.tv_j_salary.setText(parttimejob.getJ_salary());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,JobInfoActivity.class);
                    intent.putExtra("id",getItem(position).getObjectId());
                    context.startActivity(intent);
                }
            });
        }
        protected ParttimeJob getItem(int position){
            //获取内容
            return parttimeJobList.get(position);
        }
        @Override
        public int getItemCount() {
            return parttimeJobList.size();
        }

    }

