package com.easyjob.android.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.easyjob.android.R;
import com.easyjob.android.view.fragment.PartimerHomeFragment;
import com.easyjob.android.view.fragment.PartimerPersonalFragment;
import com.easyjob.android.view.fragment.RecruiterNotificationsFragment;

public class PartimerActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    PartimerHomeFragment partimerHomeFragment = new PartimerHomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,partimerHomeFragment).commit();
                    return true;
                case R.id.navigation_notifications:
                    RecruiterNotificationsFragment notificationsFragment = new RecruiterNotificationsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationsFragment).commit();
//                    PartimerNotificationsFragment partimerNotificationsFragment = new PartimerNotificationsFragment();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container,partimerNotificationsFragment).commit();
                    return true;
                case R.id.navigation_personals:
                    PartimerPersonalFragment partimerPersonalFragment = new PartimerPersonalFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,partimerPersonalFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partimer);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        PartimerHomeFragment partimerHomeFragment = new PartimerHomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,partimerHomeFragment).commit();

    }

}
