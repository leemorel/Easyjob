package com.easyjob.android.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.easyjob.android.R;
import com.easyjob.android.bean.Enroll_Info;
import com.easyjob.android.bean.Info;
import com.easyjob.android.bean.Partimer_Info;
import com.easyjob.android.bean.ParttimeJob;
import com.easyjob.android.bean.Recruiter_Info;
import com.squareup.picasso.Picasso;


import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class JobInfoViewActivity extends AppCompatActivity {
    private TextView tv_j_title, tv_j_salary, tv_j_address, tv_j_requirement, tv_j_details, tv_j_time;
    private TextView tv_recruiter_name,tv_recruiter_phone,tv_recruiter_email;
    private ImageView iv_recruiter_avatar;
    private RelativeLayout rl_company;
    private Button bt_appy_job;
    private MapView mMapView;
    private UiSettings mUiSettings;
    private String id,cid, address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_info_view);
        initview();
        mMapView.onCreate(savedInstanceState);
        showjob();

    }

    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GeocodeSearch(address);
                Picasso.with(JobInfoViewActivity.this).load(Info.getRavatar()).into(iv_recruiter_avatar);

            }
        }, 800);
        mMapView.onResume();
    }


    private void initview() {
        Bmob.initialize(this, Info.getAppid());
        getSupportActionBar().setTitle("兼职信息");
        tv_j_title = (TextView) findViewById(R.id.tv_j_title);
        tv_j_address = (TextView) findViewById(R.id.tv_j_address);
        tv_j_details = (TextView) findViewById(R.id.tv_j_details);
        tv_j_salary = (TextView) findViewById(R.id.tv_j_salary);
        tv_j_requirement = (TextView) findViewById(R.id.tv_j_requirement);
        tv_j_time = (TextView) findViewById(R.id.tv_j_time);
        bt_appy_job = (Button) findViewById(R.id.bt_appy_job);
        iv_recruiter_avatar = findViewById(R.id.iv_recruiter_avatar);
        tv_recruiter_name = findViewById(R.id.tv_recruiter_name);
        tv_recruiter_phone = findViewById(R.id.tv_recruiter_phone);
        tv_recruiter_email = findViewById(R.id.tv_recruiter_email);
        rl_company = findViewById(R.id.rl_company);
        mMapView = (MapView) findViewById(R.id.map);


        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        cid = intent.getStringExtra("cid");
        Info.setRecruiter_id(cid);
        Info.setJ_id(id);

        bt_appy_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobQuery<Enroll_Info> query = new BmobQuery<>();
                query.addWhereEqualTo("parttimejob",Info.getJ_id());
                query.addWhereEqualTo("recruiter",Info.getRecruiter_id());
                query.addWhereEqualTo("partimer",Info.getP_id());
                query.findObjects(new FindListener<Enroll_Info>() {
                    @Override
                    public void done(List<Enroll_Info> list, BmobException e) {
                    if(list.size()==0){
                        Recruiter_Info r = new Recruiter_Info();
            r.setObjectId(Info.getRecruiter_id());
            ParttimeJob j = new ParttimeJob();
            j.setObjectId(Info.getJ_id());
            Partimer_Info p = new Partimer_Info();
            p.setObjectId(Info.getP_id());
            Enroll_Info e1 = new Enroll_Info();
            e1.setPartimer(p);
            e1.setParttimejob(j);
            e1.setRecruiter(r);
            e1.setState(0);
            e1.setCompany(Info.getRcompany());
            e1.setAppyer(Info.getP_name());
            e1.setJob(Info.getJtitle());
            e1.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        Toast.makeText(JobInfoViewActivity.this,"报名成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("bmob","失败："+e.getMessage());
                    }
                }
            });
                    }
                    else {
                        Toast.makeText(JobInfoViewActivity.this,"只能报一次名哟",Toast.LENGTH_SHORT).show();
                    }
                    }
                });




            }
        });

        rl_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(JobInfoViewActivity.this,CompanyInfoViewActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void GeocodeSearch(String city) {

        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                    if(i==1000){
                        double latitude=geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude();
                        double longitude=geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude();
                        LatLng la=new LatLng(latitude,longitude);
                        AMap aMap = mMapView.getMap();
                        mUiSettings = aMap.getUiSettings();
                         mUiSettings.setZoomControlsEnabled(false);
                        final Marker marker = aMap.addMarker(new MarkerOptions().position(la).title("").snippet(""));
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(la,16.0f));
                    }else {
                        String s= i+"";
                        Log.e("iii=",s);
                    }


            }
        });
        GeocodeQuery query = new GeocodeQuery(address, city);
        geocodeSearch.getFromLocationNameAsyn(query);
    }



    private void showjob(){
        BmobQuery<ParttimeJob> query = new BmobQuery<ParttimeJob>();
        query.getObject(id, new QueryListener<ParttimeJob>() {
            @Override
            public void done(ParttimeJob parttimeJob, BmobException e) {
                if(e==null){
                    Info.setJtitle(parttimeJob.getJ_title());
                    Info.setJaddress(parttimeJob.getJ_address());
                    Info.setJdetails(parttimeJob.getJ_details());
                    Info.setJrequirement(parttimeJob.getJ_requirement());
                    Info.setJsalayr(parttimeJob.getJ_salary());
                    Info.setJtime(parttimeJob.getJ_time());
                    Info.setRcompany(parttimeJob.getJ_company());

                    address = Info.getJaddress();
                    tv_j_address.setText(Info.getJaddress());
                    tv_j_details.setText(Info.getJdetails());
                    tv_j_title.setText(Info.getJtitle());
                    tv_j_requirement.setText(Info.getJrequirement());
                    tv_j_salary.setText(Info.getJsalayr());
                    tv_j_time.setText(Info.getJtime());
                }
                else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        BmobQuery<Recruiter_Info> query1 = new BmobQuery<Recruiter_Info>();
        query1.addWhereEqualTo("objectId",cid);
        query1.getObject(cid, new QueryListener<Recruiter_Info>() {
            public void done(Recruiter_Info recruiter_info, BmobException e) {
                if(e==null){
                    Info.setRcompany(recruiter_info.getRecruiter_company());
                    Info.setRaddress(recruiter_info.getRecruiter_address());
                    Info.setRemail(recruiter_info.getRecruiter_email());
                    Info.setRphone(recruiter_info.getRecruiter_phone());
                    Info.setRprofile(recruiter_info.getRecruiter_profile());
                    Info.setRavatar(recruiter_info.getRecruiter_avatar().getFileUrl());
                    tv_recruiter_name.setText( Info.getRcompany());
                    tv_recruiter_email.setText(Info.getRemail());
                    tv_recruiter_phone.setText(Info.getRphone());
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


}

