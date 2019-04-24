package com.easyjob.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.easyjob.android.view.JobInfoViewActivity;
import com.easyjob.android.bean.ParttimeJob;
import com.easyjob.android.R;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

//public class PartimejobViewAdapter extends RecyclerView.Adapter<PartimejobViewAdapter.MyViewHolder> {
//
//    public static final int TYPE_HEADER = 0;
//    public static final int TYPE_BODY = 1;
//    private Context mcontext;
//    private View mHeaderView;
//
//    public void setHeaderView(View headerView) {
//        mHeaderView = headerView;
//    }
//
//    private List<ParttimeJob> parttimeJobList = new ArrayList<>();
//
//
//    public PartimejobViewAdapter(Context context, List<ParttimeJob> parttimeJobList) {
//        this.parttimeJobList = parttimeJobList;
//        this.mcontext = context;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0)
//            return TYPE_HEADER;
//        else if (position == 1)
//            return TYPE_BODY;
//        else
//            return TYPE_BODY;
//    }
//
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//
//
//        public TextView tv_j_title;
//        public TextView tv_j_salary;
//        public TextView tv_j_company;
//        public TextView tv_j_city;
//        public TextView tv_j_time;
//
//        public MyViewHolder(View itemView) {
//
//            super(itemView);
//            //获取子布局的控件实例
//            tv_j_title = (TextView) itemView.findViewById(R.id.tv_j_title);
//            tv_j_salary = (TextView) itemView.findViewById(R.id.tv_j_salary);
//            tv_j_city = (TextView) itemView.findViewById(R.id.tv_j_city);
//            tv_j_company = (TextView) itemView.findViewById(R.id.tv_j_company);
//            tv_j_time = (TextView) itemView.findViewById(R.id.tv_j_time);
//        }
//    }
//
//    //ViewHolder就是一个容器，它放你要展示的item里面的控件内容
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        if (viewType == TYPE_HEADER) {
//            return new MyViewHolder(mHeaderView);
//        }
//        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.p_job, viewGroup, false);   //获取notifications.xml
//        MyViewHolder myViewHolder = new MyViewHolder(item);
//        return myViewHolder;
//
//    }
//
//
//
//
//    //操作item，为item填充数据了
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        if (getItemViewType(position) == TYPE_HEADER)
//            return;
//        final ParttimeJob parttimejob = getItem(position);
//        holder.tv_j_title.setText(parttimejob.getJ_title());
//        holder.tv_j_salary.setText(parttimejob.getJ_salary());
//        holder.tv_j_time.setText(parttimejob.getJ_time());
//        holder.tv_j_company.setText(parttimejob.getJ_company());
//        holder.tv_j_city.setText(parttimejob.getJ_city());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mcontext, JobInfoActivity.class);
//                intent.putExtra("id", getItem(position).getObjectId());
//                mcontext.startActivity(intent);
//            }
//        });
//    }
//
//
//
//
//    protected ParttimeJob getItem(int position){
//        //获取内容
//        return parttimeJobList.get(position-1);
//    }
//
//
//    @Override
//    public int getItemCount() {
//        if(parttimeJobList!=null) {
//            return parttimeJobList.size()+1;
//        } return 0;
//
//    }
//
//
//}

public class PartimejobViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_BODY = 1;
    private Context mcontext;
    private View mHeaderView;
    private List<ParttimeJob> parttimeJobList = new ArrayList<>();
    private ArrayList listpath = new ArrayList<>();


    public void setHeader(ArrayList listpath) {
        this.listpath = listpath;
    }




    public  PartimejobViewAdapter(Context context,List<ParttimeJob> parttimeJobList){
        this.parttimeJobList=parttimeJobList;
        this.mcontext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return TYPE_HEADER;
        else if(position==1)
            return TYPE_BODY;
        else
            return TYPE_BODY;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.p_hearder, viewGroup, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.p_job, viewGroup, false);
            return new BodyViewHolder(view);
        }
    }


    class  HeaderViewHolder extends RecyclerView.ViewHolder{
        public Banner banner;
        public HeaderViewHolder(View itemView){
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
            banner.setImageLoader(new GlideImageLoader())
                    .setImages(listpath)
                    .setDelayTime(3000)
                    .start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("https://www.baidu.com/");//此处填链接
                    intent.setData(content_url);
                    mcontext.startActivity(intent);
                }
            });
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Picasso.with(context).load(path.toString()).into(imageView);

        }

    }

    class BodyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_j_title;
        public TextView tv_j_salary;
        public TextView tv_j_company;
        public TextView tv_j_city;
        public TextView tv_j_time;

        public BodyViewHolder(View itemView) {

            super(itemView);
            //获取子布局的控件实例
            tv_j_title = (TextView) itemView.findViewById(R.id.tv_j_title);
            tv_j_salary = (TextView) itemView.findViewById(R.id.tv_j_salary);
            tv_j_city = (TextView) itemView.findViewById(R.id.tv_j_city);
            tv_j_company = (TextView) itemView.findViewById(R.id.tv_j_company);
            tv_j_time = (TextView) itemView.findViewById(R.id.tv_j_time);
        }
    }

    //操作item，为item填充数据了
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        if (holder instanceof HeaderViewHolder) {
            return;
        } else{

            BodyViewHolder holder1 = (BodyViewHolder)holder;
            final ParttimeJob parttimejob = getItem(position);
            holder1.tv_j_title.setText(parttimejob.getJ_title());
            holder1.tv_j_salary.setText(parttimejob.getJ_salary());
            holder1.tv_j_time.setText(parttimejob.getJ_time());
            holder1.tv_j_company.setText(parttimejob.getJ_company());
            holder1.tv_j_city.setText(parttimejob.getJ_city());
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, JobInfoViewActivity.class);
                    intent.putExtra("id", getItem(position).getObjectId());
                    intent.putExtra("cid",getItem(position).getCompany().getObjectId());
                    mcontext.startActivity(intent);
                }
            });
        }
    }




    protected ParttimeJob getItem(int position){
        //获取内容
        return parttimeJobList.get(position-1);
    }


    @Override
    public int getItemCount() {
        if(parttimeJobList!=null) {
            return parttimeJobList.size()+1;
        } return 0;

    }


}
