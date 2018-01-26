package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

public class VerificationBankActivity extends BaseToolBarActivity {

    private TextView banknametype;
    private TextView cardnumber;
    private EditText etname;
    private EditText etid;
    private EditText etphone;
    private TextView submitnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_bank);

        initView();

    }

    private void initView() {
        setToolbarText("验证银行卡");
        this.submitnext = (TextView) findViewById(R.id.submit_next);
        this.etphone = (EditText) findViewById(R.id.et_phone);
        this.etid = (EditText) findViewById(R.id.et_id);
        this.etname = (EditText) findViewById(R.id.et_name);
        this.cardnumber = (TextView) findViewById(R.id.card_number);
        this.banknametype = (TextView) findViewById(R.id.bank_name_type);
        banknametype.setText(getIntent().getStringExtra("bank") + getIntent().getStringExtra("type"));
        cardnumber.setText(getIntent().getStringExtra("number"));

    }
}
