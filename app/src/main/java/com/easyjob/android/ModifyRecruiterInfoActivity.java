package com.easyjob.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import cn.bmob.v3.Bmob;

public class ModifyRecruiterInfoActivity extends AppCompatActivity {
    private EditText et_recruiter_company,et_recruiter_phone,et_recruiter_email,et_recruiter_introduction,et_recruiter_address;
    private ImageButton im_recruiter_avator;
    private Button bt_save_company_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_recruiter_info);
        initView();
    }
    private void initView(){
        Bmob.initialize(this,"6f6a477b385f8b96ec36e900a2a5d184");
        getSupportActionBar().setTitle("修改信息");
        et_recruiter_address = (EditText) findViewById(R.id.et_recruiter_address);
        et_recruiter_company = (EditText) findViewById(R.id.et_recruiter_company);
        et_recruiter_email = (EditText) findViewById(R.id.et_recruiter_email);
        et_recruiter_introduction = (EditText) findViewById(R.id.et_recruiter_introduction);
        et_recruiter_phone = (EditText) findViewById(R.id.et_recruiter_phone);
        im_recruiter_avator = (ImageButton) findViewById(R.id.im_recruiter_avator);
        bt_save_company_info = (Button) findViewById(R.id.bt_save_company_info);
        bt_save_company_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
