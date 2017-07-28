package com.bignerdranch.android.recyecler_and_cardview;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by seungwoo on 2017-07-27.
 */

public class Store {

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    private UUID mId;

    private Drawable store_image;
    private String store_title;
    private String store_detail;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    private int distance;
    private int popularity;
    private int update_time;

    public Drawable getStore_image() {
        return store_image;
    }

    public void setStore_image(Drawable store_image) {
        this.store_image = store_image;
    }

    public String getStore_title() {
        return store_title;
    }

    public void setStore_title(String store_title) {
        this.store_title = store_title;
    }

    public String getStore_detail() {
        return store_detail;
    }

    public void setStore_detail(String store_detail) {
        this.store_detail = store_detail;
    }
    public Store() {
        mId = UUID.randomUUID();
    }
}
