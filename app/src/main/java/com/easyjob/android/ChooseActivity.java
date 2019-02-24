package com.easyjob.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {
    private Button bt_recruiter;
    private Button bt_part_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        bt_recruiter = (Button) findViewById(R.id.bt_recruiter);
        bt_part_timer = (Button) findViewById(R.id.bt_part_timer);
        bt_recruiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}
