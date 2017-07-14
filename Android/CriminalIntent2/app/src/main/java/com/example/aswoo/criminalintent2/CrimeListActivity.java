package com.example.aswoo.criminalintent2;

import android.support.v4.app.Fragment;

/**
 * Created by aswoo on 2017-07-14.
 */

public class CrimeListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
