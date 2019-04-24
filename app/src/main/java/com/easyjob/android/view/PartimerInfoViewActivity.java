package com.easyjob.android.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easyjob.android.R;
import com.easyjob.android.bean.Enroll_Info;
import com.easyjob.android.bean.Info;
import com.easyjob.android.bean.Partimer_Info;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class PartimerInfoViewActivity extends AppCompatActivity {
    private ImageView iv_partimer_avatar;
    private TextView tv_partimer_name,tv_partimer_sex,tv_partimer_age,tv_partimer_phone,
            tv_partimer_email,tv_partimer_introduction,tv_partimer_height,tv_partimer_eduction;
    private Button bt_employ,bt_unemploy;
    private String pid,eid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partimer_info_view);
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        eid = intent.getStringExtra("eid");
        initView();
        showInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.with(PartimerInfoViewActivity.this).load(Info.getP_avatar()).into(iv_partimer_avatar);
            }
        }, 800);
    }
    private void initView(){
        Bmob.initialize(this, Info.getAppid());
        getSupportActionBar().setTitle("个人信息");
        tv_partimer_name = (TextView) findViewById(R.id.tv_partimer_name);
        tv_partimer_age = (TextView) findViewById(R.id.tv_partimer_age);
        tv_partimer_eduction = (TextView) findViewById(R.id.tv_partimer_education);
        tv_partimer_sex = (TextView) findViewById(R.id.tv_partimer_sex);
        tv_partimer_email = (TextView) findViewById(R.id.tv_partimer_email);
        tv_partimer_phone = (TextView) findViewById(R.id.tv_partimer_phone);
        tv_partimer_introduction = (TextView) findViewById(R.id.tv_partimer_introduction);
        tv_partimer_height = (TextView) findViewById(R.id.tv_partimer_height);
        iv_partimer_avatar = (ImageView) findViewById(R.id.iv_partimer_avatar);
        bt_employ = (Button) findViewById(R.id.bt_employ);
        bt_unemploy = findViewById(R.id.bt_unemploy);
        bt_employ.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Enroll_Info e = new Enroll_Info();
        e.setState(1);
        e.update(eid,new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(PartimerInfoViewActivity.this, "录用成功,请尽快联系", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        }
    });
        bt_unemploy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enroll_Info e = new Enroll_Info();
                e.setState(-1);
                e.update(eid,new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(PartimerInfoViewActivity.this, "不录用  成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
            }
        });
}

    private void showInfo(){
        BmobQuery<Partimer_Info> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",pid);
        query.getObject(pid, new QueryListener<Partimer_Info>() {
            @Override
            public void done(Partimer_Info partimer_info, BmobException e) {
                if(e == null){
                    tv_partimer_name.setText(partimer_info.getP_name());
                    partimer_info.getP_phone();
                    tv_partimer_age.setText(partimer_info.getP_age());
                    tv_partimer_email.setText(partimer_info.getP_email());
                    tv_partimer_eduction.setText(partimer_info.getP_education());
                    tv_partimer_height.setText(partimer_info.getP_height());
                    tv_partimer_sex.setText(partimer_info.getP_sex());
                    tv_partimer_phone.setText(partimer_info.getP_phone());
                    tv_partimer_introduction.setText(partimer_info.getP_introduction());
                    Info.setP_avatar(partimer_info.getP_avatar().getFileUrl());
                    Picasso.with(PartimerInfoViewActivity.this).load(Info.getP_avatar()).into(iv_partimer_avatar);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }
}
