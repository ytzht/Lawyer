package com.onekeyask.lawyer.ui.act.lawyer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.AliPayResult;
import com.onekeyask.lawyer.entity.LawyerBasic;
import com.onekeyask.lawyer.entity.PayResult;
import com.onekeyask.lawyer.entity.TextChatServiceInfo;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TxtPicAskActivity extends BaseToolBarActivity {

    @BindView(R.id.law_img)
    CircleImageView lawImg;
    @BindView(R.id.law_name)
    TextView lawName;
    @BindView(R.id.price_once)
    TextView priceOnce;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.cb_ali)
    ImageView cbAli;
    @BindView(R.id.rl_ali)
    RelativeLayout rlAli;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.cb_wx)
    ImageView cbWx;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.service_name)
    TextView service_name;
    @BindView(R.id.rl_wx)
    RelativeLayout rlWx;
    @BindView(R.id.cb_ye)
    ImageView cbYe;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.rl_ye)
    RelativeLayout rlYe;
    @BindView(R.id.notice_ll)
    LinearLayout noticeLl;
    @BindView(R.id.btn_pay)
    TextView btnPay;

    private int lawyerId;
    private int priceId;
    private int fid;
    private int oid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt_pic_ask);
        ButterKnife.bind(this);
        setToolbarText("图文咨询");


        initData();
    }

    private void initData() {


        SubscriberOnNextListener<LawyerBasic> listener = new SubscriberOnNextListener<LawyerBasic>() {
            @Override
            public void onNext(LawyerBasic lawyerBasic) {
                lawName.setText(lawyerBasic.getLawyer().getName());
                Glide.with(TxtPicAskActivity.this).load(lawyerBasic.getLawyer().getHeadURL())
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                        .into(lawImg);

            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        lawyerId = getIntent().getIntExtra("lawyerId", 0);
        retrofitUtil.getLawyerBasic(lawyerId, new ProgressSubscriber<LawyerBasic>(listener, TxtPicAskActivity.this, true));


        OkGo.<String>get(Apis.TextChatServiceInfo)
                .params("lawyerId", lawyerId)
                .params("userId", "2")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        TextChatServiceInfo info = (new Gson()).fromJson(response.body(), TextChatServiceInfo.class);
                        if (info.getCode() == 0) {

                            service_name.setText(info.getData().getTextChat().getServiceName());

                            priceOnce.setText("￥" + info.getData().getTextChat().getPriceList().get(0).getPrice()
                                    + "元/" + info.getData().getTextChat().getPriceList().get(0).getCycle());
                            totalPrice.setText("￥" + info.getData().getTextChat().getPriceList().get(0).getPrice());

                            priceId = info.getData().getTextChat().getPriceList().get(0).getPriceId();

                            initClick();
                        } else {
                            showShort(info.getMsg());
                            finish();
                        }

                    }
                });
    }

    int payType = 1;//1支付宝2微信3余额

    private void initClick() {
        rlAli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 1;
                cbAli.setImageResource(R.drawable.select_c);
                cbWx.setImageResource(R.drawable.select);
                cbYe.setImageResource(R.drawable.select);
            }
        });

        rlWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 2;
                cbAli.setImageResource(R.drawable.select);
                cbWx.setImageResource(R.drawable.select_c);
                cbYe.setImageResource(R.drawable.select);
            }
        });

        rlYe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 3;
                cbAli.setImageResource(R.drawable.select);
                cbWx.setImageResource(R.drawable.select);
                cbYe.setImageResource(R.drawable.select_c);
            }
        });

        noticeLl.setVisibility(View.VISIBLE);
        String s = "1、您可根据案件类型复杂程度购买图文咨询，如果时间或次数超时，只能再次购买进行图文咨询；" +
                "<br/>2.当订单产生，根据您消费的金额<font color='#f79f0a'>赠送50积分</font>；" +
                "<br/>3.如有什么疑问您可以拨打客服电话，号码是01082668266。";
        tvNotice.setText(Html.fromHtml(s));
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (payType == 1) {
                    //支付宝
                    goAliPay();
                } else if (payType == 2) {

                } else {

                }
            }
        });
    }


    private void goAliPay() {

        OkGo.<String>get(Apis.MakeOrderAndGetPayInfo)
                .params("userId", "2")
                .params("lawyerId", lawyerId)
                .params("priceId", priceId)
                .params("number", "1")
                .params("payType", payType)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        AliPayResult result = (new Gson()).fromJson(response.body(), AliPayResult.class);
                        if (result.getCode() == 0) {

                            oid = result.getData().getOrderId();
                            fid = result.getData().getFreeaskId();

                            final String orderInfo = result.getData().getZfbNew().getOrderPayInfoString();
                            L.d("开始支付宝支付 ", orderInfo);
                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(TxtPicAskActivity.this);
                                    Map<String, String> result = alipay.payV2(orderInfo, true);

                                    L.d("=====ali");
                                    Message msg = new Message();
                                    msg.what = 123;
                                    msg.obj = result;
                                    zfbHandler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();


                        } else {
                            showShort(result.getMsg());
                        }
                    }
                });

    }

    //支付宝支付结果回调
    private Handler zfbHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            L.d(resultInfo);
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                showShort("支付成功");
//                goNext(oid + "", fid + "");

            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                showShort("支付失败");
            }
        }
    };


}
