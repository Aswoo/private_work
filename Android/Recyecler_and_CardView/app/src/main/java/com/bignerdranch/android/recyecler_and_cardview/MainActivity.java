package com.bignerdranch.android.recyecler_and_cardview;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    final private static int LISTSTATIE = 0;
    final private static int GRIDSTATE = 1;
    public int page_state = 0;
    @Override
    protected void onResume() {
        super.onResume();
    }

    private pagerAdapter mPageAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ImageButton change_btn;

    private int mSelectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTablayout();

        setupToolbar();

        setupViewPager();

    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        // Creating TabPagerAdapter adapter
        mPageAdapter = new pagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPageAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void setupTablayout() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mSelectedPosition = tab.getPosition();
                Log.i("onTabSelected  " ,String.valueOf(tab.getPosition()));
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab ta) {

            }
        });

        change_btn = (ImageButton) findViewById(R.id.change_button);
        change_btn.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                page_state = 1 - page_state;

                if(page_state == GRIDSTATE){
                    store_fragment storeFragment = (store_fragment) mPageAdapter.getFragment(mSelectedPosition);
                    storeFragment.setRecyclerViewLayoutManager(GRIDSTATE);
                }
                if(page_state == LISTSTATIE){
                    store_fragment storeFragment = (store_fragment) mPageAdapter.getFragment(mSelectedPosition);
                    storeFragment.setRecyclerViewLayoutManager(LISTSTATIE);
                }

            }
        }
        );
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
