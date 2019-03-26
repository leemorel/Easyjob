package com.easyjob.android;

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
                Picasso.with(JobInfoViewActivity.this).load(Info.ravatar).into(iv_recruiter_avatar);

            }
        }, 800);
        mMapView.onResume();
    }


    private void initview() {
        Bmob.initialize(this, Info.appid);
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
        Info.recruiter_id = cid;
        Info.j_id = id;

        bt_appy_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobQuery<Enroll_Info> query = new BmobQuery<>();
                query.addWhereEqualTo("parttimejob",Info.j_id);
                query.addWhereEqualTo("recruiter",Info.recruiter_id);
                query.addWhereEqualTo("partimer",Info.p_id);
                query.findObjects(new FindListener<Enroll_Info>() {
                    @Override
                    public void done(List<Enroll_Info> list, BmobException e) {
                    if(list.size()==0){
                        Recruiter_Info r = new Recruiter_Info();
            r.setObjectId(Info.recruiter_id);
            ParttimeJob j = new ParttimeJob();
            j.setObjectId(Info.j_id);
            Partimer_Info p = new Partimer_Info();
            p.setObjectId(Info.p_id);
            Enroll_Info e1 = new Enroll_Info();
            e1.setPartimer(p);
            e1.setParttimejob(j);
            e1.setRecruiter(r);
            e1.setState(0);
            e1.setCompany(Info.rcompany);
            e1.setAppyer(Info.p_name);
            e1.setJob(Info.jtitle);
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




//            Recruiter_Info r = new Recruiter_Info();
//            r.setObjectId(Info.recruiter_id);
//            ParttimeJob j = new ParttimeJob();
//            j.setObjectId(Info.j_id);
//            Partimer_Info p = new Partimer_Info();
//            p.setObjectId(Info.p_id);
//            Enroll_Info e = new Enroll_Info();
//            e.setPartimer(p);
//            e.setParttimejob(j);
//            e.setRecruiter(r);
//            e.setState(0);
//            e.save(new SaveListener<String>() {
//                @Override
//                public void done(String s, BmobException e) {
//                    if(e==null){
//                        Toast.makeText(JobInfoViewActivity.this,"报名成功",Toast.LENGTH_SHORT).show();
//                    }else{
//                        Log.i("bmob","失败："+e.getMessage());
//                    }
//                }
//            });
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
                    Info.jtitle=parttimeJob.getJ_title();
                    Info.jaddress=parttimeJob.getJ_address();
                    Info.jdetails=parttimeJob.getJ_details();
                    Info.jrequirement=parttimeJob.getJ_requirement();
                    Info.jsalayr=parttimeJob.getJ_salary();
                    Info.jtime=parttimeJob.getJ_time();
                    Info.rcompany=parttimeJob.getJ_company();

                    address = Info.jaddress;
                    tv_j_address.setText(Info.jaddress);
                    tv_j_details.setText(Info.jdetails);
                    tv_j_title.setText(Info.jtitle);
                    tv_j_requirement.setText(Info.jrequirement);
                    tv_j_salary.setText(Info.jsalayr);
                    tv_j_time.setText(Info.jtime);
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
                    Info.rcompany=recruiter_info.getRecruiter_company();
                    Info.raddress=recruiter_info.getRecruiter_address();
                    Info.remail=recruiter_info.getRecruiter_email();
                    Info.rphone=recruiter_info.getRecruiter_phone();
                    Info.rprofile=recruiter_info.getRecruiter_profile();
                    Info.ravatar = recruiter_info.getRecruiter_avatar().getFileUrl();
                    tv_recruiter_name.setText( Info.rcompany);
                    tv_recruiter_email.setText(Info.remail);
                    tv_recruiter_phone.setText(Info.rphone);
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

