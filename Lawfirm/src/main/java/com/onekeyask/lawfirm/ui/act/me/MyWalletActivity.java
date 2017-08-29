package com.onekeyask.lawfirm.ui.act.me;

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
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.MyMoney;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.utils.UserService;

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

        OkGo.<String>get(Apis.MyWallet).params("lawyerId", UserService.service(getBaseContext()).getLawyerId()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                MyMoney myMoney = (new Gson()).fromJson(response.body(), MyMoney.class);
                if (myMoney.getCode() == 0){
                    walletYe.setText(String.valueOf(myMoney.getData().getMyMoney()));
                }else {
                    showShort(myMoney.getMsg());
                    finish();
                }
            }
        });

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
//                startActivity(RechargeActivity.class);
                break;
            case R.id.withdrawal://提现
                View view1 = LayoutInflater.from(this).inflate(R.layout.custom_dialog_share, null, false);
                alert = new AlertDialog.Builder(this).setView(view1).setCancelable(false).show();
                TextView tvMsg = (TextView)view1.findViewById(R.id.tv_msg);
                TextView cancel = (TextView)view1.findViewById(R.id.tv_cancel);
                TextView next = (TextView)view1.findViewById(R.id.tv_share_con);
                cancel.setVisibility(View.VISIBLE);
                tvMsg.setText("提现需十个工作日的时间才能到账，您是否需要继续？");
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


                break;
            case R.id.card_manage://银行卡管理
                startActivity(CardManageActivity.class);
                break;
        }
    }
}
