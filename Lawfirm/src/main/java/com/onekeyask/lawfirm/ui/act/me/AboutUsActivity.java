package com.onekeyask.lawfirm.ui.act.me;

import android.os.Bundle;

import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;

public class AboutUsActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setToolbarText("关于我们");
    }
}
