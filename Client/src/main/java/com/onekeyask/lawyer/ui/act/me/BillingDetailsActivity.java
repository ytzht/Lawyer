package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

public class BillingDetailsActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);
        setToolbarText("账单明细");

        startActivity(WithStateActivity.class);
    }
}
