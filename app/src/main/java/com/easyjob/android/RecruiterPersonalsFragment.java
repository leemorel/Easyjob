package com.easyjob.android;

import android.content.Context;
import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class RecruiterPersonalsFragment extends Fragment implements View.OnClickListener {


    public RecruiterPersonalsFragment() {
        // Required empty public constructor
    }

    private View mView;
    private RelativeLayout personal_company_info, telephone, about;
    private Button bt_logout;
    private ImageView im_recruiter_avator;
    private TextView tv_recruiter_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recruiter_personals, container, false);
        personal_company_info = (RelativeLayout) mView.findViewById(R.id.personal_company_info);
        telephone = (RelativeLayout) mView.findViewById(R.id.telephone);
        about=(RelativeLayout) mView.findViewById(R.id.about);
        bt_logout = (Button) mView.findViewById(R.id.bt_logout);
        about.setOnClickListener(this);
        telephone.setOnClickListener(this);
        personal_company_info.setOnClickListener(this);
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

        }

    }
}
