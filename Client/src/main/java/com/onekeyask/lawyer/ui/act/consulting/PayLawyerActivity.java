package com.onekeyask.lawyer.ui.act.consulting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.GiveMoneyOrderAndGetPayInfo;
import com.onekeyask.lawyer.entity.LawyerBasic;
import com.onekeyask.lawyer.entity.PriceList;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PayLawyerActivity extends BaseToolBarActivity {

    @BindView(R.id.tv_name_give)
    TextView tvNameGive;
    @BindView(R.id.tv_money_give)
    TextView tvMoneyGive;
    @BindView(R.id.cb_ali)
    ImageView cbAli;
    @BindView(R.id.rl_ali)
    RelativeLayout rlAli;
    @BindView(R.id.cb_wx)
    ImageView cbWx;
    @BindView(R.id.rl_wx)
    RelativeLayout rlWx;
    @BindView(R.id.cb_ye)
    ImageView cbYe;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.rl_ye)
    RelativeLayout rlYe;
    @BindView(R.id.btn_pay)
    TextView btnPay;

    private String name, summary;
    private double money;
    private int payType = 1;
    private String fid = "";
    private String oid = "";
    private double balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_lawyer);
        setToolbarText("送心意");

        name = getIntent().getStringExtra("name");
        summary = getIntent().getStringExtra("summary");
        money = Double.parseDouble(getIntent().getStringExtra("money"));
        fid = getIntent().getStringExtra("fid");
        oid = getIntent().getStringExtra("oid");

        initName();
        tvMoneyGive.setText(String.valueOf(money + "元"));


        initBalance();
    }


    private void initName() {
        SubscriberOnNextListener<LawyerBasic> listener = new SubscriberOnNextListener<LawyerBasic>() {
            @Override
            public void onNext(LawyerBasic lawyerBasic) {
                tvNameGive.setText(lawyerBasic.getLawyer().getName() + "律师-送心意");
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getLawyerBasic(2, new ProgressSubscriber<LawyerBasic>(listener, PayLawyerActivity.this, true));
    }


    private void initBalance() {
        SubscriberOnNextListener getResultOnNext = new SubscriberOnNextListener<PriceList>() {
            @Override
            public void onNext(PriceList list) {
                balance = list.getBalance();
                tvBalance.setText("(可用余额" + list.getBalance() + ")");

            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getPriceList(2, new ProgressSubscriber<PriceList>(getResultOnNext, PayLawyerActivity.this, true));

    }

    @OnClick({R.id.rl_ali, R.id.rl_wx, R.id.rl_ye, R.id.btn_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_ali:
                payType = 1;
                cbAli.setImageResource(R.drawable.select_c);
                cbWx.setImageResource(R.drawable.select);
                cbYe.setImageResource(R.drawable.select);
                break;
            case R.id.rl_wx:
                payType = 2;
                cbWx.setImageResource(R.drawable.select_c);
                cbAli.setImageResource(R.drawable.select);
                cbYe.setImageResource(R.drawable.select);
                break;
            case R.id.rl_ye:
                payType = 3;
                cbYe.setImageResource(R.drawable.select_c);
                cbWx.setImageResource(R.drawable.select);
                cbAli.setImageResource(R.drawable.select);
                break;
            case R.id.btn_pay:


                if (payType == 3 && balance < money) {
                    showShort("余额不足");
                } else {

                    Map<String, String> map = new HashMap<>();
                    map.put("userId", "2");
                    map.put("lawyerId", "3");
                    map.put("money", String.valueOf(money));
                    map.put("payType", String.valueOf(payType));
                    map.put("type", "2");//1:普通心意（律师主页赠送）， 2，订单心意
                    map.put("summary", summary);

                    map.put("orderId", oid);
                    map.put("freeaskId", fid);

                    getResultOnNext = new SubscriberOnNextListener<GiveMoneyOrderAndGetPayInfo>() {
                        @Override
                        public void onNext(GiveMoneyOrderAndGetPayInfo payInfo) {
                            L.d("fid " + payInfo.getFreeaskId());
                            L.d("oid " + payInfo.getOrderId());

                            Intent intent = new Intent(PayLawyerActivity.this, EvaluateCompleteActivity.class);
                            intent.putExtra("giveMoney", false);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(int code, String message) {
                            showShort(message);
                        }
                    };
                    retrofitUtil.getGiveMoneyOrderAndGetPayInfo(map,
                            new ProgressSubscriber<GiveMoneyOrderAndGetPayInfo>(getResultOnNext, PayLawyerActivity.this, true));
                }
                break;
        }
    }
}
