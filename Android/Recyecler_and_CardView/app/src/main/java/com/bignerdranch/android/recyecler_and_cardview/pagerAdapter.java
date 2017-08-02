package com.bignerdranch.android.recyecler_and_cardview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seungwoo on 2017-07-27.
 */

public class pagerAdapter extends FragmentPagerAdapter {
    Map<Integer, Fragment> mPageReferenceMap = new HashMap<>();

    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment = store_fragment.newInstance(position);
        mPageReferenceMap.put(position, fragment);
        return fragment;
    }

    public Fragment getFragment(int position) {
        return mPageReferenceMap.get(position);
    }
    @Override
    public int getCount() {
        // total page count
        return 3;
    }

}
