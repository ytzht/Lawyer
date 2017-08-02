package com.onekeyask.lawyer.entity;

/**
 * Created by zht on 2017/04/13 17:37
 */

public class AskResult {

    /**
     * zfbNew : {"orderPayInfoString":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016080200149429%3B&biz_content=%7B%22body%22%3A%22%E6%89%93%E8%B5%8F%E5%92%A8%E8%AF%A2%22%2C%22out_trade_no%22%3A%2220170802210331718%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%89%93%E8%B5%8F%E5%92%A8%E8%AF%A2%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22200.0%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F139.198.11.78%3A8080%2Fmylawyer%2Fpub%2FZfbNotify%3B&sign=c%2B3xvDdj86Ka1RBMVh%2BTO0MB4TPLO5u3R7bk4OL3g58SCvvg3BQn%2B%2FHsKVJKywjI8Z7eOMr7sl8SNvUvpyL1gILkEQVKWr1InBBoHHF8lPO6sTBqXRoX07nKM6ev6dCKS4ms%2Fqndtcau811A3K7vCmHoyqqRdt8%2Bq6vqtkQ3jVcrmAEtK1deYaKvQvPVWo%2BaoIePNyT9iSC%2FjNgm3%2B2arbqd%2FIb4K5mHfl8KNh4MehYycO2vUut1KIdLULi6TY87m0mWlZ6vFhB0NzbtzSe9TcKXonaQ%2BhBYrRTxSNM4tK4FitrdCNvkTRugEA247J2gM8yMuZApkPhokOh7fpOBuw%3D%3D&sign_type=RSA2&timestamp=2017-08-02+21%3A03%3A31&version=1.0"}
     * freeaskId : 1467
     * orderId : 692
     */

    private ZfbNewBean zfbNew;
    private int freeaskId;
    private int orderId;

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

    public static class ZfbNewBean {
        /**
         * orderPayInfoString : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016080200149429%3B&biz_content=%7B%22body%22%3A%22%E6%89%93%E8%B5%8F%E5%92%A8%E8%AF%A2%22%2C%22out_trade_no%22%3A%2220170802210331718%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%89%93%E8%B5%8F%E5%92%A8%E8%AF%A2%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22200.0%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F139.198.11.78%3A8080%2Fmylawyer%2Fpub%2FZfbNotify%3B&sign=c%2B3xvDdj86Ka1RBMVh%2BTO0MB4TPLO5u3R7bk4OL3g58SCvvg3BQn%2B%2FHsKVJKywjI8Z7eOMr7sl8SNvUvpyL1gILkEQVKWr1InBBoHHF8lPO6sTBqXRoX07nKM6ev6dCKS4ms%2Fqndtcau811A3K7vCmHoyqqRdt8%2Bq6vqtkQ3jVcrmAEtK1deYaKvQvPVWo%2BaoIePNyT9iSC%2FjNgm3%2B2arbqd%2FIb4K5mHfl8KNh4MehYycO2vUut1KIdLULi6TY87m0mWlZ6vFhB0NzbtzSe9TcKXonaQ%2BhBYrRTxSNM4tK4FitrdCNvkTRugEA247J2gM8yMuZApkPhokOh7fpOBuw%3D%3D&sign_type=RSA2&timestamp=2017-08-02+21%3A03%3A31&version=1.0
         */

        private String orderPayInfoString;

        public String getOrderPayInfoString() {
            return orderPayInfoString;
        }

        public void setOrderPayInfoString(String orderPayInfoString) {
            this.orderPayInfoString = orderPayInfoString;
        }
    }
}
