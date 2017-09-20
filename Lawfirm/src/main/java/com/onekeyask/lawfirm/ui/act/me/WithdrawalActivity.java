package com.onekeyask.lawfirm.ui.act.me;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.MyMoney;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.utils.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawalActivity extends BaseToolBarActivity {

    @BindView(R.id.can_with)
    TextView canWith;
    @BindView(R.id.et_with)
    EditText etWith;
    @BindView(R.id.with_next)
    TextView withNext;

    double balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        ButterKnife.bind(this);
        setToolbarText("余额提现");

        OkGo.<String>get(Apis.MyWallet).params("lawyerId", UserService.service(getBaseContext()).getLawyerId()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                MyMoney myMoney = (new Gson()).fromJson(response.body(), MyMoney.class);
                if (myMoney.getCode() == 0){
                    balance = Double.parseDouble(myMoney.getData().getMyMoney());
                    canWith.setText(myMoney.getData().getMyMoney()+"元");
                }else {
                    showShort(myMoney.getMsg());
                    finish();
                }
            }
        });

    }

    @OnClick(R.id.with_next)
    public void onViewClicked() {
        if (!etWith.getText().toString().equals("") && !etWith.getText().toString().equals(".")){
            double ye = Double.valueOf(etWith.getText().toString());
            if (balance>=ye){
                if (ye>=5) {
                    startActivity(PwdAuthActivity.class, "money", ye+"");
                }else {
                    showShort("提现的金额不能少于5.00元");
                }
            }else {
                showShort("您输入的金额需小于可提现金额");
            }
        }else {
            showShort("请输入提现金额");
        }





    }
}
