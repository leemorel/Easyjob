package com.easyjob.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.bmob.v3.Bmob;

public class CompanyInfoActivity extends AppCompatActivity {
    private TextView recruiter_company,recruiter_phone,recruiter_email,recruiter_introduction,recruiter_address;
    private Button bt_modify_company_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        initView();
    }
    private void initView(){
        getSupportActionBar().setTitle("企业信息");
        Bmob.initialize(this, "6f6a477b385f8b96ec36e900a2a5d184");
        recruiter_company = (TextView)findViewById(R.id.recruiter_company);
        recruiter_phone = (TextView) findViewById(R.id.recruiter_phone);
        recruiter_email = (TextView) findViewById(R.id.recruiter_email);
        recruiter_introduction = (TextView) findViewById(R.id.recruiter_introduction);
        recruiter_address = (TextView) findViewById(R.id.recruiter_address);
        bt_modify_company_info = (Button) findViewById(R.id.bt_modify_company_info);
        bt_modify_company_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyInfoActivity.this,ModifyRecruiterInfoActivity.class);
                startActivity(intent);
            }
        });
    }

}
