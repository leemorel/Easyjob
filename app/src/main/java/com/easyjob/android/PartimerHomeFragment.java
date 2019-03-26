package com.easyjob.android;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class PartimerHomeFragment extends Fragment {
    private SwipeRefreshLayout swipe_refresh_layout;
    private View mView;
    private RecyclerView mrecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Banner mBanner;
    private RelativeLayout rl_location;
    private ArrayList<String> listpath;
    private TextView tv_city;

    public PartimerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_partimer_home, container, false);
        initView();
        initData();
        getdata(Info.city);
        initBanner();
        return mView;
    }

    private void initView(){

        Bmob.initialize(getActivity(), Info.appid);
        listpath = new ArrayList<>();
        rl_location = (RelativeLayout) mView.findViewById(R.id.rl_location);
        tv_city = (TextView) mView.findViewById(R.id.tv_city);
        mrecyclerView = (RecyclerView) mView.findViewById(R.id.my_phome_view);
        swipe_refresh_layout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        mrecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());

        mrecyclerView.setLayoutManager(layoutManager);            //设置布局方式
        //下拉刷新
        swipe_refresh_layout.setColorSchemeResources(R.color.colorAccent);

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置刷新监听器
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getdata(Info.city);
                        swipe_refresh_layout.setRefreshing(false);

                    }
                }, 1500);
            }
        });



        rl_location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                List<HotCity> hotCities = new ArrayList<>();
                hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
                hotCities.add(new HotCity("上海", "上海", "101020100"));
                hotCities.add(new HotCity("广州", "广东", "101280101"));
                hotCities.add(new HotCity("深圳", "广东", "101280601"));
                hotCities.add(new HotCity("杭州", "浙江", "101210101"));
                hotCities.add(new HotCity("韶关", "广东", "101210101"));
                CityPicker.from(PartimerHomeFragment.this)
                        .enableAnimation(true)
                        .setHotCities(hotCities)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                Info.city = data.getName().replace("市","");
                                getdata(Info.city);
                                tv_city.setText(data.getName());
                            }

                            @Override
                            public void onCancel(){
                                Toast.makeText(getActivity(), "取消选择", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onLocate() {


                                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                        != PackageManager.PERMISSION_GRANTED){//未开启定位权限
                                    //开启定位权限,200是标识码
                                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
                                }else {
                                    Toast.makeText(getContext(), "已开启定位权限", Toast.LENGTH_LONG).show();
                                    Locatieing();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //定位完成之后更新数据
                                            CityPicker.from(PartimerHomeFragment.this).locateComplete(new LocatedCity(Info.city,"",""),LocateState.SUCCESS);
                                        }
                                    }, 3000);
                                }
                            }
                        })
                        .show();
            }
        });

    }
    private void initData(){
        listpath.add("http://bmob-cdn-16376.b0.upaiyun.com/2019/03/24/4f9ead90402b655580c632da9a7899de.jpg");
        listpath.add("http://bmob-cdn-16376.b0.upaiyun.com/2019/03/24/98f3c4e0404313a28021c45fcc6718e3.png");
}

    private void initBanner(){
        View header = LayoutInflater.from(getContext()).inflate(R.layout.p_hearder, null);
        mBanner = header.findViewById(R.id.banner);

        mBanner.setImageLoader(new GlideImageLoader())
                .setImages(listpath)
                .start();
    }

    private void Locatieing(){
        AMapLocationClient mlocationClient;
        AMapLocationClientOption mLocationOption = null;
        mlocationClient = new AMapLocationClient(getContext());
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        Info.city = amapLocation.getCity();
                        Log.i("city","city= "+Info.city);
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        });
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }

    private void getdata(String city) {
        BmobQuery<ParttimeJob> query = new BmobQuery<ParttimeJob>();
        query.addWhereEqualTo("j_city",city);
        query.order("-createdAt");
        query.findObjects(new FindListener<ParttimeJob>() {
            @Override
            public void done(List<ParttimeJob> list, BmobException e) {
                if (e == null) {
                    List<ParttimeJob> parttimejobList = new ArrayList<>();
                    parttimejobList.addAll(list);     //添加数据到notificationList中
                    PartimejobViewAdapter partimejobViewAdapter = new PartimejobViewAdapter(getContext(), parttimejobList);
                    partimejobViewAdapter.setHeader(listpath);
                    mrecyclerView.setAdapter(partimejobViewAdapter);     //设置适配器
                } else {
                    Log.i("bmob获取通知", "获取通知失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }


//
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Picasso.with(context).load(path.toString()).into(imageView);

        }

    }
}
