package com.easyjob.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class RecruiterActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    RecruiterHomeFragment homeFragment = new RecruiterHomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                    return true;
                case R.id.navigation_notifications:
                    RecruiterNotificationsFragment notificationsFragment = new RecruiterNotificationsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationsFragment).commit();
                    return true;
                case R.id.navigation_personals:
                    RecruiterPersonalsFragment personalsFragment = new RecruiterPersonalsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,personalsFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}