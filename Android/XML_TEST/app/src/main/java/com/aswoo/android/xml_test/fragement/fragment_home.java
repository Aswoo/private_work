package com.aswoo.android.xml_test.fragement;

/**
 * Created by seungwoo on 2017-09-25.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aswoo.android.xml_test.R;

public class fragment_home extends Fragment {


    public static fragment_home newInstance(){
        fragment_home fragment_home = new fragment_home();
        return fragment_home;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }
}