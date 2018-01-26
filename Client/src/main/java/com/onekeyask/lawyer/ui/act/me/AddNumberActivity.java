package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.BankByCard;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

public class AddNumberActivity extends BaseToolBarActivity {

    private EditText et_with;
    private TextView with_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);
        setToolbarText("添加银行卡");

        initView();

        initClick();
    }

    private void initClick() {
        et_with.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 19) {
                    with_next.setAlpha(1);

                } else {
                    with_next.setAlpha(0.5f);
                }
            }
        });

        with_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//

                OkGo.<String>get(Apis.PubBankByCard).params("cardNum", et_with.getText().toString()).execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        BankByCard card = (new Gson()).fromJson(response.body(), BankByCard.class);
                        if (card.getCode() == 0) {

                            //银行卡身份信息填写页
                            startActivity(VerificationBankActivity.class,
                                    "bank", card.getData().getBankname(),
                                    "type", card.getData().getCardtype(),
                                    "number", et_with.getText().toString());
                        } else {
                            //银行卡类别确认页（此页面的银行列表，参见”支付宝转账银行卡.txt”附件），
                            // 银行卡信息确认后跳转至银行卡身份信息填写页
                            startActivity(BankCardTypeActivity.class, "number", et_with.getText().toString());
                        }

                    }
                });
            }
        });
    }

    private void initView() {
        et_with = (EditText) findViewById(R.id.et_with);
        with_next = (TextView) findViewById(R.id.with_next);


    }


}
