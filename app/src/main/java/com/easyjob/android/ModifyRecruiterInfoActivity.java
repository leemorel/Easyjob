package com.easyjob.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.net.CookieManager;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyRecruiterInfoActivity extends AppCompatActivity {
    private EditText et_recruiter_company,et_recruiter_phone,et_recruiter_email,et_recruiter_introduction,et_recruiter_address;
    private ImageButton im_recruiter_avator;
    private Button bt_save_company_info;
    private String address,phone,company,email,profile;
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
        showInfo();
        bt_save_company_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtaintext();
                if(!judgetextnotnull(address,company,email,profile,phone)){
                    return;
                }

                add_cruiter_info(); // 添加信息到数据库
                saveLocal();    //保存到本地
                Intent intent = new Intent(ModifyRecruiterInfoActivity.this, CompanyInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void showInfo() {


        et_recruiter_company.setText(Info.rcompany);
        et_recruiter_address.setText(Info.raddress);
        et_recruiter_email.setText(Info.remail);
        et_recruiter_phone.setText(Info.rphone);
        et_recruiter_introduction.setText(Info.rprofile);
    }

    private void obtaintext(){
        address=et_recruiter_address.getText().toString();
        company=et_recruiter_company.getText().toString();
        email=et_recruiter_email.getText().toString();
        profile=et_recruiter_introduction.getText().toString();
        phone=et_recruiter_phone.getText().toString();
    }
    private boolean judgetextnotnull(String address,String company,String email,String introduction,String phone){
        if(address.isEmpty()||company.isEmpty()||email.isEmpty()||introduction.isEmpty()||phone.isEmpty()){
            Toast.makeText(this,"请把信息输入齐整", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    private void add_cruiter_info(){
        Recruiter_Info r = new Recruiter_Info();
        r.setRecruiter_address(address);
        r.setRecruiter_company(company);
        r.setRecruiter_email(email);
        r.setRecruiter_profile(profile);
        r.setRecruiter_phone(phone);
        r.update(Info.recruiter_id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(ModifyRecruiterInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    private void saveLocal(){
        Info.raddress=et_recruiter_address.getText().toString();
        Info.rcompany=et_recruiter_company.getText().toString();
        Info.remail=et_recruiter_email.getText().toString();
        Info.rprofile=et_recruiter_introduction.getText().toString();
        Info.rphone=et_recruiter_phone.getText().toString();
    }


}
