package com.onekeyask.lawyer.ui.act.lawyer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
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
import com.onekeyask.lawyer.entity.PriceList;
import com.onekeyask.lawyer.entity.TextChatServiceInfo;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.ui.act.consulting.TalkingActivity;
import com.onekeyask.lawyer.utils.UserService;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.onekeyask.lawyer.global.Constant.WECHAT_PAY_RESULT_ACTION;
import static com.onekeyask.lawyer.global.Constant.WECHAT_PAY_RESULT_EXTRA;
import static com.onekeyask.lawyer.global.Constant.WeChatAppID;

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
    private double balance = 0;
    private int lawyerId;
    private int priceId;
    private int fid;
    private int oid;
    private double money;
    private UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt_pic_ask);
        ButterKnife.bind(this);
        setToolbarText("图文咨询");
        service = UserService.service(getBaseContext());
        //获取本地广播实例。
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        //新建intentFilter并给其action标签赋值。
        intentFilter=new IntentFilter();
        intentFilter.addAction(WECHAT_PAY_RESULT_ACTION);

        //创建广播接收器实例，并注册。将其接收器与action标签进行绑定。
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
        initBalance();
        initData();
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

        retrofitUtil.getPriceList(service.getUserId(), new ProgressSubscriber<PriceList>(getResultOnNext, TxtPicAskActivity.this, true));

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
                .params("userId", UserService.service(getBaseContext()).getUserId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        TextChatServiceInfo info = (new Gson()).fromJson(response.body(), TextChatServiceInfo.class);
                        if (info.getCode() == 0) {

                            service_name.setText(info.getData().getTextChat().getServiceName());

                            money = info.getData().getTextChat().getPriceList().get(0).getPrice();
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
                "<br/>3.如有什么疑问您可以拨打客服电话，号码是01086393094。";
        tvNotice.setText(Html.fromHtml(s));



        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payType == 3) {
                    if (balance < money) {
                        showShort("余额不足");
                    } else {
                        goPay();
                    }
                }else {
                    goPay();
                }

            }
        });
    }

    AliPayResult result;
    private void goPay() {

        OkGo.<String>get(Apis.MakeOrderAndGetPayInfo)
                .params("userId", UserService.service(getBaseContext()).getUserId())
                .params("lawyerId", lawyerId)
                .params("priceId", priceId)
                .params("number", "1")
                .params("payType", payType)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        result = (new Gson()).fromJson(response.body(), AliPayResult.class);
                        if (result.getCode() == 0) {


                            oid = result.getData().getOrderId();
                            fid = result.getData().getFreeaskId();


                            if (payType == 1) {
                                //支付宝
                                final String orderInfo = result.getData().getZfbNew().getOrderPayInfoString();
                                goAliPay(orderInfo);
                            } else if (payType == 2) {
                                WePay(result.getData().getWx());
                            } else {

                                showShort("购买成功！");
//                                initBalance();
                                goNext();
                            }


                        } else {
                            showShort(result.getMsg());
                        }
                    }
                });

    }

    private void goAliPay(final String orderInfo) {
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
                goNext();
//                goNext(oid + "", fid + "");

            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                showShort("支付失败");
            }
        }
    };

    private void goNext() {
        Intent intent = new Intent(TxtPicAskActivity.this, TalkingActivity.class);
        intent.putExtra("cid", "0");
        intent.putExtra("lawyerId", lawyerId+"");
        intent.putExtra("oid", result.getData().getOrderId()+"");
        startActivity(intent);
        finish();
    }


    private void WePay(AliPayResult.DataBean.WxBean wx) {

        IWXAPI msgApi = WXAPIFactory.createWXAPI(getBaseContext(), null);
        // 将该app注册到微信
        WeChatAppID = wx.getAppid();
        msgApi.registerApp(WeChatAppID);
        PayReq request = new PayReq();
        request.appId = WeChatAppID;
        request.partnerId = wx.getPartnerId();
        request.prepayId = wx.getPrepayId();
        request.packageValue = wx.getPackageX();
        request.nonceStr = wx.getNonceStr();
        request.timeStamp = wx.getTimeStamp();
        request.sign = wx.getSign();
        msgApi.sendReq(request);

    }


    //本地广播数据类型实例。
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    private IntentFilter intentFilter;


    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            0	成功	展示成功页面
//            -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//            -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP
            switch (intent.getIntExtra(WECHAT_PAY_RESULT_EXTRA, 1)){
                case 1:
                    showShort("未知错误");
                    break;
                case 0:
                    showShort("支付成功");
                    goNext();
                    break;
                case -1:
                    showShort("-1\t错误\t可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。");
                    break;
                case -2:
                    showShort("订单已取消");
                    break;
            }

        }
    }

    @Override
    public void onDestroy(){//在onDestroy()方法中取消注册。
        super.onDestroy();
        //取消注册调用的是unregisterReceiver()方法，并传入接收器实例。
        localBroadcastManager.unregisterReceiver(localReceiver);
    }


}
