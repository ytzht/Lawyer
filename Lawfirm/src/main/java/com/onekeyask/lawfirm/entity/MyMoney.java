package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2017/08/28 13:17
 */

public class MyMoney {
    /**
     * code : 0
     * msg : 正常
     * data : {"myMoney":0}
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
         * myMoney : 0
         */

        private int myMoney;

        public int getMyMoney() {
            return myMoney;
        }

        public void setMyMoney(int myMoney) {
            this.myMoney = myMoney;
        }
    }
}
