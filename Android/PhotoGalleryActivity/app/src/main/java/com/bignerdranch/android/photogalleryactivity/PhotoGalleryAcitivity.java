package com.bignerdranch.android.photogalleryactivity;

import android.support.v4.app.Fragment;

/**
 * Created by seungwoo on 2017-07-26.
 */

public class PhotoGalleryAcitivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}
