package com.easyjob.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ChooseActivity extends AppCompatActivity {
    private Button bt_recruiter;
    private Button bt_part_timer;
    public String id;   //重要

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this,"6f6a477b385f8b96ec36e900a2a5d184");
        setContentView(R.layout.activity_choose);
        bt_recruiter = (Button) findViewById(R.id.bt_recruiter);
        bt_part_timer = (Button) findViewById(R.id.bt_part_timer);
        bt_recruiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                uploadphone(phone); }
                                else{
                                for(Recruiter_Info recruiter_info:object){
                                    id=recruiter_info.getObjectId();
                                    Toast.makeText(getApplicationContext(), "你想提示的信息"+id,Toast.LENGTH_LONG).show();
                                }

                            }
                        }else{
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });

                Intent intent = new Intent(ChooseActivity.this, RecruiterActivity.class);
                startActivity(intent);
            }
        });
        bt_part_timer.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent intent = new Intent(ChooseActivity.this, RecruiterActivity.class);
                startActivity(intent);
            }
        });

    }
    private void uploadphone(String phone){
        Recruiter_Info r = new Recruiter_Info();
        r.setRecruiter_phone(phone);
        r.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e!=null){
                    Log.i("bmob","数据库错误："+e.getMessage()+","+e.getErrorCode());
                }
                else {
                    Recruiter_Info recruiterid =new Recruiter_Info();
                    id=recruiterid.getObjectId();
                    Toast.makeText(getApplicationContext(), "你想提示的信息"+s,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
