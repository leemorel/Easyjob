package com.easyjob.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppyerAdapter extends RecyclerView.Adapter<AppyerAdapter.MyViewHolder> {
    List<Enroll_Info> enrollInfo = new ArrayList<>();
    private Context mcontext;
    public AppyerAdapter(Context context,List<Enroll_Info> enrollInfo){
        this.enrollInfo = enrollInfo;
        this.mcontext =  context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appyers,viewGroup,false);   //获取notifications.xml
        MyViewHolder myViewHolder = new MyViewHolder(item);
        return myViewHolder;

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_appy_name,tv_employment;
        public MyViewHolder(View itemView){
            super(itemView);
            tv_appy_name = itemView.findViewById(R.id.tv_appy_name);
            tv_employment = itemView.findViewById(R.id.tv_employment);
        }
    }

    public void onBindViewHolder(MyViewHolder holder, final int position){
        final Enroll_Info enroll_info = getItem(position);
        holder.tv_appy_name.setText(enroll_info.getAppyer());
        int s=enroll_info.getState();
        switch (s){
            case 0:
                holder.tv_employment.setText("待处理");
                break;
            case 1:
                holder.tv_employment.setText("已录用");
                holder.tv_employment.setTextColor(Color.parseColor("#FF4081"));
                break;
            case -1:
                holder.tv_employment.setText("不录用");
                holder.tv_employment.setTextColor(Color.parseColor("#B7B7B7"));
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, PartimerInfoViewActivity.class);
                intent.putExtra("eid",getItem(position).getObjectId());
                intent.putExtra("pid",getItem(position).getPartimer().getObjectId());
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
