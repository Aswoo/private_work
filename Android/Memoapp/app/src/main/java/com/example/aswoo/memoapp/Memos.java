package com.example.aswoo.memoapp;

import java.util.Date;

/**
 * Created by aswoo on 2017-07-01.
 */

public class Memos {


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    private String txt;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    private Date createDate;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    private Date updateDate;

    public String getTitle() {
        if(txt != null){
            if(txt.indexOf("\n") > -1)
            {
                return txt.substring(0,txt.indexOf("\n"));
            }
            else {
                return txt;
            }
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
}
