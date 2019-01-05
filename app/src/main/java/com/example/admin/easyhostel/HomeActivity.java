package com.example.admin.easyhostel;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {
Toolbar toolbar;
TabLayout tabLayout;
ViewPager viewPager;
ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tabLayout=(TabLayout)(findViewById(R.id.tapLAyout));
        viewPager=(ViewPager)(findViewById(R.id.viewPager));
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Easy Hostel");
        viewPagerAdapter = new ViewPagerAdapter(HomeActivity.this, getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabDesign();
    }

    private void setupTabDesign() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_profile_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_booking_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_notifications_active_black_24dp);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
    }


}
