package com.onekeyask.lawyer.entity;

/**
 * Created by zht on 2017/04/13 17:37
 */

public class AskResult {

    private String freeaskId;
    private String orderId;
    private ZfbNew zfbNew;
    private ZFBPay zfb;
    private WXPay wx;

    public void setFreeaskId(String freeaskId) {
        this.freeaskId = freeaskId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ZfbNew getZfbNew() {
        return zfbNew;
    }

    public void setZfbNew(ZfbNew zfbNew) {
        this.zfbNew = zfbNew;
    }

    public ZFBPay getZfb() {
        return zfb;
    }

    public void setZfb(ZFBPay zfb) {
        this.zfb = zfb;
    }

    public WXPay getWx() {
        return wx;
    }

    public void setWx(WXPay wx) {
        this.wx = wx;
    }

    public String getFreeaskId() {
        return freeaskId;
    }

    private class WXPay {
        private String appid;
        private String partnerId;
        private String prepayId;
        private String nonceStr;
        private String timeStamp;
        private String sign;
//        private String package;

        public String getAppid() {
            return appid;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public String getSign() {
            return sign;
        }
    }

    private class ZfbNew {

    }

    private class ZFBPay {
    }
}
