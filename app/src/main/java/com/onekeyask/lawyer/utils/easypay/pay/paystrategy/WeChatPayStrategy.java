package com.onekeyask.lawyer.utils.easypay.pay.paystrategy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.onekeyask.lawyer.utils.easypay.EasyPay;
import com.onekeyask.lawyer.utils.easypay.PayParams;
import com.onekeyask.lawyer.utils.easypay.pay.PrePayInfo;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;



/**
 * Author: michaelx
 * Create: 17-3-13.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description:微信支付策略.
 */

public class WeChatPayStrategy extends BasePayStrategy {
    private LocalBroadcastManager mBroadcastManager;
    private Context mContext;

    public static final String WECHAT_PAY_RESULT_ACTION = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_ACTION";
    public static final String WECHAT_PAY_RESULT_EXTRA = "com.tencent.mm.opensdk.WECHAT_PAY_RESULT_EXTRA";

    public WeChatPayStrategy(PayParams params, String prePayInfo, EasyPay.PayCallBack resultListener) {
        super(params, prePayInfo, resultListener);
        mContext = params.getActivity();
    }

    @Override
    public void doPay() {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(mContext.getApplicationContext(), mPayParams.getWeChatAppID(), true);
        if (!wxapi.isWXAppInstalled()) {
            super.mOnPayResultListener.onPayCallBack(EasyPay.WECHAT_NOT_INSTALLED_ERR);
            return;
        }

        if (!wxapi.isWXAppSupportAPI()) {
            super.mOnPayResultListener.onPayCallBack(EasyPay.WECHAT_UNSUPPORT_ERR);
            return;
        }
        wxapi.registerApp(mPayParams.getWeChatAppID());
        registPayResultBroadcast();

        // TODO 需要做正式解析，修改PrePayInfo.java类，并解开此处注释
        Gson gson = new Gson();
        PrePayInfo payInfo = gson.fromJson(mPrePayInfo, PrePayInfo.class);
        PayReq req = new PayReq();
        req.appId = payInfo.appid;//微信开放平台审核通过的应用APPID
        req.partnerId = payInfo.partnerid;//微信支付分配的商户号
        req.prepayId = payInfo.prepayid;//预支付订单号，app服务器调用“统一下单”接口获取
        req.packageValue = payInfo.packageValue;//固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
        req.nonceStr = payInfo.noncestr;//随机字符串，不长于32位，服务器小哥会给咱生成
        req.timeStamp = payInfo.timestamp;//时间戳，app服务器小哥给出
        req.sign = payInfo.sign;//签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个

        // 发送支付请求：跳转到微信客户端
        wxapi.sendReq(req);
    }

    private void registPayResultBroadcast() {
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext.getApplicationContext());
        IntentFilter filter = new IntentFilter(WECHAT_PAY_RESULT_ACTION);
        mBroadcastManager.registerReceiver(mReceiver, filter);
    }

    private void unRegistPayResultBroadcast() {
        if (mBroadcastManager != null && mReceiver != null) {
            mBroadcastManager.unregisterReceiver(mReceiver);
            mBroadcastManager = null;
            mReceiver = null;
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra(WECHAT_PAY_RESULT_EXTRA, -100);
            mOnPayResultListener.onPayCallBack(result);

            unRegistPayResultBroadcast();
        }
    };
}
