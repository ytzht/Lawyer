package com.onekeyask.lawyer.entity;

/**
 * Created by ytzht on 2017/11/08 下午7:04
 */

public class CallUp {

    /**
     * code : 0
     * msg : 正常
     * eventId :
     * data : {"callNo":"17191179007"}
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
         * callNo : 17191179007
         */

        private String callNo;

        public String getCallNo() {
            return callNo;
        }

        public void setCallNo(String callNo) {
            this.callNo = callNo;
        }
    }
}
