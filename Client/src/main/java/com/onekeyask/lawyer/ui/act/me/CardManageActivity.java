package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

public class CardManageActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage);
        setToolbarText("管理银行卡");

        startActivity(AddCardActivity.class);
    }
}