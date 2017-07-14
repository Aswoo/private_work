package com.example.aswoo.criminalintent2;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by aswoo on 2017-07-14.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    //안드로이드 작명 규칙스태틱 변수는 s!

    private List<Crime> mCrimes;

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public static CrimeLab get(Context context){
        if(sCrimeLab == null)
        {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for(int i = 0;i<100;i++){
            Crime crime = new Crime();
            crime.setTitle("범죄 #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public Crime getCrime(UUID id){
        for(Crime crime : mCrimes) {
            if(crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
