package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.BaseResult;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.utils.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCardActivity extends BaseToolBarActivity {

    @BindView(R.id.et_no)
    EditText etNo;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_type)
    EditText etType;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.rb_save)
    RadioButton rbSave;
    @BindView(R.id.rb_trust)
    RadioButton rbTrust;
    @BindView(R.id.rg_card)
    RadioGroup rgCard;
    @BindView(R.id.balance_next)
    TextView balanceNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);
        setToolbarText("添加银行卡");
    }

    @OnClick({R.id.rb_save, R.id.rb_trust, R.id.rg_card, R.id.balance_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_save:
                break;
            case R.id.rb_trust:
                break;
            case R.id.rg_card:
                break;
            case R.id.balance_next:
                checkInput();
                break;
        }
    }

    private void checkInput() {
        String cardno = etNo.getText().toString();

        if (TextUtils.isEmpty(cardno)){
            showShort(etNo.getHint().toString());
            return;
        }

        String cardholder = etName.getText().toString();

        if (TextUtils.isEmpty(cardholder)){
            showShort(etName.getHint().toString());
            return;
        }

        String bankname = etType.getText().toString();

        if (TextUtils.isEmpty(bankname)){
            showShort(etType.getHint().toString());
            return;
        }

        String province = etAddress.getText().toString();
        
        if (TextUtils.isEmpty(province)){
            showShort(etAddress.getHint().toString());
            return;
        }

        if (!rbSave.isChecked() && !rbTrust.isChecked()){
            showShort("请选择银行卡种类");
            return;
        }

        String cardtype;
        if (rbTrust.isChecked()) cardtype = "信用卡";
        else cardtype = "储蓄卡";


            OkGo.<String>post(Apis.BankCardAdd)
                    .params("userId", UserService.service(getBaseContext()).getUserId())
                    .params("cardno", cardno)
                    .params("cardholder", cardholder)
                    .params("bankname", bankname)
                    .params("province", province)
                    .params("cardtype", cardtype)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            BaseResult result = (new Gson()).fromJson(response.body(), BaseResult.class);
                            if (result.getCode() == 0) {
                                showShort("添加成功");
                                finish();
                            } else {
                                showShort(result.getMsg());
                            }
                        }
                    });

    }
}
