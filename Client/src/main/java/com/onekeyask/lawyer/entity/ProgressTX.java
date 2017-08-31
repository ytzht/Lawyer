package com.onekeyask.lawyer.entity;

/**
 * Created by zht on 2017/08/24 13:49
 */

public class ProgressTX {
    /**
     * code : 0
     * msg : 正常
     * data : {"progressInfo":{"id":13,"lawyerId":2,"cardNum":"***4975","bankName":"建设银行","applyName":"123","money":2,"status":1,"createTime":1503046761000,"userType":0,"userId":2}}
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
         * progressInfo : {"id":13,"lawyerId":2,"cardNum":"***4975","bankName":"建设银行","applyName":"123","money":2,"status":1,"createTime":1503046761000,"userType":0,"userId":2}
         */

        private ProgressInfo progressInfo;

        public ProgressInfo getProgressInfo() {
            return progressInfo;
        }

        public void setProgressInfo(ProgressInfo progressInfo) {
            this.progressInfo = progressInfo;
        }

    }
}
