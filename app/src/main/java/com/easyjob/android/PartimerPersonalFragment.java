package com.easyjob.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


public class PartimerPersonalFragment extends Fragment implements View.OnClickListener{
    private View mView;
    private RelativeLayout personal_partimer_info, telephone, about,rl_state;
    private ImageView iv_partimer_avatar;
    private TextView tv_partimer_name;
    private Button bt_logout;



    public PartimerPersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.with(getContext()).load(Info.p_avatar).fit().into(iv_partimer_avatar);
            }
        }, 800);
        display();           //显示头像，简历
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bmob.initialize(getActivity(),Info.appid);
        mView = inflater.inflate(R.layout.fragment_partimer_personal, container, false);
        initView();
        return mView;
    }

    private void initView(){
        personal_partimer_info = (RelativeLayout) mView.findViewById(R.id.personal_partimer_info);
        telephone = (RelativeLayout) mView.findViewById(R.id.telephone);
        about=(RelativeLayout) mView.findViewById(R.id.about);
        rl_state=(RelativeLayout)mView.findViewById(R.id.rl_state);
        bt_logout = (Button) mView.findViewById(R.id.bt_logout);
        about.setOnClickListener(this);
        rl_state.setOnClickListener(this);
        telephone.setOnClickListener(this);
        bt_logout.setOnClickListener(this);
        personal_partimer_info.setOnClickListener(this);
        iv_partimer_avatar = (ImageView) mView.findViewById(R.id.iv_partimer_avatar);
        tv_partimer_name = (TextView) mView.findViewById(R.id.tv_partimer_name);
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
            case R.id.personal_partimer_info:
                Intent intent2 = new Intent(getContext(),PartimerInfoActivity.class);
                startActivity(intent2);
                break;
            case R.id.bt_logout:
                Intent intent3 = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent3);
                getActivity().finish();
                break;
            case R.id.rl_state:
                Intent intent4 = new Intent(getContext(),MyJobActivity.class);
                startActivity(intent4);
        }
    }
    private void display(){
        Picasso.with(getContext()).load(Info.p_avatar).into(iv_partimer_avatar);
        BmobQuery<Partimer_Info>  query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",Info.p_id);
        query.getObject(Info.p_id, new QueryListener<Partimer_Info>() {
            @Override
            public void done(Partimer_Info partimer_info, BmobException e) {
            if(e == null){
            tv_partimer_name.setText(partimer_info.getP_name());
            Info.p_name = partimer_info.getP_name();
            Info.p_phone = partimer_info.getP_phone();
            Info.p_age = partimer_info.getP_age();
            Info.p_email = partimer_info.getP_email();
            Info.p_education = partimer_info.getP_education();
            Info.p_height = partimer_info.getP_height();
            Info.p_sex = partimer_info.getP_sex();
            Info.p_introduction = partimer_info.getP_introduction();
            Info.p_avatar = partimer_info.getP_avatar().getFileUrl();
            }else{
                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
            }
            }
        });
    }



}
