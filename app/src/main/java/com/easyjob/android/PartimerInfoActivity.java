package com.easyjob.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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

        Bmob.initialize(this, Info.appid);
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
        tv_partimer_name.setText(Info.p_name);
        tv_partimer_age.setText(Info.p_age);
        tv_partimer_eduction.setText(Info.p_education);
        tv_partimer_sex.setText(Info.p_sex);
        tv_partimer_email.setText(Info.p_email);
        tv_partimer_phone.setText(Info.p_phone);
        tv_partimer_introduction.setText(Info.p_introduction);
        tv_partimer_height.setText(Info.p_height);
        Picasso.with(PartimerInfoActivity.this).load(Info.p_avatar).into(iv_partimer_avatar);
    }
}
