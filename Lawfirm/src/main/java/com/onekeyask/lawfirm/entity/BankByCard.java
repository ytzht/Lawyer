package com.onekeyask.lawfirm.entity;

/**
 * Created by zht on 2018/01/26 14:27
 */

public class BankByCard {

    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"bankname":"建设银行","cardtype":"储蓄卡"}
     */

    private int code;
    private String msg;
    private String eventId;
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bankname : 建设银行
         * cardtype : 储蓄卡
         */

        private String bankname;
        private String cardtype;

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getCardtype() {
            return cardtype;
        }

        public void setCardtype(String cardtype) {
            this.cardtype = cardtype;
        }
    }
}
