package com.onekeyask.lawfirm.ui.act.me;

import android.os.Bundle;

import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;

public class StatementActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        setToolbarText("声明");
    }
}
