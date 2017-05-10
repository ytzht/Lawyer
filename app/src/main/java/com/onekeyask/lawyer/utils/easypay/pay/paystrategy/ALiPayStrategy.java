package com.onekeyask.lawyer.utils.easypay.pay.paystrategy;

import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.onekeyask.lawyer.utils.easypay.EasyPay;
import com.onekeyask.lawyer.utils.easypay.PayParams;
import com.onekeyask.lawyer.utils.easypay.pay.ALiPayResult;
import com.onekeyask.lawyer.utils.easypay.util.ThreadManager;

import java.util.Map;


/**
 * Author: michaelx
 * Create: 17-3-13.
 * <p>
 * Endcode: UTF-8
 * <p>
 * Blog:http://blog.csdn.net/xiong_it | https://xiong-it.github.io
 * github:https://github.com/xiong-it
 * <p>
 * Description: 支付宝策略.
 */

public class ALiPayStrategy extends BasePayStrategy {
    private static final int PAY_RESULT_MSG = 0;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what != PAY_RESULT_MSG) {
                return;
            }
            ThreadManager.shutdown();
            ALiPayResult result = new ALiPayResult((Map<String, String>) msg.obj);
            switch (result.getResultStatus()) {
                case ALiPayResult.PAY_OK_STATUS:
                    mOnPayResultListener.onPayCallBack(EasyPay.COMMON_PAY_OK);
                    break;

                case ALiPayResult.PAY_CANCLE_STATUS:
                    mOnPayResultListener.onPayCallBack(EasyPay.COMMON_USER_CACELED_ERR);
                    break;

                case ALiPayResult.PAY_FAILED_STATUS:
                    mOnPayResultListener.onPayCallBack(EasyPay.COMMON_PAY_ERR);
                    break;

                case ALiPayResult.PAY_WAIT_CONFIRM_STATUS:
                    mOnPayResultListener.onPayCallBack(EasyPay.ALI_PAY_WAIT_CONFIRM_ERR);
                    break;

                case ALiPayResult.PAY_NET_ERR_STATUS:
                    mOnPayResultListener.onPayCallBack(EasyPay.ALI_PAY_NET_ERR);
                    break;

                case ALiPayResult.PAY_UNKNOWN_ERR_STATUS:
                    mOnPayResultListener.onPayCallBack(EasyPay.ALI_PAY_UNKNOW_ERR);
                    break;

                default:
                    mOnPayResultListener.onPayCallBack(EasyPay.ALI_PAY_OTHER_ERR);
                    break;
            }
            mHandler.removeCallbacksAndMessages(null);
        }
    };

    public ALiPayStrategy(PayParams params, String prePayInfo, EasyPay.PayCallBack resultListener) {
        super(params, prePayInfo, resultListener);
    }

    @Override
    public void doPay() {
        Runnable payRun = new Runnable() {
            @Override
            public void run() {
                PayTask task = new PayTask(mPayParams.getActivity());
                // TODO 请根据自身需求解析mPrePayinfo，最终的字符串值应该为一连串key=value形式
                Map<String, String> result = task.payV2(mPrePayInfo, true);
                Message message = mHandler.obtainMessage();
                message.what = PAY_RESULT_MSG;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        };
        ThreadManager.execute(payRun);
    }
}
