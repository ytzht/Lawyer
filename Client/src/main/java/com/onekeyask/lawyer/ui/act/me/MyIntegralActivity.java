package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

public class MyIntegralActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_integral);
        setToolbarText("我的积分");


    }
}
