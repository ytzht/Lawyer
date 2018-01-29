package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.BankCardList;
import com.onekeyask.lawyer.entity.PriceList;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.utils.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyWalletActivity extends BaseToolBarActivity {

    @BindView(R.id.wallet_ye)
    TextView walletYe;
    @BindView(R.id.recharge)
    RelativeLayout recharge;
    @BindView(R.id.withdrawal)
    RelativeLayout withdrawal;
    @BindView(R.id.card_manage)
    RelativeLayout cardManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);
        setToolbarText("我的钱包");



    }

    @Override
    protected void onResume() {
        super.onResume();
        SubscriberOnNextListener getResultOnNext = new SubscriberOnNextListener<PriceList>() {
            @Override
            public void onNext(PriceList list) {
                walletYe.setText(String.valueOf(list.getBalance()));
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getPriceList(UserService.service(getBaseContext()).getUserId(), new ProgressSubscriber<PriceList>(getResultOnNext, MyWalletActivity.this, false));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallet, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.my_wallet) {
            //账目明细
            startActivity(BillingDetailsActivity.class);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private AlertDialog alert;
    @OnClick({R.id.recharge, R.id.withdrawal, R.id.card_manage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recharge://充值
                startActivity(RechargeActivity.class);
                break;
            case R.id.withdrawal://提现

                OkGo.<String>get(Apis.BankCardList)
                        .params("userId", UserService.service(getBaseContext()).getUserId())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                BankCardList list = (new Gson()).fromJson(response.body(), BankCardList.class);
                                if (list.getCode() == 0) {
                                    if (list.getData().getCardList().size() > 0){
                                        hasCard();
                                    }else {
                                        hasBank();
                                    }
                                } else {
                                    showShort(list.getMsg());
                                }
                            }
                        });
                break;
            case R.id.card_manage://银行卡管理
                startActivity(CardManageActivity.class);
                break;
        }
    }

    private void hasBank() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.custom_dialog_share, null, false);
        alert = new AlertDialog.Builder(this).setView(view1).setCancelable(false).show();
        TextView tvMsg = (TextView)view1.findViewById(R.id.tv_msg);
        TextView cancel = (TextView)view1.findViewById(R.id.tv_cancel);
        TextView next = (TextView)view1.findViewById(R.id.tv_share_con);
        cancel.setVisibility(View.VISIBLE);
        tvMsg.setText("您还没有添加银行卡，请先添加提现的银行卡信息");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CardManageActivity.class);
                if (alert.isShowing()) alert.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alert.isShowing()) alert.dismiss();
            }
        });
    }

    private void hasCard() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.custom_dialog_share, null, false);
        alert = new AlertDialog.Builder(this).setView(view1).setCancelable(false).show();
        TextView tvMsg = (TextView)view1.findViewById(R.id.tv_msg);
        TextView cancel = (TextView)view1.findViewById(R.id.tv_cancel);
        TextView next = (TextView)view1.findViewById(R.id.tv_share_con);
        cancel.setVisibility(View.VISIBLE);
        tvMsg.setText("提现预计需要十个工作日到帐，请问您是否继续？");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WithdrawalActivity.class);
                if (alert.isShowing()) alert.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alert.isShowing()) alert.dismiss();
            }
        });
    }
}
