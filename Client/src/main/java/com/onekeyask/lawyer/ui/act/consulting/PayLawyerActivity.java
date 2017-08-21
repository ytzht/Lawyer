package com.onekeyask.lawyer.ui.act.consulting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.GiveMoneyOrderAndGetPayInfo;
import com.onekeyask.lawyer.entity.PayResult;
import com.onekeyask.lawyer.entity.PriceList;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.Constant;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.utils.UserService;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.onekeyask.lawyer.global.Constant.WECHAT_PAY_RESULT_ACTION;
import static com.onekeyask.lawyer.global.Constant.WECHAT_PAY_RESULT_EXTRA;
import static com.onekeyask.lawyer.global.Constant.WeChatAppID;

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

    private String summary, type;
    private double money;
    private int payType = 1;
    private String userServiceId = "";
    private double balance = 0;
    private int lawyerId;

    private UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_lawyer);
        setToolbarText("送心意");

        type = getIntent().getStringExtra("type");
        summary = getIntent().getStringExtra("summary");
        lawyerId = getIntent().getIntExtra("lawyerId", Constant.lawyerId);
        money = getIntent().getDoubleExtra("money", 0);
        userServiceId = getIntent().getStringExtra("userServiceId");
        service = UserService.service(getBaseContext());
        tvNameGive.setText(getIntent().getStringExtra("name") + "律师-送心意");
        tvMoneyGive.setText(String.valueOf(money + "元"));

        //获取本地广播实例。
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        //新建intentFilter并给其action标签赋值。
        intentFilter=new IntentFilter();
        intentFilter.addAction(WECHAT_PAY_RESULT_ACTION);

        //创建广播接收器实例，并注册。将其接收器与action标签进行绑定。
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);


        initBalance();
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

        retrofitUtil.getPriceList(service.getUserId(), new ProgressSubscriber<PriceList>(getResultOnNext, PayLawyerActivity.this, true));

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

                if (payType == 3) {
                    if (balance < money) {
                        showShort("余额不足");
                    } else {
                        goPay();
                    }
                }else {
                    goPay();
                }
                break;
        }
    }

    private void goPay() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", service.getUserId() + "");
        map.put("lawyerId", lawyerId + "");
        map.put("money", String.valueOf(money));
        map.put("payType", String.valueOf(payType));
        map.put("type", type);//1:普通心意（律师主页赠送）， 2，订单心意
        map.put("summary", summary);
        if (!userServiceId.equals("-1"))
            map.put("userServiceId", userServiceId);
        getResultOnNext = new SubscriberOnNextListener<GiveMoneyOrderAndGetPayInfo>() {
            @Override
            public void onNext(GiveMoneyOrderAndGetPayInfo payInfo) {

                if (payType == 1){
                    ZfbPay(payInfo.getZfbNew().getOrderPayInfoString());
                }

                if (payType == 2){
                    WePay(payInfo.getWx());
                }

                if (payType == 3){
                    goNextActivity();
                }

            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getGiveMoneyOrderAndGetPayInfo(map,
                new ProgressSubscriber<GiveMoneyOrderAndGetPayInfo>(getResultOnNext, PayLawyerActivity.this, true));
    }

    private void goNextActivity() {
        Intent intent = new Intent(PayLawyerActivity.this, EvaluateCompleteActivity.class);
        intent.putExtra("giveMoney", false);
        intent.putExtra("lawyerId", lawyerId);
        startActivity(intent);
        finish();
    }




    //进行支付宝调起支付
    private void ZfbPay(final String orderInfo) {
        //支付宝支付
        L.d("开始支付宝支付 ", orderInfo);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayLawyerActivity.this);
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
                goNextActivity();
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                showShort("支付失败");
            }
        }
    };

    private void WePay(GiveMoneyOrderAndGetPayInfo.WxBean wx) {

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
                    goNextActivity();
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
