package com.example.seungwoo.view_pager_fragment;

import android.graphics.drawable.Drawable;

import java.util.UUID;

/**
 * Created by seungwoo on 2017-07-17.
 */

public class Page {

    public Drawable getPage_icon() {
        return page_icon;
    }

    public void setPage_icon(Drawable page_icon) {
        this.page_icon = page_icon;
    }
    public String gettitle() {
        return mtitle;
    }

    public void settitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String gettitle_detail() {
        return mtitle_detail;
    }

    public void settitle_detail(String mtitle_detail) {
        this.mtitle_detail = mtitle_detail;
    }

    public String getBody_text() {
        return Body_text;
    }

    public void setBody_text(String body_text) {
        Body_text = body_text;
    }

    public Drawable getBody_image() {
        return Body_image;
    }

    public void setBody_image(Drawable body_image) {
        Body_image = body_image;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    private UUID mId;
    private Drawable page_icon;
    private String mtitle;
    private String mtitle_detail;
    //private Image good_icon;
    private String Body_text;
    private Drawable Body_image;

    public Page() {
        mId = UUID.randomUUID();
    }
}
