package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

public class WithStateActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_state);
        setToolbarText("提现进度");
    }
}
