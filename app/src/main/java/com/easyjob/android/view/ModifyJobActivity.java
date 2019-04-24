package com.easyjob.android.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easyjob.android.R;
import com.easyjob.android.bean.Info;
import com.easyjob.android.bean.ParttimeJob;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyJobActivity extends AppCompatActivity {
    private EditText et_j_title,et_j_salary,
            et_j_address,et_j_requirement,et_j_details,et_j_time;
    private Button bt_j_save;
    private String title,salary,address,requirement,details,time;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_job);
        initView();
    }

    private void initView(){
        Bmob.initialize(this, Info.getAppid());
        getSupportActionBar().setTitle("   修改兼职");
        et_j_title = (EditText) findViewById(R.id.et_j_title);
        et_j_salary = (EditText) findViewById(R.id.et_j_salary);
        et_j_address = (EditText) findViewById(R.id.et_j_address);
        et_j_requirement = (EditText) findViewById(R.id.et_job_requirement);
        et_j_details = (EditText) findViewById(R.id.et_j_details);
        et_j_time = (EditText) findViewById(R.id.et_j_time);
        bt_j_save = (Button) findViewById(R.id.bt_j_save);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        showjob();
        bt_j_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtaintext();
                if(!judgetextnotnull(title,salary,address,requirement,details,time)){
                    return;

                }
                savejob();
                finish();
            }
        });
    }

    private void showjob(){
        et_j_address.setText(Info.getJaddress());
        et_j_details.setText(Info.getJdetails());
        et_j_requirement.setText(Info.getJrequirement());
        et_j_salary.setText(Info.getJsalayr());
        et_j_title.setText(Info.getJtitle());
        et_j_time.setText(Info.getJtime());
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
            String pattern = "^\\d{1,2}\\.\\d{1,2}\\-\\d{1,2}\\.\\d{1,2}$";
            if(!time.matches(pattern)){
                Toast.makeText(this,"请输入正确日期", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }

    private void savejob(){
        ParttimeJob parttimeJob = new ParttimeJob();
        parttimeJob.setJ_address(address);
        parttimeJob.setJ_details(details);
        parttimeJob.setJ_requirement(requirement);
        parttimeJob.setJ_salary(salary);
        parttimeJob.setJ_time(time);
        parttimeJob.setJ_title(title);
        parttimeJob.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(ModifyJobActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
