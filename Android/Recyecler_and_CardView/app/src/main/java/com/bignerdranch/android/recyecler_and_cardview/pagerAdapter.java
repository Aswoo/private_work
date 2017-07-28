package com.bignerdranch.android.recyecler_and_cardview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by seungwoo on 2017-07-27.
 */

public class pagerAdapter extends FragmentStatePagerAdapter {
    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       return store_fragment.newInstance(position);
    }


    @Override
    public int getCount() {
        // total page count
        return 3;
    }

}
