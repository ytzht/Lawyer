package com.onekeyask.lawyer.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zht on 2017/08/07 14:37
 */

public class AliPayResult {

    /**
     * code : 0
     * msg : 正常
     * data : {"zfbNew":{"orderPayInfoString":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016080500170426&biz_content=%7B%22body%22%3A%22%E5%BE%8B%E5%B8%88%E4%B9%8B%E5%AE%B6%E5%BE%8B%E5%B8%88%E7%9A%84%E7%A7%81%E4%BA%BA%E5%BE%8B%E5%B8%88%22%2C%22out_trade_no%22%3A%2220170807143658751%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%A7%81%E4%BA%BA%E5%BE%8B%E5%B8%88%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22500.00%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F139.198.11.78%3A8080%2Fmylawyer%2Fpub%2FZfbNotify&sign=p0MmBHz%2BYpFo1zNcKUh2GSL2p8riqFPApkh8OFCK41%2BwujIoeJEWall0rOzIGZIIdLKI0TJg8Icbq9uutFXg0IsSafQ3MSDBFC3Y4E3GEg3sFYJ0UzF9SlPFl%2FlKJGnrFqJcfisULVmnyhXCJcGoZd9dYqBfLHIMrWnTBhcHotwmja3NdBzjEDW0czeqI6JTUY1WL405dGovPLbXMZ%2BOfT7QRV5OSjtn3eqhma9rYXnTzZP%2BH0Kj%2BZGkih2BZ3XNoWQoScXoya9AnUCwR2h%2BrNx56fdfx4pbJeWQ5L7vZKpPydbz%2FuimIuF3202H7K6fjX0o9VnxYGx%2Fj7sJM%2FpyBg%3D%3D&sign_type=RSA2&timestamp=2017-08-07+14%3A36%3A58&version=1.0"},"freeaskId":0,"orderId":752}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * zfbNew : {"orderPayInfoString":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016080500170426&biz_content=%7B%22body%22%3A%22%E5%BE%8B%E5%B8%88%E4%B9%8B%E5%AE%B6%E5%BE%8B%E5%B8%88%E7%9A%84%E7%A7%81%E4%BA%BA%E5%BE%8B%E5%B8%88%22%2C%22out_trade_no%22%3A%2220170807143658751%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%A7%81%E4%BA%BA%E5%BE%8B%E5%B8%88%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22500.00%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F139.198.11.78%3A8080%2Fmylawyer%2Fpub%2FZfbNotify&sign=p0MmBHz%2BYpFo1zNcKUh2GSL2p8riqFPApkh8OFCK41%2BwujIoeJEWall0rOzIGZIIdLKI0TJg8Icbq9uutFXg0IsSafQ3MSDBFC3Y4E3GEg3sFYJ0UzF9SlPFl%2FlKJGnrFqJcfisULVmnyhXCJcGoZd9dYqBfLHIMrWnTBhcHotwmja3NdBzjEDW0czeqI6JTUY1WL405dGovPLbXMZ%2BOfT7QRV5OSjtn3eqhma9rYXnTzZP%2BH0Kj%2BZGkih2BZ3XNoWQoScXoya9AnUCwR2h%2BrNx56fdfx4pbJeWQ5L7vZKpPydbz%2FuimIuF3202H7K6fjX0o9VnxYGx%2Fj7sJM%2FpyBg%3D%3D&sign_type=RSA2&timestamp=2017-08-07+14%3A36%3A58&version=1.0"}
         * freeaskId : 0
         * orderId : 752
         */

        private ZfbNewBean zfbNew;


        public ZfbNewBean getZfbNew() {
            return zfbNew;
        }

        public void setZfbNew(ZfbNewBean zfbNew) {
            this.zfbNew = zfbNew;
        }

        public static class ZfbNewBean {
            /**
             * orderPayInfoString : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016080500170426&biz_content=%7B%22body%22%3A%22%E5%BE%8B%E5%B8%88%E4%B9%8B%E5%AE%B6%E5%BE%8B%E5%B8%88%E7%9A%84%E7%A7%81%E4%BA%BA%E5%BE%8B%E5%B8%88%22%2C%22out_trade_no%22%3A%2220170807143658751%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%A7%81%E4%BA%BA%E5%BE%8B%E5%B8%88%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22500.00%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F139.198.11.78%3A8080%2Fmylawyer%2Fpub%2FZfbNotify&sign=p0MmBHz%2BYpFo1zNcKUh2GSL2p8riqFPApkh8OFCK41%2BwujIoeJEWall0rOzIGZIIdLKI0TJg8Icbq9uutFXg0IsSafQ3MSDBFC3Y4E3GEg3sFYJ0UzF9SlPFl%2FlKJGnrFqJcfisULVmnyhXCJcGoZd9dYqBfLHIMrWnTBhcHotwmja3NdBzjEDW0czeqI6JTUY1WL405dGovPLbXMZ%2BOfT7QRV5OSjtn3eqhma9rYXnTzZP%2BH0Kj%2BZGkih2BZ3XNoWQoScXoya9AnUCwR2h%2BrNx56fdfx4pbJeWQ5L7vZKpPydbz%2FuimIuF3202H7K6fjX0o9VnxYGx%2Fj7sJM%2FpyBg%3D%3D&sign_type=RSA2&timestamp=2017-08-07+14%3A36%3A58&version=1.0
             */

            private String orderPayInfoString;

            public String getOrderPayInfoString() {
                return orderPayInfoString;
            }

            public void setOrderPayInfoString(String orderPayInfoString) {
                this.orderPayInfoString = orderPayInfoString;
            }
        }
        /**
         * wx : {"appid":"wx51d42419270bac61","partnerId":"1485835722","prepayId":"wx201708211603235979d8d2330580137138","nonceStr":"Bt48oSao41cY6foT","timeStamp":"1503302603","sign":"AFA6B07E263A269A8247D496788DE8CD","package":"Sign=WXPay"}
         * freeaskId : 0
         * orderId : 896
         */

        private WxBean wx;
        private int freeaskId;
        private int orderId;

        public WxBean getWx() {
            return wx;
        }

        public void setWx(WxBean wx) {
            this.wx = wx;
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

        public static class WxBean {
            /**
             * appid : wx51d42419270bac61
             * partnerId : 1485835722
             * prepayId : wx201708211603235979d8d2330580137138
             * nonceStr : Bt48oSao41cY6foT
             * timeStamp : 1503302603
             * sign : AFA6B07E263A269A8247D496788DE8CD
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
}
