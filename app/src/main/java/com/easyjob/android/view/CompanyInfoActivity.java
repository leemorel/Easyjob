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

public class CompanyInfoActivity extends AppCompatActivity {
    private TextView recruiter_company,recruiter_phone,recruiter_email,recruiter_introduction,recruiter_address;
    private Button bt_modify_company_info;
    private ImageView im_recruiter_avator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        initView();
    }
    private void initView(){
        getSupportActionBar().setTitle("企业信息");
        Bmob.initialize(this, Info.getAppid());
        recruiter_company = (TextView)findViewById(R.id.recruiter_company);
        recruiter_phone = (TextView) findViewById(R.id.recruiter_phone);
        recruiter_email = (TextView) findViewById(R.id.recruiter_email);
        recruiter_introduction = (TextView) findViewById(R.id.recruiter_introduction);
        recruiter_address = (TextView) findViewById(R.id.recruiter_address);
        bt_modify_company_info = (Button) findViewById(R.id.bt_modify_company_info);
        im_recruiter_avator = (ImageView) findViewById(R.id.im_recruiter_avator);

        showInfo();
        bt_modify_company_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyInfoActivity.this,ModifyRecruiterInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void showInfo(){
        recruiter_company.setText(Info.getRcompany());
        recruiter_address.setText(Info.getRaddress());
        recruiter_email.setText(Info.getRemail());
        recruiter_phone.setText(Info.getRphone());
        recruiter_introduction.setText(Info.getRprofile());
        Picasso.with(CompanyInfoActivity.this).load(Info.getRavatar()).error(R.drawable.ic_default_avator).into(im_recruiter_avator);

    }

}
