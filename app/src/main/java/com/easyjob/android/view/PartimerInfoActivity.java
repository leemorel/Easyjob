package com.easyjob.android.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyjob.android.R;
import com.easyjob.android.bean.Info;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.Bmob;

public class PartimerInfoActivity extends AppCompatActivity {
    private ImageView iv_partimer_avatar;
    private TextView tv_partimer_name,tv_partimer_sex,tv_partimer_age,tv_partimer_phone,
            tv_partimer_email,tv_partimer_introduction,tv_partimer_height,tv_partimer_eduction;
    private Button bt_modify_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partimer_info);
        getSupportActionBar().setTitle("个人信息");
        initView();
        showInfo();
    }
    private void initView(){

        Bmob.initialize(this, Info.getAppid());
        tv_partimer_name = (TextView) findViewById(R.id.tv_partimer_name);
        tv_partimer_age = (TextView) findViewById(R.id.tv_partimer_age);
        tv_partimer_eduction = (TextView) findViewById(R.id.tv_partimer_education);
        tv_partimer_sex = (TextView) findViewById(R.id.tv_partimer_sex);
        tv_partimer_email = (TextView) findViewById(R.id.tv_partimer_email);
        tv_partimer_phone = (TextView) findViewById(R.id.tv_partimer_phone);
        tv_partimer_introduction = (TextView) findViewById(R.id.tv_partimer_introduction);
        tv_partimer_height = (TextView) findViewById(R.id.tv_partimer_height);
        iv_partimer_avatar = (ImageView) findViewById(R.id.iv_partimer_avatar);
        bt_modify_info = (Button) findViewById(R.id.bt_modify_info);
        bt_modify_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PartimerInfoActivity.this,ModifyPartimerInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showInfo(){
        tv_partimer_name.setText(Info.getP_name());
        tv_partimer_age.setText(Info.getP_age());
        tv_partimer_eduction.setText(Info.getP_education());
        tv_partimer_sex.setText(Info.getP_sex());
        tv_partimer_email.setText(Info.getP_email());
        tv_partimer_phone.setText(Info.getP_phone());
        tv_partimer_introduction.setText(Info.getP_introduction());
        tv_partimer_height.setText(Info.getP_height());
        Picasso.with(PartimerInfoActivity.this).load(Info.getP_avatar()).into(iv_partimer_avatar);
    }
}
