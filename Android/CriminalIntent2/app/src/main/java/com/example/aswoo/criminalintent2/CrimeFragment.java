package com.example.aswoo.criminalintent2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by aswoo on 2017-07-14.
 */

public class CrimeFragment extends Fragment{

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }
    /*Activity.onCreate(Bundle)은
    protected 메서드인 반면에， Fragment.onCreate(Bundle)은 p ublic 메서드다. Fragment.
    onCreate( . .. )와 Fragment의 다른 생명주기 메서드들은 public이어야 한다. 왜냐하면 이 메서
    드들은 프래그먼트를 호스팅하는 어떤 액티비티에서도 호출될 것이기 때문이다.
    * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime,containter,false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });


        return v;
        //attachtoRoot 인플레이트된 뷰를 뷰의 부모에게 추가할 것인지
    }
}
