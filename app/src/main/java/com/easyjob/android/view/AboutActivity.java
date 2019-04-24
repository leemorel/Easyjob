package com.easyjob.android.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.easyjob.android.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加箭头
        getSupportActionBar().setTitle("关于我们");

    }
    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return super.onSupportNavigateUp();
    }
}
