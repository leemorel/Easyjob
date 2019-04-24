package com.easyjob.android.view;

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
import com.easyjob.android.bean.Recruiter_Info;

import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddjobActivity extends AppCompatActivity {
    private EditText et_j_title,et_j_salary,
            et_j_address,et_j_requirement,et_j_details,et_j_time;
    private Button bt_add;
    private String title,salary,address,requirement,details,time;
    private static String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addjob);
        initView();
    }

    private void initView(){
        Bmob.initialize(this, Info.getAppid());
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
                    return ;
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

        int flag = address.indexOf("省");
        switch (flag){
            case -1:
                String[] parts2 = address.split("市");
                city = parts2[0];
                break;

            default:
                String[] parts = address.split("省");
                String part2 = parts[1]; // 034556
                String[] parts1 = part2.split("市");
                city = parts1[0];
                break;
        }

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


    private void addjob(){
        Recruiter_Info company = new Recruiter_Info();
        company.setObjectId(Info.getRecruiter_id());
        ParttimeJob j = new ParttimeJob();
        j.setJ_address(address);
        j.setJ_details(details);
        j.setJ_requirement(requirement);
        j.setJ_salary(salary+"/天");
        j.setJ_time(time);
        j.setJ_title(title);
        j.setJ_company(Info.getRcompany());
        j.setCompany(company);
        j.setJ_city(city);
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
