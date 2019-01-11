package com.easyjob.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity implements View.OnClickListener{
//    private Button bt_recruiter;
//    private Button bt_part_timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
//        bt_recruiter=(Button) findViewById(R.id.bt_recruiter);
//        bt_part_timer=(Button) findViewById(R.id.bt_part_timer);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.bt_recruiter:
            break;
        case R.id.bt_part_timer:
            break;

    }
    }
}
