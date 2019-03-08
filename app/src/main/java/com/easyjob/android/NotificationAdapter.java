package com.easyjob.android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

 public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    List<Notification> notificationList=new ArrayList<>();  //数据集合
    public NotificationAdapter(List<Notification> notificationList){
        this.notificationList=notificationList;             //数据初始化
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View item =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notifications,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(item);
        return myViewHolder;

    }

    // Replace the contents of a view (invoked by the layout manager)
     //操作item
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Notification notification = getItem(position);
        holder.tv_notification.setText(notification.getNotification());
        holder.tv_notification_time.setText(notification.getUpdateAt_data());
    }
     protected Notification getItem(int position){
         //获取内容
         return notificationList.get(position);
     }



     //  初始化控件
      class MyViewHolder extends RecyclerView.ViewHolder {

         public TextView tv_notification;
         public TextView tv_notification_time;
         public MyViewHolder(View itemView) {
             super(itemView);
             //获取子布局的控件实例
             tv_notification=(TextView) itemView.findViewById(R.id.tv_notification);
             tv_notification_time=(TextView) itemView.findViewById(R.id.tv_notification_time);

         }
     }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}

