package com.onekeyask.lawyer.ui.act.lawyer;

import android.os.Bundle;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

public class PersonLawyerActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_lawyer);
        setToolbarText("购买私人律师服务");


    }
}
