package com.onekeyask.lawyer.utils.easypay.callback;


import com.onekeyask.lawyer.utils.easypay.enums.PayWay;

/**
 * Created by michaelx on 2017/3/11.
 */

public interface OnPayResultListener {
    void onPaySuccess(PayWay payWay);

    void onPayCancel(PayWay payWay);

    void onPayFailure(PayWay payWay, int errCode);
}
