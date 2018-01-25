package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/08/29 16:24
 */

public class IncomeAll {
    /**
     * code : 0
     * msg : 正常
     * data : {"totalMoney":1865.58}
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
         * totalMoney : 1865.58
         */

        private String totalMoney;

        public String getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(String totalMoney) {
            this.totalMoney = totalMoney;
        }
    }
}
