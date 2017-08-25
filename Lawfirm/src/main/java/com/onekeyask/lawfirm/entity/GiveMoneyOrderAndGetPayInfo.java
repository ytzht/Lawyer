package com.onekeyask.lawfirm.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zht on 2017/04/18 9:36
 */

public class GiveMoneyOrderAndGetPayInfo {


    /**
     * err : {"code":0,"msg":"正常","eventId":""}
     * zfbNew : {"orderPayInfoString":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016080200149429%3B&biz_content=%7B%22body%22%3A%22%E9%80%81%E7%BB%99%EF%BC%9A%E5%BE%8B%E5%B8%88%E4%B9%8B%E5%AE%B6%E5%BE%8B%E5%B8%88%E7%9A%84%E5%BF%83%E6%84%8F%22%2C%22out_trade_no%22%3A%2220170419102641474%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E9%80%81%E5%BF%83%E6%84%8F%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%221%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F139.198.11.78%3A8080%2Fmylawyer%2Fpub%2FZfbNotify%3B&sign=ZaSYP74BvgYNUuYSZj2qz%2F8F%2FnPOvbmLQacaJecoDBmfeS6WNZj4rOn5ewFerU6%2BUTk%2B81f%2Fpq2oh8GbTfdKCjIeOKYMjhuT2HQAEx9ENdHeEll8Gzyf2HVxLXMtUeP0er9ClJbG2HQXXHkSmvhMUjgNpJcv14FKQrFhN2RDseomxPlJbpid6sKhCHzRTQxs6J6HbaosPKdUrJJ7rdqwHzfNUVu8k2G3JYo8Ud3GEl0BDWuzMrlfyLL8d7JiETQmSndihFOlKDNVW%2Bm5c5BJFwcR8iAbgcF6jaH9QYp2OvLxijhzdBdbt5Ppxd%2FUc3TFmMRHy2bHFNYgxZVK52DxwA%3D%3D&sign_type=RSA2&timestamp=2017-04-19+10%3A26%3A41&version=1.0"}
     * freeaskId : 0
     * orderId : 287
     */


    private ZfbNewBean zfbNew;
    private int freeaskId;
    private int orderId;
    /**
     * wx : {"appid":"wx51d42419270bac61","partnerId":"1485835722","prepayId":"wx20170821152454ff346f9c1b0262639083","nonceStr":"kp6zH4IjTmxBaClO","timeStamp":"1503300294","sign":"224B257DC96E4162D036817BE2B831D2","package":"Sign=WXPay"}
     */

    private WxBean wx;


    public ZfbNewBean getZfbNew() {
        return zfbNew;
    }

    public void setZfbNew(ZfbNewBean zfbNew) {
        this.zfbNew = zfbNew;
    }

    public int getFreeaskId() {
        return freeaskId;
    }

    public void setFreeaskId(int freeaskId) {
        this.freeaskId = freeaskId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public WxBean getWx() {
        return wx;
    }

    public void setWx(WxBean wx) {
        this.wx = wx;
    }

    public static class ZfbNewBean {
        /**
         * orderPayInfoString : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016080200149429%3B&biz_content=%7B%22body%22%3A%22%E9%80%81%E7%BB%99%EF%BC%9A%E5%BE%8B%E5%B8%88%E4%B9%8B%E5%AE%B6%E5%BE%8B%E5%B8%88%E7%9A%84%E5%BF%83%E6%84%8F%22%2C%22out_trade_no%22%3A%2220170419102641474%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E9%80%81%E5%BF%83%E6%84%8F%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%221%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F139.198.11.78%3A8080%2Fmylawyer%2Fpub%2FZfbNotify%3B&sign=ZaSYP74BvgYNUuYSZj2qz%2F8F%2FnPOvbmLQacaJecoDBmfeS6WNZj4rOn5ewFerU6%2BUTk%2B81f%2Fpq2oh8GbTfdKCjIeOKYMjhuT2HQAEx9ENdHeEll8Gzyf2HVxLXMtUeP0er9ClJbG2HQXXHkSmvhMUjgNpJcv14FKQrFhN2RDseomxPlJbpid6sKhCHzRTQxs6J6HbaosPKdUrJJ7rdqwHzfNUVu8k2G3JYo8Ud3GEl0BDWuzMrlfyLL8d7JiETQmSndihFOlKDNVW%2Bm5c5BJFwcR8iAbgcF6jaH9QYp2OvLxijhzdBdbt5Ppxd%2FUc3TFmMRHy2bHFNYgxZVK52DxwA%3D%3D&sign_type=RSA2&timestamp=2017-04-19+10%3A26%3A41&version=1.0
         */

        private String orderPayInfoString;

        public String getOrderPayInfoString() {
            return orderPayInfoString;
        }

        public void setOrderPayInfoString(String orderPayInfoString) {
            this.orderPayInfoString = orderPayInfoString;
        }
    }

    public static class WxBean {
        /**
         * appid : wx51d42419270bac61
         * partnerId : 1485835722
         * prepayId : wx20170821152454ff346f9c1b0262639083
         * nonceStr : kp6zH4IjTmxBaClO
         * timeStamp : 1503300294
         * sign : 224B257DC96E4162D036817BE2B831D2
         * package : Sign=WXPay
         */

        private String appid;
        private String partnerId;
        private String prepayId;
        private String nonceStr;
        private String timeStamp;
        private String sign;
        @SerializedName("package")
        private String packageX;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }
    }
}
