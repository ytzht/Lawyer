package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseEvent;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.utils.CheckCardID;
import com.onekeyask.lawyer.utils.Forms;

public class VerificationBankActivity extends BaseToolBarActivity {

    private TextView banknametype;
    private TextView cardnumber;
    private EditText etname;
    private EditText etid;
    private EditText etphone;
    private TextView submitnext;
    private String cardno;
    private String cardholder;
    private String bankname;
    private String idNumber;
    private String checkPhoneNo;
    private String cardtype;


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
        bankname = getIntent().getStringExtra("bank");
        cardtype = getIntent().getStringExtra("type");
        banknametype.setText(bankname + cardtype);
        cardno = getIntent().getStringExtra("number");
        cardnumber.setText(cardno);


        submitnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //验证参数是否为空，若都不为空则传递参数到短信验证页面
                if (etname.getText().length() < 2) {
                    showShort("请输入持卡人姓名");
                    return;
                }

                if (!CheckCardID.isIDCard(etid.getText().toString())){
                    showShort("请输入证件号");
                    return;
                }

                if (Forms.disValid(etphone.getText().toString(), Forms.PHONENUM)){
                    etphone.requestFocus();
                    showShort("请输入银行预留手机号");
                    return;

                }

                cardholder = etname.getText().toString();
                idNumber = etid.getText().toString();
                checkPhoneNo = etphone.getText().toString();

                startActivity(VerificationSmsActivity.class,
                        "cardno", cardno,
                        "cardholder", cardholder,
                        "bankname", bankname,
                        "idNumber", idNumber,
                        "checkPhoneNo", checkPhoneNo,
                        "cardtype", cardtype);


            }
        });
    }
    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);

        if (event.getCode() == BaseEvent.AddBankCard){
            finish();
        }
    }

}
