package com.easyjob.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddjobActivity extends AppCompatActivity {
    private EditText et_j_title,et_j_salary,
            et_j_address,et_j_requirement,et_j_details,et_j_time;
    private Button bt_add;
    private String title,salary,address,requirement,details,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addjob);
        initView();
    }

    private void initView(){
        Bmob.initialize(this,Info.appid);
        getSupportActionBar().setTitle("   发布兼职");
        et_j_title = (EditText) findViewById(R.id.et_j_title);
        et_j_salary = (EditText) findViewById(R.id.et_j_salary);
        et_j_address = (EditText) findViewById(R.id.et_j_address);
        et_j_requirement = (EditText) findViewById(R.id.et_job_requirement);
        et_j_details = (EditText) findViewById(R.id.et_j_details);
        et_j_time = (EditText) findViewById(R.id.et_j_time);
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtaintext();
                if(!judgetextnotnull(title,salary,address,requirement,details,time)){
                    return;
                }
                addjob();
            }
        });
    }


    private void obtaintext(){
        title = et_j_title.getText().toString();
        salary = et_j_salary.getText().toString();
        address = et_j_address.getText().toString();
        requirement = et_j_requirement.getText().toString();
        details = et_j_details.getText().toString();
        time = et_j_time.getText().toString();
    }

    private boolean judgetextnotnull(String title,String salary,String address,String requirement,String details,String time){
        if(address.isEmpty()||title.isEmpty()||salary.isEmpty()||address.isEmpty()||requirement.isEmpty()||details.isEmpty()||time.isEmpty()){
            Toast.makeText(this,"请把信息输入完整", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    private void addjob(){
        Recruiter_Info company = new Recruiter_Info();
        company.setObjectId(Info.recruiter_id);
        ParttimeJob j = new ParttimeJob();
        j.setJ_address(address);
        j.setJ_details(details);
        j.setJ_requirement(requirement);
        j.setJ_salary(salary+"/天");
        j.setJ_time(time);
        j.setJ_title(title);
        j.setCompany(company);
        j.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(AddjobActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }
        });
    }
}
