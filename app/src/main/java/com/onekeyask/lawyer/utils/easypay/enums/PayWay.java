package com.onekeyask.lawyer.utils.easypay.enums;

/**
 * Created by on 2017/3/11
 */

public enum PayWay {
    WechatPay(0),
    ALiPay(1);

    int payway;
    PayWay(int way) {
        payway = way;
    }

    @Override
    public String toString() {
        return String.valueOf(payway);
    }

}
