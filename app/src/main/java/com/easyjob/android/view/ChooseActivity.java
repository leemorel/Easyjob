package com.easyjob.android.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.easyjob.android.R;
import com.easyjob.android.bean.Info;
import com.easyjob.android.bean.Partimer_Info;
import com.easyjob.android.bean.Recruiter_Info;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ChooseActivity extends AppCompatActivity {
    private Button bt_recruiter;
    private Button bt_part_timer;
    private String id,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, Info.getAppid());
        setContentView(R.layout.activity_choose);
        bt_recruiter = (Button) findViewById(R.id.bt_recruiter);
        bt_part_timer = (Button) findViewById(R.id.bt_part_timer);
        bt_recruiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verification(); //验证用户是否已存在

            }
        });
        bt_part_timer.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                verificationPartimer(); //验证用户是否已存在
//            Intent intent = new Intent(ChooseActivity.this,ModifyPartimerInfo1Activity.class);
//            startActivity(intent);
//            finish();
            }
        });

    }



    //验证用户是否存在
    private void verification(){
        Intent intent1 =getIntent();
        String phone = intent1.getStringExtra("phone");//////////////
        BmobQuery<Recruiter_Info> query = new BmobQuery<>();
        query.addWhereEqualTo("recruiter_phone", phone);
        query.findObjects(new FindListener<Recruiter_Info>() {
            @Override
            public void done(List<Recruiter_Info> object, BmobException e) {
                if(e==null){
                    if(object.size()==0){
                        Intent intent1 =getIntent();
                        String phone = intent1.getStringExtra("phone");////////////////
                        uploadphone();         //不存在则创建新用户

                    }
                    else{
                        for(Recruiter_Info recruiter_info:object){
                            id=recruiter_info.getObjectId();
                            Info.setRecruiter_id(id);
                            Info.setRcompany(recruiter_info.getRecruiter_company());
                        }
                        Intent intent = new Intent(ChooseActivity.this, RecruiterActivity.class);
                        intent.putExtra("id",id);
                        Info.setRecruiter_id(id);
                        startActivity(intent);

                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }


    private void verificationPartimer(){
        Intent intent1 =getIntent();
        phone = intent1.getStringExtra("phone");//////////////
        BmobQuery<Partimer_Info> query = new BmobQuery<>();
        query.addWhereEqualTo("p_phone",phone);
        query.findObjects(new FindListener<Partimer_Info>() {
            @Override
            public void done(List<Partimer_Info> list, BmobException e) {
                if(e==null){
                    if(list.size()==0){
                        uploadphonePartimer();         //不存在则创建新用户

                    }
                    else{
                        for(Partimer_Info partimer_info:list){
                            Info.setP_id(partimer_info.getObjectId());
                            Info.setP_name(partimer_info.getP_name());
                        }
                        Intent intent = new Intent(ChooseActivity.this, PartimerActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);

                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void uploadphone(){
        Recruiter_Info r = new Recruiter_Info();
//        r.setRecruiter_phone(phone);
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e!=null){
                    Log.i("bmob","数据库错误："+e.getMessage()+","+e.getErrorCode());
                }
                else {
                    Intent intent1 =getIntent();
                    String phone = intent1.getStringExtra("phone");
                    id=s;                     /////objectid
                    Info.setRecruiter_id(id);
                    Intent intent2 = new Intent(ChooseActivity.this,ModifyRecruiterInfo1Activity.class);  //TODO 111
                    intent2.putExtra("id",id);
                    intent2.putExtra("phone",phone);
                    startActivity(intent2);
                }
            }
        });
    }

    private void uploadphonePartimer(){
        Partimer_Info p = new Partimer_Info();
//        p.setP_phone(phone);
        p.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e!=null){
                    Log.i("bmob","数据库错误："+e.getMessage()+","+e.getErrorCode());
                }
                else {
                    Intent intent1 =getIntent();
                    Info.setP_phone(intent1.getStringExtra("phone"));
                    Info.setP_id(s);
                    Intent intent2 = new Intent(ChooseActivity.this,ModifyPartimerInfo1Activity.class);  //TODO 111
                    startActivity(intent2);
                }
            }
        });
    }
}
