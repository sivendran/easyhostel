package com.example.admin.easyhostel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Admin on 9/28/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context ctx;

    public ViewPagerAdapter(Context ctx, FragmentManager fm){
        super(fm);
        this.ctx = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if(position == 0){
            fragment = new StudentProfile();
        }
        if(position == 1){
            fragment = new Notifications();
        }
        if(position == 2){
            fragment = new Booking();
        }

        return fragment;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

}
