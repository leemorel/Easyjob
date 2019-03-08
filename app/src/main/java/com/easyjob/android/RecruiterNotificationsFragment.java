package com.easyjob.android;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecruiterNotificationsFragment extends Fragment implements View.OnClickListener{


    private View mView;
    private RelativeLayout rl_system_notification;



    public RecruiterNotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_recruiter_notifications, container, false);
        rl_system_notification=(RelativeLayout) mView.findViewById(R.id.rl_system_notification);
        rl_system_notification.setOnClickListener(this);


        return mView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_system_notification:
                Intent intent = new Intent(getActivity(), SystemNotificationsActivity.class);
                startActivity(intent);
        }
    }
}
