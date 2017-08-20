package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.PriceList;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.utils.UserService;

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

        SubscriberOnNextListener getResultOnNext = new SubscriberOnNextListener<PriceList>() {
            @Override
            public void onNext(PriceList list) {
                balance = list.getBalance();
                canWith.setText(list.getBalance()+"元");
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getPriceList(UserService.service(getBaseContext()).getUserId(), new ProgressSubscriber<PriceList>(getResultOnNext, WithdrawalActivity.this, false));

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
