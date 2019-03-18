package com.easyjob.android;

import android.content.Context;
import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;


public class RecruiterPersonalsFragment extends Fragment implements View.OnClickListener {



    public RecruiterPersonalsFragment() {

        // Required empty public constructor
    }

    private View mView;
    private RelativeLayout personal_company_info, telephone, about;
    private Button bt_logout;
    private ImageView iv_recruiter_avator;
    private TextView tv_recruiter_name;

    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            Picasso.with(getContext()).load(Info.ravatar).fit().into(iv_recruiter_avator);
            }
        }, 1500);
        display();           //显示头像，公司
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bmob.initialize(getActivity(),Info.appid);
        mView = inflater.inflate(R.layout.fragment_recruiter_personals, container, false);
        personal_company_info = (RelativeLayout) mView.findViewById(R.id.personal_company_info);
        telephone = (RelativeLayout) mView.findViewById(R.id.telephone);
        about=(RelativeLayout) mView.findViewById(R.id.about);
        bt_logout = (Button) mView.findViewById(R.id.bt_logout);
        about.setOnClickListener(this);
        telephone.setOnClickListener(this);
        bt_logout.setOnClickListener(this);
        personal_company_info.setOnClickListener(this);
        iv_recruiter_avator = (ImageView) mView.findViewById(R.id.iv_recruiter_avator);
        tv_recruiter_name = (TextView) mView.findViewById(R.id.tv_recruiter_name);
//        display();           //显示头像，公司
//        Picasso.with(getContext()).load(Info.ravatar).into(iv_recruiter_avator);
        return mView;


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                Intent intent=new Intent(getActivity(),AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.telephone:
                Intent intent1 = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+1234567890));
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
            case R.id.personal_company_info:
                Intent intent2 = new Intent(getActivity(),CompanyInfoActivity.class);
                startActivity(intent2);
                break;
            case R.id.bt_logout:
                Intent intent3 = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent3);
                getActivity().finish();
                break;


        }
    }


    private void display(){
        Picasso.with(getContext()).load(Info.ravatar).into(iv_recruiter_avator);
        BmobQuery<Recruiter_Info> query = new BmobQuery<Recruiter_Info>();
        query.addWhereEqualTo("objectId",Info.appid);
        query.getObject(Info.recruiter_id, new QueryListener<Recruiter_Info>() {
            public void done(Recruiter_Info recruiter_info, BmobException e) {
                if(e==null){
                    tv_recruiter_name.setText( recruiter_info.getRecruiter_company());
                    Info.ravatar = recruiter_info.getRecruiter_avatar().getFileUrl();
                    Info.rcompany=recruiter_info.getRecruiter_company();
                    Info.raddress=recruiter_info.getRecruiter_address();
                    Info.remail=recruiter_info.getRecruiter_email();
                    Info.rphone=recruiter_info.getRecruiter_phone();
                    Info.rprofile=recruiter_info.getRecruiter_profile();

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }



}

