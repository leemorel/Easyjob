package com.easyjob.android.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easyjob.android.R;
import com.easyjob.android.bean.Complaint_Info;
import com.easyjob.android.bean.Info;
import com.easyjob.android.bean.Partimer_Info;
import com.easyjob.android.bean.Recruiter_Info;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class CompanyInfoViewActivity extends AppCompatActivity {
    private TextView recruiter_company, recruiter_phone, recruiter_email, recruiter_introduction, recruiter_address;
    private Button bt_modify_company_info;
    private ImageView im_recruiter_avator;
    private RelativeLayout rl_complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info_view);
        initView();
        showInfo();
    }

    private void initView() {
        getSupportActionBar().setTitle("企业信息");
        Bmob.initialize(this, Info.getAppid());
        recruiter_company = (TextView) findViewById(R.id.recruiter_company);
        recruiter_phone = (TextView) findViewById(R.id.recruiter_phone);
        recruiter_email = (TextView) findViewById(R.id.recruiter_email);
        recruiter_introduction = (TextView) findViewById(R.id.recruiter_introduction);
        recruiter_address = (TextView) findViewById(R.id.recruiter_address);
        im_recruiter_avator = (ImageView) findViewById(R.id.im_recruiter_avator);
        rl_complaint = (RelativeLayout) findViewById(R.id.rl_complaint);
        rl_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

    }
    private void showInputDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(CompanyInfoViewActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(CompanyInfoViewActivity.this);
        inputDialog.setIcon(R.drawable.ic_complaint);
        inputDialog.setTitle("投诉详情信息").setView(editText);
        inputDialog.setMessage("点击下方输入");
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                                Recruiter_Info recruiter_info = new Recruiter_Info();
                                recruiter_info.setObjectId(Info.getRecruiter_id());
                                Partimer_Info partimer_info = new Partimer_Info();
                                partimer_info.setObjectId(Info.getP_id());
                                Complaint_Info complaint_info = new Complaint_Info();
                                complaint_info.setComplaint(editText.getText().toString());
                                complaint_info.setPartimer(partimer_info);
                                complaint_info.setRecruiter(recruiter_info);
                                complaint_info.setCompany(Info.getRcompany());
                                complaint_info.setComplainter(Info.getP_name());
                                complaint_info.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if(e==null){
                                            Toast.makeText(CompanyInfoViewActivity.this,"投诉成功",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Log.i("bmob","失败："+e.getMessage());
                                        }
                                    }
                                });


                    }
                }).show();
    }
    private void showInfo() {
        recruiter_company.setText(Info.getRcompany());
        recruiter_address.setText(Info.getRaddress());
        recruiter_email.setText(Info.getRemail());
        recruiter_phone.setText(Info.getRphone());
        recruiter_introduction.setText(Info.getRprofile());
        Picasso.with(CompanyInfoViewActivity.this).load(Info.getRavatar()).error(R.drawable.ic_default_avator).into(im_recruiter_avator);

    }
}
