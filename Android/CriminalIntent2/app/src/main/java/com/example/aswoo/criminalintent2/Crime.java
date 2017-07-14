package com.example.aswoo.criminalintent2;

import java.util.Date;
import java.util.UUID;

/**
 * Created by aswoo on 2017-07-14.
 */

public class Crime {


    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }



    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public UUID getId(){
        return mId;
    }

    public Crime() {
        //고유한 식별자를 생성한다.
        mId = UUID.randomUUID();

    }
}
