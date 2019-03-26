package com.easyjob.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class JobInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_j_title, tv_j_salary, tv_j_address, tv_j_requirement, tv_j_details, tv_j_time;
    private Button bt_delete_job, bt_modify_job,bt_appyer;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_info);
        initview();
        showjob();
    }


    private void initview() {
        Bmob.initialize(this, Info.appid);
        getSupportActionBar().setTitle("兼职信息");
        tv_j_title = (TextView) findViewById(R.id.tv_j_title);
        tv_j_address = (TextView) findViewById(R.id.tv_j_address);
        tv_j_details = (TextView) findViewById(R.id.tv_j_details);
        tv_j_salary = (TextView) findViewById(R.id.tv_j_salary);
        tv_j_requirement = (TextView) findViewById(R.id.tv_j_requirement);
        tv_j_time = (TextView) findViewById(R.id.tv_j_time);
        bt_delete_job = (Button) findViewById(R.id.bt_delete_job);
        bt_modify_job = (Button) findViewById(R.id.bt_modify_job);
        bt_appyer = findViewById(R.id.bt_appyer);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        bt_modify_job.setOnClickListener(this);
        bt_delete_job.setOnClickListener(this);
        bt_appyer.setOnClickListener(this);
    }

    private void showjob(){
        BmobQuery<ParttimeJob> query = new BmobQuery<ParttimeJob>();
        query.getObject(id, new QueryListener<ParttimeJob>() {
            @Override
            public void done(ParttimeJob parttimeJob, BmobException e) {
                if(e==null){
                    Info.jtitle=parttimeJob.getJ_title();
                    Info.jaddress=parttimeJob.getJ_address();
                    Info.jdetails=parttimeJob.getJ_details();
                    Info.jrequirement=parttimeJob.getJ_requirement();
                    Info.jsalayr=parttimeJob.getJ_salary();
                    Info.jtime=parttimeJob.getJ_time();
                    tv_j_address.setText(Info.jaddress);
                    tv_j_details.setText(Info.jdetails);
                    tv_j_title.setText(Info.jtitle);
                    tv_j_requirement.setText(Info.jrequirement);
                    tv_j_salary.setText(Info.jsalayr);
                    tv_j_time.setText(Info.jtime);
                }
                else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_delete_job:
                showNormalDialog();
                break;
            case R.id.bt_modify_job:
                Intent intent = new Intent(JobInfoActivity.this,ModifyJobActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;

            case R.id.bt_appyer:
                Intent intent1 = new Intent(JobInfoActivity.this,AppyerActivity.class);
                intent1.putExtra("jid",id);
                startActivity(intent1);
                break;


        }
    }
    private void showNormalDialog(){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(JobInfoActivity.this);
        normalDialog.setIcon(R.drawable.ic_delete);
        normalDialog.setTitle("确认删除吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobQuery<Enroll_Info> e = new BmobQuery<>();
                        e.addWhereEqualTo("parttimejob",id);
                        e.findObjects(new FindListener<Enroll_Info>() {
                            @Override
                            public void done(List<Enroll_Info> list, BmobException e) {
                            if(e==null){

                                Enroll_Info enroll_info = new Enroll_Info();
                                enroll_info.setObjectId(list.get(0).getObjectId());
                                enroll_info.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e!=null){
                                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                        }
                                    }
                                });

                            }
                            }
                        });


                        ParttimeJob parttimeJob = new ParttimeJob();
                        parttimeJob.setObjectId(id);
                        parttimeJob.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(JobInfoActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                            finish();
                        }
                    });
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            // 显示
            normalDialog.show();
    }
}
